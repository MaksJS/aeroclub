package models

import play.api.db._
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.i18n._
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

case class Transaction(id: Pk[Long], accountId: Long, flightId: Long, date: Date, amount: Double) {

  override def toString = Messages.get("models.transaction.toString", amount.toString, new SimpleDateFormat("dd/MM/yyyy").format(date))

}

object Transaction {

  val transaction = {
    get[Pk[Long]]("id")~
    get[Long]("account_id")~
    get[Long]("flight_id")~
    get[Date]("date")~
    get[Double]("amount") map {
      case id~account_id~flight_id~date~amount => Transaction(id, account_id, flight_id, date, amount)
    }
  }

  def create(account: Account, flight: Flight, amount: Double) {
    DB.withConnection { implicit c =>
      SQL(
        """
          INSERT INTO transaction(account_id, flight_id, date, amount) VALUES ({account_id}, {flight_id}, {date}, {amount})
        """
      ).on(
        'account_id -> account.id,
        'flight_id  -> flight.id,
        'date       -> new Date,
        'amount     -> amount
      ).executeInsert()
    }
  }
}
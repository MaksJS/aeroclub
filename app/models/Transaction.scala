package models

import play.api.db._
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.i18n._
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

case class Transaction(id: Pk[Long], accountId: Long, date: Date, amount: Double) {

  override def toString = Messages.get("models.transaction.toString", amount.toString, new SimpleDateFormat("dd/MM/yyyy").format(date))

}

object Transaction {

  val transaction = {
    get[Pk[Long]]("id")~
    get[Long]("account_id")~
    get[Date]("date")~
    get[Double]("amount") map {
      case id~account_id~date~amount => Transaction(id, account_id, date, amount)
    }
  }

  def create(account: Account, amount: Double) {
    DB.withConnection { implicit c =>
      val id: Long = SQL("select nextval('transaction_seq')").as(scalar[Long].single)
      SQL(
        """
          INSERT INTO transaction(id, account_id, date, amount) VALUES ({id}, {account_id}, {date}, {amount})
        """
      ).on(
        'id         -> id,
        'account_id -> account.id,
        'date       -> new Date,
        'amount     -> amount
      ).executeInsert()
    }
  }

  def calculateAmount(planePrice: Double, flightDuration: Int, flightReduction: Double = 0.0, weekReduction: Double = 0.0, specialPrice: Double = 0.0): Double = {
    planePrice * flightDuration - flightReduction - weekReduction - specialPrice
  }
}
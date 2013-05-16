import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import views.html._
import models.User
import models.Account
import controllers.Scalate
import play.libs.Crypto

object Global extends GlobalSettings {

	val adminPassword = "admin"

	override def onHandlerNotFound(request: RequestHeader): Result = {
		NotFound(Scalate("notFound").render('request -> request))
	}

	override def onStart(app: Application) {
		if(User.find.all.size == 0) {
			val user = new User(1L, "admin", Crypto.sign(adminPassword), "admin@aeroclub.git")
			user.save
			Account.create(user)
		}
	}
}
package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.users.*;
import models.User;
import models.Account;
import models.Transaction;
import play.i18n.*;

public class Users extends Controller {
	
	static Form<User> userForm = Form.form(User.class);
	static Result GO_HOME      = redirect(routes.Application.index());

	// GET /users/new
	public static Result _new() {
		return ok(_new.render(userForm));
	}

	// GET /user/account
	@Security.Authenticated(Secured.class)
	public static Result account() {
		User user = User.find
						.where()
						.eq("username", session().get("user"))
						.findUnique();
		return ok(account.render(user.getAccount()));
	}

	// GET /user/edit
	@Security.Authenticated(Secured.class)
	public static Result edit() {
		User user = User.find
						.where()
						.eq("username", session().get("user"))
						.findUnique();
		Form<User> filledForm = userForm.fill(user);
		return ok(edit.render(filledForm));
	}

	// POST /user
	@Security.Authenticated(Secured.class)
	public static Result update() {
		Form<User> filledForm = userForm.bindFromRequest();
		Long id = User.find.where()
					  	   .eq("username", session().get("user"))
					  	   .findUnique()
					       .id;
		//play.Logger.info(filledForm.errors().toString());
		if (filledForm.hasErrors()) {
			flash("error", Messages.get("controllers.error"));
			return badRequest(edit.render(filledForm));
		}
		else {
			User.update(id, filledForm.get());
			flash("success", Messages.get("controllers.updateSuccess", filledForm.get()));
			return redirect(routes.Users.edit());
		}
	}

	// POST /users
	public static Result create() {
		Form<User> filledForm = userForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			flash("error", Messages.get("controllers.error"));
			return badRequest(_new.render(filledForm));
		}
		else {
			try {
				User.save(filledForm.get());
				flash("success", Messages.get("controllers.createSuccess", filledForm.get()));
				return GO_HOME;
			}
			catch (Exception e) { // if the username already exists
				flash("error", Messages.get("controllers.errorAccountName"));
				return badRequest(_new.render(filledForm));
			}
		}
	}
}
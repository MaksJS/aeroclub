package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.planetypes.*;
import models.PlaneType;
import play.i18n.*;

@Security.Authenticated(Secured.class)
public class PlaneTypes extends Controller {
  
	static Form<PlaneType> planeTypeForm = Form.form(PlaneType.class);
	static Result GO_HOME = redirect(routes.PlaneTypes.index(0, 10));

	// GET /planetype?page=0&pageSize=10
	public static Result index(int page, int pageSize) {
		return ok(index.render(PlaneType.page(page, pageSize)));
	}

	// GET /planetype/:id
	public static Result show(Long id) {
		return ok(show.render(PlaneType.find.byId(id)));
	}

	// GET /planetype/new
	public static Result _new() {
		if (Secured.isAdmin()) {
			return ok(_new.render(planeTypeForm));
		}
		else {
			return forbidden(views.html.forbidden.render());
		}
	}

	// GET /planetype/:id/edit
	public static Result edit(Long id) {
		if (Secured.isAdmin()) {
			Form<PlaneType> filledForm = planeTypeForm.fill(PlaneType.find.byId(id));
			return ok(edit.render(id, filledForm));
		}
		else {
			return forbidden(views.html.forbidden.render());
		}
	}

	// POST /planetype
	public static Result create() {
		if (Secured.isAdmin()) {
			Form<PlaneType> filledForm = planeTypeForm.bindFromRequest();
			if (filledForm.hasErrors()) {
				flash("error", Messages.get("controllers.error"));
				return badRequest(_new.render(filledForm));
			}
			else {
				filledForm.get().save();
				flash("success", Messages.get("controllers.createSuccess", filledForm.get()));
				return GO_HOME;
			}
		}
		else {
			return forbidden(views.html.forbidden.render());
		}
	}

	// POST /planetype/:id
	public static Result update(Long id) {
		if (Secured.isAdmin()) {
			Form<PlaneType> filledForm = planeTypeForm.bindFromRequest();
			if (filledForm.hasErrors()) {
				flash("error", Messages.get("controllers.error"));
				return badRequest(edit.render(id, filledForm));
			}
			else {
				filledForm.get().update(id);
				flash("success", Messages.get("controllers.updateSuccess", filledForm.get()));
				return GO_HOME;
			}
		}
		else {
			return forbidden(views.html.forbidden.render());
		}
	}

	// POST /planetype/:id/delete
	public static Result delete(Long id) {
		if (Secured.isAdmin()) {
			PlaneType.find.byId(id).delete();
			flash("success", Messages.get("controllers.planetype.deleteSuccess"));
			return GO_HOME;
		}
		else {
			return forbidden(views.html.forbidden.render());
		}
	}
}
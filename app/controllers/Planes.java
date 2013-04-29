package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.planes.*;
import models.Plane;
import play.i18n.*;
import flexjson.JSONSerializer;

public class Planes extends Controller {
  
	static Form<Plane> planeForm = Form.form(Plane.class);
	static Result GO_HOME = redirect(routes.Planes.index(0, 10));

	// GET /planes.json
	public static Result indexJson() {
		return ok(new JSONSerializer().exclude("flights", "planeType", "class").serialize(Plane.find.all()));
	}

	// GET /planes?page=0&pageSize=10
	@Security.Authenticated(Secured.class)
	public static Result index(int page, int pageSize) {
		return ok(index.render(Plane.page(page, pageSize)));
	}

	// GET /planes/:id
	@Security.Authenticated(Secured.class)
	public static Result show(Long id) {
		return ok(show.render(Plane.find.byId(id)));
	}

	// GET /planes/new
	@Security.Authenticated(Secured.class)
	public static Result _new() {
		return ok(_new.render(planeForm));
	}

	// GET /planes/:id/edit
	@Security.Authenticated(Secured.class)
	public static Result edit(Long id) {
		Form<Plane> filledForm = planeForm.fill(Plane.find.byId(id));
		return ok(edit.render(id, filledForm));
	}

	// POST /planes
	@Security.Authenticated(Secured.class)
	public static Result create() {
		Form<Plane> filledForm = planeForm.bindFromRequest();
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

	// POST /planes/:id
	@Security.Authenticated(Secured.class)
	public static Result update(Long id) {
		Form<Plane> filledForm = planeForm.bindFromRequest();
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

	// POST /planes/:id/delete
	@Security.Authenticated(Secured.class)
	public static Result delete(Long id) {
		Plane.find.byId(id).delete();
		flash("success", Messages.get("controllers.planes.deleteSuccess"));
		return GO_HOME;
	}
}
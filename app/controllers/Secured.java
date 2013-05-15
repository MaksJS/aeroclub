package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Security.*;
import play.mvc.Http.*;

public class Secured extends Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("user");
    }

    @Override
    // Generates an alternative result if the user is not authenticated; the default a simple '401 Not Authorized' page.
    public Result onUnauthorized(Context ctx) {
    	return redirect(routes.Application.index());
    }

    public static Boolean isAdmin() {
    	return Context.current().request().username().equals("admin");
    }
}
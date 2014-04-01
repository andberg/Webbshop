package controllers;

import play.mvc.Http.Context;
import play.mvc.Security;

public class UserAuthenticator extends Security.Authenticator {
	@Override
    public String getUsername(Context ctx) {
        return ctx.session().get("user");
    }
}

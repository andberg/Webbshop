package controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.Staff;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminlogin;
import views.html.adminloginform;
import views.html.startpage;

public class AdminController extends Controller {

	@Transactional
	@Security.Authenticated
	public static Result index() {
		return ok(adminlogin.render(null));
	}

	@Transactional
	public static Result loginAdmin() {
		if (session().containsKey("username")) {
			String message = "You are logged in as "
					+ session().get("username").toUpperCase();
			return ok(adminlogin.render(message));
		}
		return ok(adminloginform.render());
	}

	@Transactional
	public static Result loginAdminValidate() {
		if (session().containsKey("user")){
			session().clear(); 
		}
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String username = form.get("username")[0];
		String password = form.get("password")[0];

		boolean usernameIsEmpty = "".equals(username);
		boolean passwordIsEmpty = "".equals(password);

		if (usernameIsEmpty || passwordIsEmpty) {
			if (usernameIsEmpty) {
				flash().put("username-empty", "yes");
			}
			if (passwordIsEmpty) {
				flash().put("password-empty", "yes");
			}

			return redirect(routes.AdminController.loginAdmin());
		}

		TypedQuery<Staff> query = JPA
				.em()
				.createQuery(
						"SELECT c FROM Staff c WHERE c.username = :username AND c.password = :password",
						Staff.class);
		query.setParameter("username", username);
		query.setParameter("password", password);

		List<Staff> matchingStaffs = query.getResultList();

		if (matchingStaffs.size() == 1) {
			session().put("username", username);
			String message = "You are logged in as "
					+ session().get("username").toUpperCase();
			return ok(adminlogin.render(message));
		} else {
			flash().put("no-username-password-match", "yes");

			return redirect(routes.AdminController.loginAdmin());
		}
	}

	public static Result logoutAdmin() {
		if (session().containsKey("username")) {
			session().clear();
			return ok(startpage.render());
		}
		return ok(startpage.render());
	}
}

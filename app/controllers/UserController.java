package controllers;

import java.util.List;

import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.readallusers;
import views.html.readuser;

public class UserController extends Controller {

	@Transactional
	public static Result readUser(String username) {
		models.User user = JPA.em().find(models.User.class, username); 

		if (user == null) {
			return notFound("User not found");
		}
		return ok(readuser.render(user));
	}

	@Transactional
	public static Result readAllUsers() {
		List<User> users = JPA.em().createQuery("SELECT c FROM User c", User.class).getResultList();
		return ok(readallusers.render(users));
	}

}

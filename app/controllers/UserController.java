package controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.createuserform;
import views.html.readallusers;
import views.html.readuser;
import views.html.startpage;
import views.html.userlogin;
import views.html.userloginform;
import views.html.placeorderform;

public class UserController extends Controller {

	@Transactional
	@Security.Authenticated(UserAuthenticator.class)
	public static Result readUser(String email) {
		TypedQuery<User> query = JPA
				.em()
				.createQuery(
						"SELECT c FROM User c WHERE c.email = :email",
						User.class);
		query.setParameter("email", email);
		User user = query.getSingleResult();  
			
		if (user == null) {
			return notFound("User not found");
		}
		return ok(readuser.render(user));
	}
	
	@Transactional
	@Security.Authenticated
	public static Result adminReadUser(String email) {
		TypedQuery<User> query = JPA
				.em()
				.createQuery(
						"SELECT c FROM User c WHERE c.email = :email",
						User.class);
		query.setParameter("email", email);
		User user = query.getSingleResult();  
			
		if (user == null) {
			return notFound("User not found");
		}
		return ok(readuser.render(user));
	}

	@Transactional
	@Security.Authenticated
	public static Result readAllUsers() {
		List<User> users = JPA.em()
				.createQuery("SELECT c FROM User c", User.class)
				.getResultList();
		return ok(readallusers.render(users));
	}

	@Transactional
	public static Result createUserForm() {
		return ok(createuserform.render());
	}

	@Transactional
	public static Result createUser() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String email = form.get("email")[0];
		String password = form.get("password")[0];
		String firstname = form.get("firstname")[0];
		String surname = form.get("surname")[0];
		String streetAddress = form.get("streetaddress")[0];
		String postcode = form.get("postcode")[0];
		String town = form.get("town")[0];
		String phonenumber = form.get("phonenumber")[0];
		
		boolean emailEmpty = "".equals(email); 
		boolean passwordEmpty = "".equals(password); 
		boolean firstnameEmpty = "".equals(firstname);
		boolean surnameEmpty = "".equals(surname);
		boolean streetAddressEmpty = "".equals(streetAddress);
		boolean postcodeEmpty = "".equals(postcode);
		boolean townEmpty = "".equals(town);
		boolean phonenumberEmpty = "".equals(phonenumber);
		
		if(emailEmpty || passwordEmpty || firstnameEmpty || surnameEmpty || streetAddressEmpty || postcodeEmpty ||townEmpty || phonenumberEmpty){
			if (emailEmpty){
				flash().put("email-empty", "yes");
			}
			if (passwordEmpty){
				flash().put("password-empty", "yes");
			}
			if (firstnameEmpty){
				flash().put("firstname-empty", "yes");
			}
			if (surnameEmpty){
				flash().put("surname-empty", "yes");
			}
			if (streetAddressEmpty){
				flash().put("street-address-empty", "yes");
			}
			if (firstnameEmpty){
				flash().put("postcodeEmpty-empty", "yes");
			}
			if (firstnameEmpty){
				flash().put("postcode-empty", "yes");
			}
			if (townEmpty){
				flash().put("town-empty", "yes");
			}
			if (phonenumberEmpty){
				flash().put("phonenumber-empty", "yes");
			}
			return redirect(routes.UserController.createUserForm()); 
		}
		
		User user = new User(email, password, firstname, surname,
				streetAddress, postcode, town, phonenumber);
		JPA.em().persist(user);
		return ok(readuser.render(user));
	}

	@Transactional
	public static Result loginUser() {
		if (session().containsKey("user")) {
			String email = session().get("user"); 
			flash().put("already-logged-in", "yes");
			TypedQuery<User> query = JPA.em().createQuery(
					"SELECT c FROM User c WHERE c.email = :email", User.class);
			query.setParameter("email", email);
			
			User user = query.getSingleResult();

			return ok(readuser.render(user));
		}
		return ok(userloginform.render());
	}

	@Transactional
	public static Result loginUserValidate(){
		if (session().containsKey("username")){
			session().clear(); 
		}
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String email = form.get("email")[0];
		String password = form.get("password")[0];

		boolean emailIsEmpty = "".equals(email);
		boolean passwordIsEmpty = "".equals(password);

		if (emailIsEmpty || passwordIsEmpty) {
			if (emailIsEmpty) {
				flash().put("email-empty", "yes");
			}
			if (passwordIsEmpty) {
				flash().put("password-empty", "yes");
			}

			return redirect(routes.UserController.loginUser());
		}

		TypedQuery<User> query = JPA
				.em()
				.createQuery(
						"SELECT c FROM User c WHERE c.email = :email AND c.password = :password",
						User.class);
		query.setParameter("email", email);
		query.setParameter("password", password);

		List<User> matchingUsers = query.getResultList();

		if (matchingUsers.size() == 1) {
			session().put("user", email); 
			User user = matchingUsers.get(0); 	
			return ok(userlogin.render(user));
			
		} else {
			flash().put("no-username-password-match", "yes");
			return redirect(routes.UserController.loginUser());
		}
	}	
	
	@Transactional
	public static Result logoutUser() {
		if (session().containsKey("user")) {
			session().clear();
			return ok(startpage.render());
		}
		return ok(startpage.render());
	}
}
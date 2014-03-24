package controllers;

import java.util.List;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.readallcategories;
import views.html.readcategory;

public class CategoryController extends Controller {
	
	@Transactional
	public static Result readAllCategories() {
		List<models.Category> categories = JPA.em().createQuery("SELECT a FROM Category a", models.Category.class).getResultList(); 
		return ok(readallcategories.render(categories));
	}
	
	@Transactional
	public static Result readCategory(int id) {
		models.Category category = JPA.em().find(models.Category.class, id); 

		return ok(readcategory.render(category));
	}
}

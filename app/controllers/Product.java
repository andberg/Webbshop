package controllers;

import java.util.List;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.readallproducts;
import views.html.readproduct;

public class Product extends Controller {

	@Transactional
	public static Result readOneProduct(int id) {
		models.Product product = JPA.em().find(models.Product.class, id);

		return ok(readproduct.render(product));
	}
	
	@Transactional
	public static Result readAllProducts() {
		List<models.Product> products = JPA.em()
				.createQuery("SELECT a FROM Product a", models.Product.class)
				.getResultList();
		return ok(readallproducts.render(products));
	}
}
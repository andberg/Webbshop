package controllers;

import java.util.List;
import java.util.Map;

import models.Product;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.readallproducts;
import views.html.readproduct;
import views.html.showcreateproductform;

public class ProductController extends Controller {

	@Transactional
	public static Result readOneProduct(int id) {
		
		
		Product product = JPA.em().find(Product.class, id);
		return ok(readproduct.render(product));
	}
	
	@Transactional
	public static Result readAllProducts() {
		List<Product> products = JPA.em().createQuery("SELECT c FROM Product c", Product.class).getResultList();

		return ok(readallproducts.render(products));
	}
	
	@Transactional
	public static Result createProduct(){
		return ok(showcreateproductform.render()); 
	}
	
//	@Transactional
//	public static Result createProduct(){
//		Map<String, String[]> form = request().body().asFormUrlEncoded(); 
//		String name = form.get("name")[0]; 
//		
//		models.Product product = new models.Product(); 
//		
//		JPA.em().persist(product); 
//		return redirect(routes.DefaultController.index()); 
//	}
	
}
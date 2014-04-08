package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.Category;
import models.Product;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminlogin;
import views.html.deleteproductform;
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
	@Security.Authenticated
	public static Result createProductForm() {
		List<Category> categories = JPA.em()
				.createQuery("SELECT a from Category AS a", Category.class)
				.getResultList();

		return ok(showcreateproductform.render(categories));
	}

	@Transactional
	@Security.Authenticated
	public static Result createProduct() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String name = form.get("name")[0];
		String description = form.get("description")[0];
		double cost = Double.parseDouble(form.get("cost")[0]);
		double RRP = Double.parseDouble(form.get("RRP")[0]);
		List<Category> categories = new ArrayList<Category>();
		String[] formArray = form.get("category");

		for (int i = 0; i < formArray.length; i++) {
			int categoryId = Integer.parseInt(formArray[i]);

			Category category = JPA.em().find(Category.class, categoryId);
			categories.add(category);
		}

		Product product = new Product(name, description, cost, RRP, categories);
		JPA.em().persist(product);
		String message = "Product " + product.getName() + " created!";
		return ok(adminlogin.render(message));
	}

	@Transactional
	@Security.Authenticated
	public static Result deleteProductForm() {
		List<Product> products = JPA.em()
				.createQuery("SELECT a from Product AS a", Product.class)
				.getResultList();
		return ok(deleteproductform.render(products));
	}

	@Transactional
	@Security.Authenticated
	public static Result deleteProduct() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();

		String[] productsId = form.get("products");

		if (productsId == null) {
			return redirect(routes.ProductController.deleteProductForm());
		}

		for (int i = 0; i < productsId.length; i++) {

			int productId = Integer.parseInt(productsId[i]);
			TypedQuery<Product> query = JPA.em().createQuery(
					"SELECT c FROM Product c WHERE c.id = :productId",
					Product.class);
			Product product = query.setParameter("productId", productId)
					.getSingleResult();
			JPA.em().remove(product);
		}
		return ok(adminlogin.render("Products deleted"));
	}

}
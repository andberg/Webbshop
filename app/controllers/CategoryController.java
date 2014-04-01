package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.Category;
import models.Product;
import models.Staff;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminlogin;
import views.html.createcategoryform;
import views.html.deletecategoryform;
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
	
	@Transactional
	@Security.Authenticated
	public static Result createCategoryForm() {
		List<Staff> staffs = JPA.em()
				.createQuery("SELECT a from Staff AS a", Staff.class)
				.getResultList();
		List<Product> products = JPA.em()
				.createQuery("SELECT a from Product AS a", Product.class)
				.getResultList();
		return ok(createcategoryform.render(staffs, products));
	}

	@Transactional
	@Security.Authenticated
	public static Result createCategory() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		String name = form.get("name")[0];
		int staffId = Integer.parseInt(form.get("staff")[0]);
		String[] formArray = form.get("products");

		List<Product> products = new ArrayList<Product>();

		Category category = new Category(name, staffId, products);
		JPA.em().persist(category);

		for (int i = 0; i < formArray.length; i++) {
			int productId = Integer.parseInt(formArray[i]);

			Product product = JPA.em().find(Product.class, productId);
			product.categories.add(category);
		}

		String message = "Category " + category.getName() + " created!";
		return ok(adminlogin.render(message));
	}

	@Transactional
	@Security.Authenticated
	public static Result deleteCategoryForm() {
		List<Category> categories = JPA.em()
				.createQuery("SELECT a from Category AS a", Category.class)
				.getResultList();
		return ok(deletecategoryform.render(categories));
	}

	@Transactional
	@Security.Authenticated
	public static Result deleteCategory() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();

		String[] categoriesId = form.get("categories");

		if (categoriesId == null) {
			return redirect(routes.CategoryController.deleteCategoryForm());
		}

		for (int i = 0; i < categoriesId.length; i++) {

			int categoryId = Integer.parseInt(categoriesId[i]);
			TypedQuery<Category> query = JPA.em().createQuery(
					"SELECT c FROM Category c WHERE c.id = :categoryId",
					Category.class);
			Category category = query.setParameter("categoryId", categoryId)
					.getSingleResult();
			JPA.em().remove(category);
		}
		return ok(adminlogin.render("Category deleted"));
	}
}

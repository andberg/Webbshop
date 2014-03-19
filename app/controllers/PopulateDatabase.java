package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Product;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.populatedatabase;

public class PopulateDatabase extends Controller {
	
	@Transactional
	public static Result populateDatabase() {
		
		Category category1 = new models.Category("Books", "Andrea", "Berglund"); 
		Category category2 = new models.Category("Toys", "Anders", "Berglund"); 
		Category category3 = new models.Category("Electronics", "Johan", "Berglund"); 
		
		JPA.em().persist(category1);
		JPA.em().persist(category2);
		JPA.em().persist(category3);
		
		List<Category> categories = new ArrayList <Category>();
		categories.add(category3); 
		categories.add(category1); 
		categories.add(category2); 
		
		Product product1 = new models.Product("Harry Potter och den vises sten","New book by JK Rowling", 200.50, 278.00, categories); 
		Product product2 = new models.Product("Bamse i Trollskogen", "Den bästa bamseboken i stan", 180.50, 239.00, categories); 
		Product product3 = new models.Product("Att angöra en brygga", "Fantastiska filmen med Gösta Ekman, Monika Zetterlund mfl", 100.50, 150.00, categories); 
		
		JPA.em().persist(product1);
		JPA.em().persist(product2);
		JPA.em().persist(product3);
		
		models.User user1 = new models.User("andreaberglund@hotmail.com", "password",
				"Andrea", "Berglund", "Fogdevägen 44", "12841",
				"Bagarmossen", "070989898"); 
		
		models.User user2 = new models.User("blgnse@hotmail.com", "password", "Anders",
				"Hultqvist", "Fogdevägen 44", "12841", "Bagarmossen",
				"9090");
		
		JPA.em().persist(user1);
		JPA.em().persist(user2);
	
		
		return ok(populatedatabase.render());
	}
	
}

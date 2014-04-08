package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.Cart;
import models.Order;
import models.Product;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.readproduct;
import views.html.viewcartanduser;
import views.html.vieworder;
import views.html.viewallorders;

public class OrderAndCartController extends Controller {

	@Transactional
	@Security.Authenticated(UserAuthenticator.class)
	public static Result viewCart() {

		String userEmail = session().get("user");
		TypedQuery<User> query = JPA.em().createQuery(
				"SELECT c FROM User c WHERE c.email = :email", User.class);
		User user = query.setParameter("email", userEmail).getSingleResult();

		int userId = user.getId();
		TypedQuery<Cart> query2 = JPA.em().createQuery(
				"SELECT c FROM Cart c WHERE c.userId = :userId", Cart.class);
		query2.setParameter("userId", userId);
		List<Cart> usersCarts = new ArrayList<Cart>();
		usersCarts = query2.getResultList();

		return ok(viewcartanduser.render(usersCarts, user));
	}

	@Transactional
	@Security.Authenticated(UserAuthenticator.class)
	public static Result addProductToCart() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
	
		String userEmail = form.get("user")[0];
		int productId = Integer.parseInt(form.get("product")[0]);
		int quantity = Integer.parseInt(form.get("quantity")[0]);
	
		TypedQuery<User> query = JPA.em().createQuery(
				"SELECT c FROM User c WHERE c.email = :email", User.class);
		User user = query.setParameter("email", userEmail).getSingleResult();
		Product product = JPA.em().find(Product.class, productId);
	
		Cart cart = new Cart(user.getId(), productId, quantity);
	
		JPA.em().persist(cart);
		flash().put("item-purchased", "yes");
		return ok(readproduct.render(product));
	}

	@Transactional
	@Security.Authenticated(UserAuthenticator.class)
	public static Result deleteFromCart() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		int cartId = Integer.parseInt(form.get("cartId")[0]);
		TypedQuery<Cart> query = JPA.em().createQuery(
				"SELECT c FROM Cart c WHERE c.id = :cartId", Cart.class);
		Cart cart = query.setParameter("cartId", cartId).getSingleResult();

		JPA.em().remove(cart);
		flash().put("deleted", "yes");

		String userEmail = session().get("user");
		TypedQuery<User> query3 = JPA.em().createQuery(
				"SELECT c FROM User c WHERE c.email = :email", User.class);
		User user = query3.setParameter("email", userEmail).getSingleResult();

		int userId = user.getId();

		TypedQuery<Cart> query2 = JPA.em().createQuery(
				"SELECT c FROM Cart c WHERE c.userId = :userId", Cart.class);
		query2.setParameter("userId", userId);
		List<Cart> usersCarts = new ArrayList<Cart>();
		usersCarts = query2.getResultList();
		return ok(viewcartanduser.render(usersCarts, user));
	}

	@Transactional
	@Security.Authenticated(UserAuthenticator.class)
	public static Result placeOrder() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		int userId = Integer.parseInt(form.get("user-id")[0]);

		User user = JPA.em().find(User.class, userId);

		TypedQuery<Cart> query2 = JPA.em().createQuery(
				"SELECT c FROM Cart c WHERE c.userId = :userId", Cart.class);
		query2.setParameter("userId", userId);
		List<Cart> usersCarts = new ArrayList<Cart>();
		usersCarts = query2.getResultList();

		List<Order> orders = new ArrayList<Order>();

		for (Cart cart : usersCarts) {
			int orderUserId = cart.getUserId();
			int orderProductId = cart.getProductId();
			int orderQuantity = cart.getQuantity();

			Order order = new Order(orderUserId, orderProductId, orderQuantity);
			orders.add(order);
			JPA.em().persist(order);
			JPA.em().remove(cart);
		}
		flash().put("order-send", "yes"); 
		return ok(vieworder.render(user, orders));
	}

	@Transactional
	@Security.Authenticated(UserAuthenticator.class)
	public static Result viewOrders() {
		
		String userEmail = session().get("user");
		TypedQuery<User> query = JPA.em().createQuery(
				"SELECT c FROM User c WHERE c.email = :email", User.class);
		User user = query.setParameter("email", userEmail).getSingleResult();

		int userId = user.getId();
		
		TypedQuery<Order> query2 = JPA.em().createQuery(
				"SELECT c FROM Order c WHERE c.userId = :userId", Order.class);
		query2.setParameter("userId", userId);
		List<Order> orders = new ArrayList<Order>();
		orders = query2.getResultList(); 
		
		return ok(vieworder.render(user, orders));
	}

	@Transactional
	@Security.Authenticated
	public static Result viewAllOrders(){
		TypedQuery<Order> query2 = JPA.em().createQuery(
				"SELECT c FROM Order c", Order.class);
		List<Order> orders = new ArrayList<Order>();
		orders = query2.getResultList(); 
		
		return ok(viewallorders.render(orders)); 
		
	}
}

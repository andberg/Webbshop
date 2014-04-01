package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	int orderId; 
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "product_id")
	private int productId; 
	
	private int quantity;
	
	public Order(){
		
	}
	
	public Order(int orderId, int userId, int productId, int quantity){
		this.orderId = orderId; 
		this.userId = userId; 
		this.productId = productId;
		this.quantity = quantity; 
	}
	
	public Order(int userId, int productId, int quantity){
		this.userId = userId; 
		this.productId = productId;
		this.quantity = quantity; 
	}
	
	public int getOrderId() {
		return orderId;
	}

	public int getUserId() {
		return userId;
	}

	public int getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public String toString(){
		String order = "Order contains \n Users id: " + this.getUserId() + " \n Product id: " + this.getProductId()
				+ " \n Quantity: " + this.getQuantity(); 
		return order; 
	}
		
}

package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="carts")
public class Cart {
	
	@Id
	@GeneratedValue
	private int id; 
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "product_id")
	private int productId; 
	private int quantity;
	
	public Cart(){
		
	}
	public Cart(int userId, int productId, int quantity){
		this.userId = userId; 
		this.productId = productId; 
		this.quantity = quantity; 	
	}
	
	public Cart(int id, int userId, int productId, int quantity){
		this.id = id; 
		this.userId = userId; 
		this.productId = productId; 
		this.quantity = quantity; 	
	}
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString(){
		String thisCart = "This Entry: \n Product " + this.getProductId() + " Quantity " + this.getQuantity() 
				+ "\n"; 
		return thisCart; 
	}
}

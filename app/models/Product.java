package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public final class Product {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	private double cost;
	private double RRP;
	@ManyToMany
	private List<Category> categories;
	
	public Product() {
		
	}

	public Product(int id, String name, double RRP) {
		this.id = id;
		this.name = name;
		this.description = null;
		this.cost = 0;
		this.RRP = RRP;
		this.categories = null;
	}

	public Product(int id, String name, String description, double cost,
			double RRP, List<Category> categories) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.RRP = RRP;
		this.categories = categories;
	}

	public Product(String name, String description, double cost, double RRP,
			List<Category> categories) {
		this.id = 0;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.RRP = RRP;
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Product ID: " + getId() + "\nName: " + getName()
				+ "\nDescription: " + getDescription() + "\nCost: " + getCost()
				+ "\nRRP: " + getRRP() + "\nCategories: " + getCategories()
				+ "\n";
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getRRP() {
		return RRP;
	}

	public double getCost() {
		return cost;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public boolean addCategory(Category category) {
		if (this.categories.add(category)) {
			return true;
		} else {
			return false;
		}
	}
}

package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue
	private int id; 
	private String name; 
	
	@Column(name="staff_responsible")
	private int staffResponsible; 
	
	@ManyToMany(mappedBy="categories") 
	public List<models.Product> products;
	
	public Category() {
			
	}
	
	public Category(String name, int staffResponsible){
		this.setName(name); 
		this.setStaffResponsible(staffResponsible);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStaffResponsible() {
		return staffResponsible; 
	}

	public void setStaffResponsible(int staffResponsible) {
		this.staffResponsible = staffResponsible;
	}

	@Override
	public String toString(){
		return getName(); 
	}

}

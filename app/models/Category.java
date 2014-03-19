package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
	@Id
	@GeneratedValue
	private int id; 
	private String name; 
	private String staffResponsibleFirstname; 
	private String staffResponsibleSurname;
	
	public Category() {
			
	}
	
	public Category(String name, String staffResponsibleFirstname, String staffResponsibleSurname){
		this.setName(name); 
		this.setStaffResponsibleFirstname(staffResponsibleFirstname); 
		this.setStaffResponsibleSurname(staffResponsibleSurname); 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffResponsibleFirstname() {
		return staffResponsibleFirstname;
	}

	public void setStaffResponsibleFirstname(String staffResponsibleFirstname) {
		this.staffResponsibleFirstname = staffResponsibleFirstname;
	}

	public String getStaffResponsibleSurname() {
		return staffResponsibleSurname;
	}

	public void setStaffResponsibleSurname(String staffResponsibleSurname) {
		this.staffResponsibleSurname = staffResponsibleSurname;
	}
	
	@Override
	public String toString(){
		return this.getName(); 
	}

}

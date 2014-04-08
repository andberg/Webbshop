package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="staff")
public class Staff {
	@Id
	@GeneratedValue
	private int id; 
	
	@Column(name="username")
	public String username; 
	@Column(name="password")
	public String password; 
	private String firstname; 
	private String surname; 
	private String dob; 
	@Column(name="street_address")
	private String streetAddress; 
	private String town; 
	private String postcode; 
	private String mobile; 
	private String email; 
	private int salary;

	public Staff(int id, String username, String password, String firstname,
			String surname, String dob, String streetAddress, String town,
			String postcode, String mobile, String email, int salary) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.surname = surname;
		this.dob = dob;
		this.streetAddress = streetAddress;
		this.town = town;
		this.postcode = postcode;
		this.mobile = mobile;
		this.email = email;
		this.salary = salary;
	}
	public Staff() {
		
	}

	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	} 

}

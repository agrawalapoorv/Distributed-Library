package edu.sjsu.digitalLibrary.prj.models;


import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class RegistrationJsonPojo implements Serializable {
private static final long serialVersionUID = 1L;

private String userName;
private String dob;
private String emailId;
private String phone;
private String category;
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getParentId() {
	return parentId;
}
public void setParentId(String parentId) {
	this.parentId = parentId;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getAptNo() {
	return aptNo;
}
public void setAptNo(String aptNo) {
	this.aptNo = aptNo;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getZipcode() {
	return zipcode;
}
public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
}
public String getUserPassword() {
	return userPassword;
}
public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}
public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}
private String parentId;
private String street;
private String aptNo;
private String city;
private String state;
private String country;
private String zipcode;
private String userPassword;
private String confirmPassword;
private int regionId;
public int getRegionId() {
	return regionId;
}
public void setRegionId(int regionId) {
	this.regionId = regionId;
}


}

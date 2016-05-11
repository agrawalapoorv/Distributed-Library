package edu.sjsu.digitalLibrary.prj.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Registration {
private static final long serialVersionUID = 1L;

@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private int userId;


@Column(name = "emailId", length  = 50)
private String emailId;

@Column(name = "name", length = 100)
private String userName;


@Column(name = "phone", length = 45)
private String phone;

@Column(name = "dob")
private String dob;



@Column(name = "category", length = 45)
private String category;

@Column(name = "parentId")
private String parentId;


@Column(name = "active")
private int activeUser;

@Column(name = "Password", length = 100)
private String userPassword ;


private String confirmPassword ;


public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getDob() {
	return dob;
}

public void setDob(String dob) {
	this.dob = dob;
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

public int getActiveUser() {
	return activeUser;
}

public void setActiveUser(int activeUser) {
	this.activeUser = activeUser;
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

///////////address
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
   private int id;
   
  
	

	@Column(name = "userId", length  = 50)
   private String addressUserId;
   
   @Column(name = "street", length = 100)
   private String street;
   
   @Column(name = "aptNo", length = 45)
   private String aptNo;

   @Column(name = "city", length = 100)
   private String city;
   
   @Column(name = "state", length = 100)
   private String state;
   
   @Column(name = "country", length = 100)
   private String country;
   
   @Column(name = "zip", length = 45)
   private String zip;
   
   @Column(name = "attachmentId", length = 100)
   private String attachmentId;
   
   @Column(name = "active")
   private int active;
   
   @Column(name = "regionid")
   private int regionid;
   
   
   public int getRegionid() {
	return regionid;
}

public void setRegionid(int regionid) {
	this.regionid = regionid;
}

public void setAddressUserId(String addressUserId) {
	this.addressUserId = addressUserId;
}

public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddressUserId() {
		return addressUserId;
	}

	public void setUserId(String addressUserId) {
		this.addressUserId = addressUserId;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public int getActive() {
		return active;	
	}

	public void setActive(int active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

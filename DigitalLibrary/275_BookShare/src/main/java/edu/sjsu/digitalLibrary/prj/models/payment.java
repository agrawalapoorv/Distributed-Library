package edu.sjsu.digitalLibrary.prj.models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class payment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
    
    @Column(name = "userId")
    private int userId;
    
    @Column(name = "cardNumber")
    private int cardNumber;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "cvv")
    private String cvv;
    
    @Column(name = "validTillMonth")
    private String validTillMonth;
    
    @Column(name = "validTillYear")
    private String validTillYear;
    
    @Column(name = "cardType")
    private String cardType;
    
    @Column(name = "active")
    private int active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getValidTillMonth() {
		return validTillMonth;
	}

	public void setValidTillMonth(String validTillMonth) {
		this.validTillMonth = validTillMonth;
	}


	public String getValidTillYear() {
		return validTillYear;
	}

	public void setValidTillYear(String validTillYear) {
		this.validTillYear = validTillYear;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

package edu.sjsu.digitalLibrary.prj.dao;

import java.util.List;

import edu.sjsu.digitalLibrary.prj.dataoperations.DBCrud;

import edu.sjsu.digitalLibrary.prj.models.payment;

public class JPAPaymentDAO {
	
	
	public int insert(payment payment) 
	{
		System.out.println("in payment jpa");
		int addressId= 0;
		try {
			DBCrud<payment> db = new DBCrud<payment>();
			addressId = db.Insert(payment);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return addressId;
	}

	
	/*
	  * function to update address tuple n the database 
	  * 
	  */
	public void update(payment category) {
		
		
				try {
					DBCrud<payment> db = new DBCrud<payment>();
					db.update(category);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
	}

	
	/*
	 * Function to delete address from databse
	 * 
	 */
	public void delete(payment category) {
		
		
		
		try {
			DBCrud<payment> db = new DBCrud<payment>();
			db.delete(category);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}


	public payment get(int userId) {
		
		payment result = null;
		
		try {
			DBCrud<payment> db = new DBCrud<payment>();
			result = db.getPaymentDetails(userId);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}

		return result;
	}


	

	
	

}

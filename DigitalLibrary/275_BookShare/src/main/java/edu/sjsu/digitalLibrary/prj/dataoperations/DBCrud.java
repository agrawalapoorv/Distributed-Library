package edu.sjsu.digitalLibrary.prj.dataoperations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;

import edu.sjsu.digitalLibrary.prj.models.BookId;
import edu.sjsu.digitalLibrary.prj.models.Login;
import edu.sjsu.digitalLibrary.prj.models.Tokens;
import edu.sjsu.digitalLibrary.prj.models.address;
import edu.sjsu.digitalLibrary.prj.models.category;
import edu.sjsu.digitalLibrary.prj.models.order;
import edu.sjsu.digitalLibrary.prj.models.payment;
import edu.sjsu.digitalLibrary.prj.models.region;
import edu.sjsu.digitalLibrary.prj.models.requestQueue;
import edu.sjsu.digitalLibrary.prj.models.requestQueueCount;
import edu.sjsu.digitalLibrary.prj.models.requestbook;
import edu.sjsu.digitalLibrary.prj.models.subbook;
import edu.sjsu.digitalLibrary.prj.models.user;
import edu.sjsu.digitalLibrary.prj.models.utilityClass;

public class DBCrud<T> {
	Session session;
	SessionFactory s;
	public DBCrud(){}
	
	public int Insert(T obj){
		System.out.println("in crud");
		int id = 0;

		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		session.save(obj);
		session.getTransaction().commit();
		if(obj instanceof user){
			user p = (user)obj;
			id = p.getId();
		}
		else if(obj instanceof address){
			address s = (address)obj;
			id = s.getId();
			System.out.println("in crud address " + id);
		}
		else if(obj instanceof category){
			category s = (category)obj;
			id = s.getId();
			System.out.println("in crud category " + id);
		}
		
		else if(obj instanceof requestbook){
			System.out.println("in jpa of req.book");
			requestbook s = (requestbook)obj;
			id = s.getRequestId();
			System.out.println("in crud book " + id);
		}
		
		
		else if(obj instanceof order){
			System.out.println("in jpa of order");
			order s = (order)obj;
			id = s.getId();
			System.out.println("in crud order " + id);
		}
		
		else if(obj instanceof Tokens){
			System.out.println("in DBCrud insert token");
			Tokens t = (Tokens)obj;
			id = t.getId();
			System.out.println("Id of token: " + id);
		}
		else if(obj instanceof requestQueue){
			System.out.println("in jpa of order");
			requestQueue s = (requestQueue)obj;
			id = s.getId();
			System.out.println("in crud order " + id);
		}
		else if(obj instanceof payment){
			System.out.println("in jpa of payment");
			payment s = (payment)obj;
			id = s.getId();
			System.out.println("in crud payment " + id);
		}
		session.close();
		s.close();
		
		return id;
	}
	
	// Apoorv --- Customer choice books
	

	
	
	//Apoorv Notification Code Start	
	

	
	public user getUserDetails(int userId){
		System.out.println(" in all usertable table " + userId );
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from BookShareDB.user where id=:sCode1").addEntity(user.class).setParameter("sCode1", userId);
		user  result = new user();
		if(query.list().size()==0)
        {
        	 return null;
        }
        else
        {
        	System.out.println("enter the else in the getUserDetails function");
        	 result = (user)query.list().get(0);
        }

		System.out.println(" value of result in DBCrud" + result);
		session.close();
		s.close();
		return result;
	}
	
	  public List<requestQueue> getRequestQueuedetails( String isbn,int userId)
	   {
		  System.out.println(isbn + "   " +userId);
			List<requestQueue> result = new ArrayList<requestQueue>();
			s = SessionFactoryObj.getSessionFactory();
			session = s.openSession();
			session.beginTransaction();
			Query query = session.createSQLQuery("select * from BookShareDB.requestQueue where isbn=:sCode2 and userid=:sCode1 and isOrdered=0").addEntity(requestQueue.class).setParameter("sCode1", userId).setParameter("sCode2",isbn);
			System.out.println("hello");
			result = (List<requestQueue>)query.list();
			session.close();
			s.close();		
			System.out.println("----" + result);
			return result;
	   }
	
	
	
 public List<order> getOrderInfo( int numberDays)
 {
		List<order> result = new ArrayList<order>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from BookShareDB.order where endDate=current_date() + "+numberDays+" ").addEntity(order.class);
		System.out.println("hello");
		result = (List<order>)query.list();
		session.close();
		s.close();		
		System.out.println("----" + result);
		return result;
 }
	
 
	
	public List<order> getCheckorderExtension(int bookId) {
		List<order> result = new ArrayList<order>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		System.out.println("Enter the getCheckorderExtension in DBCRud" + " **** "  + bookId);
		Query query = session.createSQLQuery("select * from BookShareDB.order where active=1 and bookId=:sCode1 and startDate between current_date() + 1 and current_date() + 7 ").addEntity(order.class).setParameter("sCode1", bookId);
		System.out.println("Enter the requestDetails in DBCRud");
		result = (List<order>)query.list();
		session.close();
		s.close();		
		System.out.println("----" + result);
		return result;
	}
	
	
	public void updateOrderExtension(int orderNo)
	{
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		System.out.println("Enter the getCheckorderExtension in DBCRud" + " **** "  + orderNo);
		Query updateQuery = session.createSQLQuery("update BookShareDB.order set endDate=current_date() + 7 where id=:sCode").addEntity(order.class).setParameter("sCode", orderNo);
		System.out.println("Enter the updateOrderExtension in DBCRud");
		System.out.println(updateQuery);
		int result = updateQuery.executeUpdate();
		System.out.println(result);
		session.getTransaction().commit();
		session.close();
		s.close();		

	}
	
	
	
	@SuppressWarnings("unchecked")
	public T get(T obj, int id){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		T newR;
		newR = (T)session.get(obj.getClass(), id);
		session.close();
		s.close();
		return newR;
	}
	
	public void update(T obj){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		session.update(obj);
		session.getTransaction().commit();
		session.close();
		s.close();
	}
	
	
	public void delete(T obj){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		session.delete(obj);
		session.getTransaction().commit();
		session.close();
		s.close();
	}	
	
	
	public void updateUserPassword(int userid, String password){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();

		int result=0;
		Query updateQuery = session.createSQLQuery(
				"update user set password= :sCode where id = :jCode")
				.setParameter("sCode", password)
				.setParameter("jCode", userid);
				
				result = updateQuery.executeUpdate();
				System.out.println(result);
				session.getTransaction().commit();
		
		
		session.close();
		s.close();
		
		System.out.println("----" + result);
	}
	
	public int getExistingEmail(String emailId){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(
				"select * from user  where EmailID = :sCode")
				.addEntity(user.class)
				.setParameter("sCode", emailId);
				int  result = query.list().size();
		session.close();
		s.close();
		System.out.println("----" + result);
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public Tokens getTokenData(int userid){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();

		int result=0;
		Query query = session.createSQLQuery("select * from Tokens where userid = :sCode")
				.addEntity(Tokens.class)
				.setParameter("sCode", userid);
		
		result = query.list().size();
		Tokens token = null;
		if( result !=0 ){
			token = new Tokens();
			token.setId(((Tokens)query.list().get(0)).getId());
			token.setUserid(((Tokens)query.list().get(0)).getUserid());
			token.setToken(((Tokens)query.list().get(0)).getToken());
			token.setExpiry_date(((Tokens)query.list().get(0)).getExpiry_date());
		}
		session.close();
		s.close();
		
		System.out.println("----" + result);
		return token;
	}

		
	@SuppressWarnings("unchecked")
	public List<category> getCategories(){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();

		Query query = session.createSQLQuery("select * from category where active = 1").addEntity(category.class);
		
		List<category> result = (List<category>)query.list();
		session.close();
		s.close();
		
		System.out.println("----" + result);
		return result;
	}
	
	
	public int validate(Login login){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		
		System.out.println("sCode"+ login.getUserEmail());
		System.out.println("jCode"+ login.getPassword());
		
		int result,returnedId = 0;
		boolean activateUser = false;
		Query query = session.createSQLQuery(
				"select * from user  where emailId = :sCode and password = :jCode")
				.addEntity(user.class)
				.setParameter("sCode", login.getUserEmail())
				.setParameter("jCode", login.getPassword());
		
		
				result = query.list().size();
				if(result!=0)
				{
				returnedId = ((user)query.list().get(0)).getId();
				int active = ((user)query.list().get(0)).getActive();
				if(active==0){
					activateUser = true;
				}
				System.out.println("returned id" + returnedId);
				}
				
				result=0;
				
		if(activateUser){
			
			Query updateQuery = session.createSQLQuery(
					"update user set active=1 where id = :sCode")
					.addEntity(user.class)
					.setParameter("sCode", returnedId);
					
					result = updateQuery.executeUpdate();
					System.out.println(result);
					session.getTransaction().commit();
				
		}
				
				
		
		session.close();
		s.close();
	
		return returnedId;
	}
	
	
	
	
	public JSONObject validateActivation(Login login){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		
		System.out.println("sCode"+ login.getUserEmail());
		
		int result,returnedId = 0;
		JSONObject obj = new JSONObject();
		Query query = session.createSQLQuery(
				"select * from user  where emailId = :sCode and active = 0")
				.addEntity(user.class)
				.setParameter("sCode", login.getUserEmail());
		
		
				result = query.list().size();
				if(result!=0)
				{
				obj.put("flag", "N");
				obj.put("activationCode", ((user)query.list().get(0)).getActivationCode());
				((user)query.list().get(0)).getActivationCode();
				System.out.println("returned id" + returnedId);
				}else{
					obj.put("flag", "Y");
					obj.put("activationCode", "none");
				}
		
		session.close();
		s.close();
	
		return obj;
	}
	
	public JSONObject checkUser(Login login){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		
		System.out.println("sCode"+ login.getUserEmail());
		
		int result;
		JSONObject obj = new JSONObject();
		Query query = session.createSQLQuery(
				"select * from user  where emailId = :sCode")
				.addEntity(user.class)
				.setParameter("sCode", login.getUserEmail());
		
		
				result = query.list().size();
				if(result!=0)
				{
				obj.put("username", ((user)query.list().get(0)).getName());
				obj.put("userid", ((user)query.list().get(0)).getId());
				obj.put("isUserInTheSystem", true);
				
				
				}else{
					obj.put("username", "none");
					obj.put("userid", 0);
					obj.put("isUserInTheSystem", false);
				}
		
		session.close();
		s.close();
	
		return obj;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<requestbook> getRequestdetails() {
		List<requestbook> result = new ArrayList<requestbook>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from requestbook where ParentId=:sCode").addEntity(requestbook.class).setParameter("sCode", 0);
		System.out.println("helo");
		result = (List<requestbook>)query.list();
		session.close();
		s.close();		
		System.out.println("----" + result);
		return result;
	}
	
	
	public int getLatestMongoBookId(){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(
				"select id from BookId")
				.addEntity(BookId.class);
				
				int  result = Integer.parseInt( query.list().get(0).toString());
		session.close();
		s.close();
		
		return result;
	}
	
	public int getExistingCategory(String category){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(
				"select * from category where name = :sCode")
				.addEntity(category.class)
				.setParameter("sCode", category);
				int  result = query.list().size();
		session.close();
		s.close();
		System.out.println("category found----" + result);
		return result;
	}

	public int getOrderCount(int userID ){
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(
				"select * from BookShareDB.order where userId = :sCode")
				.addEntity(order.class)
				.setParameter("sCode", userID);
				int  result = query.list().size();
		session.close();
		s.close();
		System.out.println("category found----" + result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<subbook> getSubBookdetails(int bookId) {
		List<subbook> result = new ArrayList<subbook>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		System.out.println("Enter the getSubBookdetails in DBCRud" + " **** "  + bookId);
		Query query = session.createSQLQuery("select * from subbook where parentId=:sCode").addEntity(subbook.class).setParameter("sCode", bookId);
		System.out.println("Enter the requestDetails in DBCRud");
		result = (List<subbook>)query.list();
		session.close();
		s.close();		
		System.out.println("----" + result);
		return result;
	}
	
	// Apoorv maxUserOrder
	
	public List<payment> userPaymentDetails(int userId) {
		List<payment> result = new ArrayList<payment>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		System.out.println("Enter the getSubBookdetails in DBCRud" + " **** "  + userId);
		Query query = session.createSQLQuery("select * from payment where userId=:sCode").addEntity(payment.class).setParameter("sCode", userId);
		System.out.println("Enter the requestDetails in DBCRud");
		result = (List<payment>)query.list();
		session.close();
		s.close();		
		System.out.println("----" + result);
		return result;
	}
	
	public region getRegionInfo(int regionId){
		System.out.println(" in all Order table " + regionId );
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from BookShareDB.region where id=:sCode").addEntity(region.class).setParameter("sCode", regionId);

		region  result = new region();
		if(query.list().size()==0)
        {
        	 return null;
        }
        else
        {
        	System.out.println("enter the else in the region function");
        	 result = (region)query.list().get(0);
        }

		System.out.println(" value of result in DBCrud" + result);
		session.close();
		s.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<order> getBookAvailability(int bookId){
		System.out.println(" in all Order table " + bookId );
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from BookShareDB.order where bookId=:sCode and active='1' and endDate>current_date").addEntity(order.class).setParameter("sCode", bookId);

		List<order>  result = new ArrayList<order>();
		if(query.list().size()==0)
        {
        	 return null;
        }
        else
        {
        	 result = (List<order>)query.list();
        }

		System.out.println(" value of result in DBCrud" + result);
		session.close();
		s.close();
		return result;
	}
	
	
	
	public List<region> getAllRegions(String city){
		List<region> r = new ArrayList<region>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from BookShareDB.region where city=:sCode and active='1'").addEntity(region.class).setParameter("sCode", city);

		
		r = (List<region>)query.list();
       
		session.close();
		s.close();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public int checkUserActiveOrders(int userId){
		
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from `order` where userId=:sCode and active=1").addEntity(order.class).setParameter("sCode", userId);
		
		if(query.list().size()==0)
        {
        	 return 0;
        }
        else
        {
        	return query.list().size();
        }

	}
	
	@SuppressWarnings("unchecked")
	public List<order> getAllUserOrders(int userId){
		List<order> r = new ArrayList<order>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		System.out.println(userId);
		Query query = session.createSQLQuery("select * from `order` where userId=:sCode ORDER BY Active desc").addEntity(order.class).setParameter("sCode", userId);
		
		
		if(query.list().size() > 0)
		{
			
			r=  (List<order>)query.list();
		}
		else
			r=null;
		
		session.close();
		s.close();
		
		return r;
	}
	@SuppressWarnings("unchecked")
	public int getBookParentId(int bookId) {
		subbook result = new subbook();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		
		Query query = session.createSQLQuery("select * from subbook where id=:sCode").addEntity(subbook.class).setParameter("sCode", bookId);
		
		result = (subbook)query.list().get(0);
		session.close();
		s.close();		
		int id = Integer.parseInt(result.getParentId() + "");
		return id;
	}
	
	/*
	 * Added by Jinal for requestQueue
	 */
	public List<requestQueueCount> getRequestQueueInfo() {
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(
				"Select * from (SELECT q.isbn,count(q.isbn) as countReq,u.regionid FROM BookShareDB.requestQueue q, BookShareDB.user u where q.userid = u.id and isOrdered = 0 group by q.isbn,u.regionid) res where res.countReq > 3");
		List<Object[]> results = (List<Object[]>) query.list();
		List<requestQueueCount> listRC = new ArrayList<requestQueueCount>();
		for (Object[] result : results) {
			requestQueueCount rc = new requestQueueCount();
			rc.setIsbn(result[0].toString());
			rc.setCountReq(Integer.valueOf(result[1].toString()));
			rc.setRegionId(Integer.valueOf(result[2].toString()));
			listRC.add(rc);
		}

		session.close();
		s.close();
		System.out.println("Count found----" + listRC);
		return listRC;
	}

	public List<requestQueue> getRequestQueueInfoByISBNRegion(String isbn, int regionid) {
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select rq.* from requestQueue rq, user u where rq.isbn = '" + isbn
				+ "' and rq.userid = u.id and u.regionid= '" + regionid
				+ "' and rq.isOrdered = 0 order by rq.requestTime").addEntity(requestQueue.class);
		List<requestQueue> results = (List<requestQueue>) query.list();

		session.close();
		s.close();
		System.out.println("Count found----" + results);
		return results;
	}
	
	public List<region> getAllRegionsAJAX(){
		List<region> r = new ArrayList<region>();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("select * from BookShareDB.region where active='1'").addEntity(region.class);
		r = (List<region>)query.list();
		session.close();
		s.close();
		return r;
	}
	

	
	public void updateUserCredit(int userId,float credit)
	{
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		System.out.println("Enter the getCheckorderExtension in DBCRud" + " **** "  + userId);
		Query updateQuery = session.createSQLQuery("update BookShareDB.user set creditScore=:sCode2 where id=:sCode1").addEntity(order.class).setParameter("sCode1", userId).setParameter("sCode2",credit);
		System.out.println("Enter the updateOrderExtension in DBCRud");
		System.out.println(updateQuery);
		int result = updateQuery.executeUpdate();
		System.out.println(result);
		session.getTransaction().commit();
		session.close();
		s.close();		

	}

	
	@SuppressWarnings("unchecked")
	public List<utilityClass> getAllBookDelay(){
		//List<utilityClass> r = new ArrayList<utilityClass>();
		
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();

Query query = session.createSQLQuery("SELECT userid,DATEDIFF(endDate,CURDATE()) as days from BookShareDB.order where active=1 and DATEDIFF(endDate,CURDATE()) < 0");
List<Object[]> results = (List<Object[]>) query.list();
List<utilityClass> listRC = new ArrayList<utilityClass>();
		if(results.size()!=0)
			{
				
			
				for (Object[] result : results) {
					System.out.println(result[0]);
					System.out.println(result[1]);
					utilityClass rc = new utilityClass();
					rc.setVar1(result[0].toString());
					rc.setVar3(result[1].toString());
					listRC.add(rc);
			                  }
				
			}
				else
				{
					listRC=null;
					
				}

		
		session.close();
		s.close();
		
		return listRC;
	}
	
	
	
	
	
	 @SuppressWarnings("unchecked")
	public List<utilityClass>getBookHighRating()
	 {
		
		System.out.println("DBCRUD ========= getBookHighRating");
		 //List<utilityClass> result = new ArrayList<utilityClass>();
			s = SessionFactoryObj.getSessionFactory() ;
			session = s.openSession();
			session.beginTransaction();
			Query query = session.createSQLQuery("select bookId ,avg(feedback) from BookShareDB.order group by bookId desc");
			List<Object[]> results = (List<Object[]>) query.list();
			System.out.println("hello");
			System.out.println("==============result"  + results);
			List<utilityClass> listRC = new ArrayList<utilityClass>();
			if(results.size()!=0)
			{
				
				
				for (Object[] result : results) {
					utilityClass rc = new utilityClass();
					rc.setVar1(result[0].toString());
					listRC.add(rc);
			                  }
				
			}
				else
				{
					listRC=null;
					
				}
	
			
			session.close();
			s.close();		
			System.out.println("UK -" + listRC.size() );
			return listRC;
	 }
	
	@SuppressWarnings("unchecked")
	public payment getPaymentDetails(int userId) {
		payment result = new payment();
		s = SessionFactoryObj.getSessionFactory();
		session = s.openSession();
		session.beginTransaction();
		
		Query query = session.createSQLQuery("select * from payment where userId=:sCode").addEntity(payment.class).setParameter("sCode", userId);
		
		if(query.list().size()!=0){
			result = (payment)query.list().get(0);
		}
		
		session.close();
		s.close();		
		
		return result;
	}
	
	
}
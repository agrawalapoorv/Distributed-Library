package edu.sjsu.digitalLibrary.prj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.digitalLibrary.prj.dao.*;
import edu.sjsu.digitalLibrary.prj.jsonview.Views;
import edu.sjsu.digitalLibrary.prj.models.*;
import edu.sjsu.digitalLibrary.prj.utils.*;
import edu.sjsu.digitalLibrary.prjservices.*;


@Controller
public class RequestBookController {

    @Autowired
   	private HttpSession httpSession;

   	@Autowired
   	private CheckSession sessionService;

    ///////Raunaq Code 05/05/2016
	  
   	@JsonView(Views.Public.class)
    @ResponseBody
   	@RequestMapping(value = "/checkOrders",method = RequestMethod.GET)
    public JsonResponse checkOrders() {
   		JsonResponse j = new JsonResponse();
   		if(sessionService.checkAuth()) {
   			
   	   	 	int checkActiveOrders=checkUserActiveOrders(Integer.parseInt(httpSession.getAttribute("USERID").toString()));
   	   	 	System.out.println("No's of orders:" + checkActiveOrders);
   	   	 	if(checkActiveOrders == 2)
   	   	 	{	
   	   	 		j.setSuccessFlag("N");
   	   	 		j.setErrorMessage("You already have two orders");
   	   	 	}

   	   	 	else
   	   	 	{
   	   	 		j.setSuccessFlag("Y");
   			 
   	   	 	}
   		
   		}
   		else
   		{
   			System.out.println("No session");
   			j.setSuccessFlag("L");
   			j.setErrorMessage("You are not logged in");
   			
   		}
	  return j;
   	}
///////Raunaq Code ends
   	
  @RequestMapping(value = "/requestbook/{bookId}",method = RequestMethod.GET)
  public Object bookAvailability(@PathVariable int bookId) {
	  System.out.println("enter bookavailabiltity");
	  int userID=Integer.parseInt(httpSession.getAttribute("USERID").toString());
	  if(!sessionService.checkAuth())
    	{
    			return "redirect:/";


    	} 
	  ModelAndView mv = new ModelAndView();
  	  JPARequestBookDAO j= new JPARequestBookDAO();
  	  int bookID=bookId; 
  	  String isbn= (String) httpSession.getAttribute("isbn");
  	  List<subbook> checkBookPresent= j.getBooksubId(bookID);
  	  List<requestQueue>checkRequestQueue=j.getRequestQueue(isbn,userID);
    // Apoorv Code  	
	  System.out.println("checkRequestQueue " + checkRequestQueue.size());
	
    	if(checkBookPresent.size()==0 && checkRequestQueue.size()==0)
    	{
    		RequestBookController n= new RequestBookController();
    		System.out.println("enter the check book available in the requestbook controller");
     	   requestQueue rq=new requestQueue();
     	   
     	    java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
     	    java.sql.Timestamp timestamp = new java.sql.Timestamp(new Date().getTime());
     		rq.setIsbn(isbn);
     		rq.setIsOrdered(0);
     		rq.setUserid(userID);
     		rq.setOrderDate(date);
     		rq.setRequestTime(timestamp);
     		int id = j.insert(rq);
     		System.out.println("value of the id in the requestQueue is" + id);
     		System.out.println(timestamp); //2014/08/06 15:59:48
     		
     		mv.addObject("bookAvailDetails", null);
       		mv.setViewName("requestdetails");
    	}
    	else
    	{

        	
        	List<bookAvail> bookAvailDetails =new ArrayList<bookAvail>();
        	bookAvailDetails=j.getBookOrderDetails(bookID);
        	String[] s = null;
        	Date dTemp = new Date();
        	String dateTempString="";
        	TimeZone zone = TimeZone.getTimeZone("GMT-8");
			  	DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		  		format.setTimeZone(zone);
        	for(bookAvail b : bookAvailDetails)
        	{
        		
        		System.out.println("Sub Book Id: " + b.getSubId());
        		System.out.println("Start Date: " + b.getStart_date());
        		
        		s = null;
        		 dTemp = new Date();
        		
        		if(b.getEnd_date() == null)
        		{
        				Calendar c = Calendar.getInstance();
	      			  	c.setTime(dTemp); 
	      			  	c.add(Calendar.DATE, 1); 
	      			  	String output = format.format(c.getTime());
	      			  	b.setSingle_start_date(output);
        		}
        		else
        		{
        			
        		  		s = b.getEnd_date().split(",");
            			try {
            				dateTempString = s[0];
            				if(dateTempString.contains("["))
            					dateTempString = dateTempString.replace("[", "");
            				
            				if(dateTempString.contains("]"))
            					dateTempString = dateTempString.replace("]", "");
            				
							dTemp = format.parse(dateTempString);
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
        			
        			  
        			  Calendar c = Calendar.getInstance();
        			  c.setTime(dTemp); 
        			  c.add(Calendar.DATE, 1); 
        			  String output = format.format(c.getTime());
        			  
        			  try {
							dTemp = format.parse(output);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			  
        			  b.setSingle_start_date(output);
        		}
        		
        		
        		System.out.println("End Date: " + b.getEnd_date());
        	}
        	//httpSession.setAttribute("bookSearched", bookAvailDetails);
        	
        	
        	if(bookAvailDetails.size() >0)
        		mv.addObject("bookAvailDetails", bookAvailDetails);
        	else
        		mv.addObject("bookAvailDetails", null);
      		mv.setViewName("requestdetails");
        	 
    	}

    	 return mv;
    }

  
  @RequestMapping(value = "/orderbook/{bookId}/{startDate}",method = RequestMethod.GET)
  public Object bookOrder(@PathVariable int bookId, @PathVariable String startDate) throws ParseException {
	  System.out.println("enter book order");
	 
	  if(!sessionService.checkAuth())
    	{
    			return "redirect:/";


    	} 
    	
	  int userId=Integer.parseInt(httpSession.getAttribute("USERID").toString());
	  TimeZone zone = TimeZone.getTimeZone("GMT-8");
	  DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
  		format.setTimeZone(zone);
	 
	  Date dStart = format.parse(startDate);
	  
	
	  
	  Calendar c = Calendar.getInstance();
	  c.setTime(dStart); 
	  c.add(Calendar.DATE, 7); 
	  String output = format.format(c.getTime());
	  
	  //@SuppressWarnings("deprecation")
	  Date dEnd = format.parse(output);
	  
	  String confNumber = "CN";
	  Random randomGenerator = new Random();
	  for(int x=0;x< 5; x++)
		  confNumber += randomGenerator.nextInt(9);
	  
	  	order o = new order();
	  	o.setActive(1);
	  	o.setBookId(bookId);
	  	o.setConfirmationNumber(confNumber);
	  	
	  	confNumber = "";
	  	randomGenerator = new Random();
		  for(int x=0;x< 5; x++)
			  confNumber += randomGenerator.nextInt(9);
	  	
		  o.setCode(confNumber);
	  	
    	
	  	c = Calendar.getInstance();
		  c.setTime(new Date()); 
		  
		  output = format.format(c.getTime());
		  java.util.Date date = null;
	  	try {
			date =	format.parse(output);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	  //	System.out.println(sqlDate);
	  	o.setDateOfOrder(sqlDate);
	  	
	  	java.sql.Date sqlDateEnd = new java.sql.Date(dEnd.getTime());
	  	//System.out.println(sqlDateEnd);
	  	o.setEndDate(sqlDateEnd);
	  	
	  	java.sql.Date sqlDateStart = new java.sql.Date(dStart.getTime());
	  	System.out.println(sqlDateStart);
	  	o.setStartDate(sqlDateStart);
	  	
	  	System.out.println(userId);
	  	o.setUserId(userId);
	  	
	  	
    	JPARequestBookDAO j= new JPARequestBookDAO();
    	int id = j.insert(o);
    	o.setId(id);
    	
    	
    	return "redirect:/showOrders/" + userId;
    	 

    }

  
  
  
  
  
 public boolean checkUserPaymentDetails(int userID) {
	 System.out.println("Enter the *****checkUserPaymentDetails  *****");
    JPARequestBookDAO i= new JPARequestBookDAO();
  	boolean userDetails=i.userPaymentDetails(userID);   // userDetails - true limit reached the count more than 2
  	//System.out.println("steeerrr"+str);
  	return userDetails;  
  }

 
///////Raunaq Code 05/05/2016
 public int checkUserActiveOrders(int userID) {
	 System.out.println("Enter the *****User Active Orders check  *****");
    JPARequestBookDAO jpaBookDao= new JPARequestBookDAO();
    return jpaBookDao.checkUserActiveOrders(userID);
  	
  	
  }
 
 
 
 @RequestMapping(value = "/showOrders/{userId}",method = RequestMethod.GET)
 public Object userOrders(@PathVariable int userId) throws ParseException {
	  
	 if (!sessionService.checkAuth()) {
		 System.out.println("invalid session: ");
			return "redirect:/";

		}
	 if(userId != Integer.parseInt(httpSession.getAttribute("USERID").toString())){
 		System.out.println("Don't try to sneek other's record: ");
 		return "redirect:/homepage";
 	}
	
	 
	 	System.out.println("enter user order");
	  JPARequestBookDAO i= new JPARequestBookDAO();
	  
	  List<order> userOrders = i.getAllUserOrders(userId);
	  
	  ModelAndView mv = new ModelAndView();
  	
		mv.setViewName("showOrders");
		mv.addObject("userOrdersDetails", userOrders);
		return mv;
 }
 
 
 
 @ResponseBody
 @RequestMapping(value = "/feedback/{id}/{value}",method = RequestMethod.POST)
 public String insertFeedback(@PathVariable int id, @PathVariable int value) {
		//JsonResponse j = new JsonResponse();
		if(sessionService.checkAuth()) {
			order o = new order();
			
			
			JPARequestBookDAO jd = new JPARequestBookDAO();
			//o.setId(id);
			
			o = jd.getOrder(id);
			o.setFeedback(value);
			jd.updateOrder(o);
			o = jd.getOrder(id);
			
			//write feedback in recommendation file
			
		    BookController b = new BookController();
		    int parentId = b.getParentBook(o.getBookId());
			
		    int userId=Integer.parseInt(httpSession.getAttribute("USERID").toString());
		    
		    jd.inserDataMahout(userId, parentId, value);
			
		    if(o.getFeedback() == value)
	   	 		return "Y";
			else
				return "N";
		
		
		}
		else
		{
			System.out.println("No session");
			return "NS";
		}
	  
	}
 
 
 
 @ResponseBody
 @RequestMapping(value = "/codeConfirm/{id}/{value}",method = RequestMethod.POST)
 public String codeConfirm(@PathVariable int id, @PathVariable String value) {
		//JsonResponse j = new JsonResponse();
		if(sessionService.checkAuth()) {
			order o = new order();
			
			System.out.println("orderID " + id + ", value " + value);
			JPARequestBookDAO jd = new JPARequestBookDAO();
			//o.setId(id);
			
			o = jd.getOrder(id);
			//o.setCode(value);
			System.out.println("DBValue " + o.getCode() + ", ac" + o.getActive());
			
			if(o.getCode().equals(value) && o.getActive() == 1){
				o.setActive(0);
				jd.updateOrder(o);
				o = jd.getOrder(id);
				System.out.println("orderID " + id + ", value " + value);

				if(o.getCode().equals(value) && o.getActive() == 0){
		   	 		
					SendEmail se = new SendEmail();
					
					JPAUserDAO jpU = new JPAUserDAO();
					user u = jpU.getUser(o.getUserId());
					String subject = "Order complete!";
					String body="Dear "+u.getName()+"," + "\n\nYour order id: " +  id + " is complete. \nThanks! \nDigitalLibrary Team";
					
					se.sendOrderConf(u.getName(), id, u.getEmailId(), subject,body);
					
					return "Y";
				}else
					return "N";
			}
			else
			{
				if(o.getCode().equals(value))
					return "Invalid Code!";
				else
					return "Inactive order";
			}
			
			
			
		
		
		}
		else
		{
			System.out.println("No session");
			return "NS";
		}
	  
	}

}

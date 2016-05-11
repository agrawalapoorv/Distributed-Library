package edu.sjsu.digitalLibrary.prj.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.digitalLibrary.prj.dao.JPABookDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPALoginDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPATokenDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPAUserDAO;
import edu.sjsu.digitalLibrary.prj.jsonview.Views;
import edu.sjsu.digitalLibrary.prj.models.JsonResponse;
import edu.sjsu.digitalLibrary.prj.models.Login;
import edu.sjsu.digitalLibrary.prj.models.LoginSamplee;
import edu.sjsu.digitalLibrary.prj.models.MongoBook;
import edu.sjsu.digitalLibrary.prj.models.Tokens;
import edu.sjsu.digitalLibrary.prj.models.user;
import edu.sjsu.digitalLibrary.prj.utils.CheckSession;
import edu.sjsu.digitalLibrary.prj.utils.PlayPP;
//import org.springframework.stereotype.Controller;
 

@RestController
public class LoginController {
    
	LoginSamplee loginModel;
	Tokens tokenModel;
    
    @Autowired
	private HttpSession httpSession;
    
	@Autowired
	private CheckSession sessionService;
    
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView loginPage() {
    	if(null != httpSession.getAttribute("USERID")) {
    		httpSession.removeAttribute("USERID");
        	httpSession.removeAttribute("USERNAME");
        	httpSession.invalidate();
    	}
    	loginModel = new LoginSamplee();
    	return new ModelAndView("login", "logindetails", loginModel);
    }
    
    
    @RequestMapping(value = "/resetpassword/{userid}/{token}",method = RequestMethod.GET)
    public ModelAndView openResetPasswordPage(@PathVariable("userid") int userId,@PathVariable("token") String token) {
    	
    	System.out.println("userId :"+userId);
    	System.out.println("token :"+token);
    	
    	JPATokenDAO tokenDao = new JPATokenDAO();
    	tokenModel = tokenDao.getTokenData(userId);
    	
    	ModelAndView model = new ModelAndView();
    	
    	Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        java.util.Date currentDate = cal.getTime();
        
        System.out.println(new java.sql.Timestamp(currentDate.getTime()));
        System.out.println(tokenModel.getExpiry_date());
        
        if(new java.sql.Timestamp(currentDate.getTime()).after(tokenModel.getExpiry_date())){
        	model.addObject("Flag", "E");
        	model.addObject("message", "This link is expired !!");
        	model.addObject("resetpassword", tokenModel);
          	model.setViewName("resetPassword");
          	
          	return model;
        }
    	
    	model.addObject("id", tokenModel.getId());
    	model.addObject("resetpassword", tokenModel);
      	model.setViewName("resetPassword"); 
      	
    	return model;
    }
    
    @RequestMapping(value = "/resetpassword/{userid}/{token}",method = RequestMethod.POST)
    public ModelAndView resetPassword(@ModelAttribute("userdetails")Tokens tokenModel,HttpServletRequest request,  HttpServletResponse response) {
    	
    	try{
    		
    		System.out.println(tokenModel.getUserid());
        	System.out.println(tokenModel.getNewPassword());
        	System.out.println(tokenModel.getId());
        	
        	
        	JPATokenDAO tokenDao = new JPATokenDAO();
        	tokenDao.delete(tokenModel);
        	
        	JPAUserDAO userDAO = new JPAUserDAO();
        	userDAO.updateUserPassword(tokenModel.getUserid(), PlayPP.sha1(tokenModel.getNewPassword()));
        	
        	ModelAndView model = new ModelAndView();

        	
        	// TODO : delete row from tokens table and update user table with new password
        	model.addObject("Flag", "S");
           	model.addObject("message", "Your password has been changed. Now you can login with your new password !");
           	model.addObject("resetpassword", tokenModel);
          	model.setViewName("resetPassword"); 
        	return model;

    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		return new ModelAndView("error404");
    		
    	}
    	
    	}
    
    
    @JsonView(Views.Public.class)
    @ResponseBody
    @RequestMapping(value="/checkUserAccountActivation", method=RequestMethod.POST)
    public JsonResponse checkUserAccountActivation(@RequestBody Login loginModel){
    	
    	try{
    	JsonResponse jsonResponse = new JsonResponse();
    	
    	JPALoginDAO obj= new JPALoginDAO();
    	JSONObject jsonObj = obj.validateActivation(loginModel);
    	
    	if(!jsonObj.equals(null)){
    		jsonResponse.setSuccessFlag("Y");
        	jsonResponse.setSuccessMessage(jsonObj.toString());
    	}else{
    		jsonResponse.setSuccessFlag("E");
    		jsonResponse.setErrorMessage("Error Occurred while processing request");
    	}
    	    	
    	return jsonResponse;
    	}catch(Exception e){
    		JsonResponse jsonResponse = new JsonResponse();
    		jsonResponse.setSuccessFlag("E");
    		jsonResponse.setErrorMessage("Error Occurred while processing request");                 
          	return jsonResponse;
    	}
    	
    }
    
    @JsonView(Views.Public.class)
    @ResponseBody
    @RequestMapping(value="/sendEmail", method=RequestMethod.POST)
    public JsonResponse sendemail(@RequestBody Login loginModel){
    	
    	JsonResponse jsonResponse = new JsonResponse();
    	
    	JPALoginDAO obj= new JPALoginDAO();
    	JSONObject result = obj.checkUser(loginModel);
    	
    	boolean isUserInTheSystem = (boolean) result.get("isUserInTheSystem");
    	
    	if(!isUserInTheSystem){
    		
    		jsonResponse.setSuccessFlag("N");
	        jsonResponse.setSuccessMessage("No user with this email id exists");
    		return jsonResponse;
    	}
    	
    	int userid= result.getInt("userid"); 
    	
    	String token = UUID.randomUUID().toString();
    	token = token.replaceAll("-", "");
    	
    	Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 2);
        java.util.Date expirationDate = cal.getTime();
        java.sql.Timestamp expiryDate = new java.sql.Timestamp(expirationDate.getTime());
    	
    	JPATokenDAO tokenDAOObj = new JPATokenDAO();
    	
    	Tokens tokenObject = tokenDAOObj.getTokenData(userid);
    	
    	boolean sendEmail = false;
    	if(tokenObject != null){    		
    		tokenObject.setExpiry_date(expiryDate);
    		tokenObject.setToken(token);
    		tokenDAOObj.update(tokenObject);
    		sendEmail = true;
    	}else{
    		
    		Tokens tokenObj = new Tokens();    	    	
	    	tokenObj.setExpiry_date(expiryDate);
	    	tokenObj.setUserid(userid);
	    	tokenObj.setToken(token);
	    	
	    	int l = tokenDAOObj.insert(tokenObj);
	    	
	    	if(l != 0){
	    		sendEmail = true;
	    	}
    		
    	}    	
    	
    	if(sendEmail){
    		
    		String username = result.getString("username");
        	
        	
        	Properties props = new Properties();
    		props.put("mail.smtp.host", "smtp.gmail.com");
    		props.put("mail.smtp.socketFactory.port", "465");
    		props.put("mail.smtp.socketFactory.class",
    				"javax.net.ssl.SSLSocketFactory");
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.port", "465");

    		Session session = Session.getDefaultInstance(props,
    			new javax.mail.Authenticator() {
    				protected PasswordAuthentication getPasswordAuthentication() {
    					return new PasswordAuthentication("noreplydigitalbookshare@gmail.com","cmpe295b");
    				}
    			});

    		try {

    			Message message = new MimeMessage(session);
    			message.setFrom(new InternetAddress("noreplydigitalbookshare@gmail.com"));
    			message.setRecipients(Message.RecipientType.TO,
    					InternetAddress.parse(loginModel.getUserEmail()));
    			message.setSubject("Recover your password");
    			
    			message.setText("Dear "+username+"," +
    					"\n\nHere is Your link to change your password (Expires in two hours) " +
    					"http://localhost:8080/Distributed-Library/resetpassword/"+result.getInt("userid")+"/"+token+
    					"\n\nRegards,\n\nDigital Book Share Team\n\nPLEASE DO NOT REPLY TO THIS EMAIL");

    			Transport.send(message);

    			System.out.println("Done");
    			
    			jsonResponse.setSuccessFlag("M");
    	        jsonResponse.setSuccessMessage("Steps to recover your password are sent on provided email..");
    	        
    	        return jsonResponse;

    		} catch (MessagingException e) {

    			e.printStackTrace();
    			
    			jsonResponse.setSuccessFlag("E");
    	        jsonResponse.setErrorMessage("Error....");
    	        
    	        return jsonResponse;
    		}
    		
    	}else{
    		
    		jsonResponse.setSuccessFlag("E");
	        jsonResponse.setErrorMessage("Error....");
	        
	        return jsonResponse;
    		
    	}
    		
    }
    
    @JsonView(Views.Public.class)
    @RequestMapping(value="/login", method=RequestMethod.POST)
	public JsonResponse userLogin(@RequestBody Login loginModel) {
    	
try {
        	
        	String msg=null;
        	
           if(loginModel.getUserEmail().equals(null) || loginModel.getUserEmail().isEmpty())
           {
        	   
            JsonResponse response = new JsonResponse();
            response.setSuccessFlag("N");
            response.setErrorMessage("Invalid user email and password combination");                    
          	return response;
           }
           
        	else {
            	JPALoginDAO obj= new JPALoginDAO();
            	loginModel.setPassword(PlayPP.sha1(loginModel.getPassword()));
            	loginModel.setPassword(loginModel.getPassword());
            	int l =obj.validate(loginModel);
            	
            	ModelAndView model = new ModelAndView();
            	if(l == 0) {
            		JsonResponse response = new JsonResponse();
            		response.setSuccessFlag("N");
                    response.setErrorMessage("Invalid user email and password combination");                    
                  	return response;
            	} else {
            		
            		JsonResponse response = new JsonResponse();
            		JPAUserDAO jp = new JPAUserDAO();
            		System.out.println("Welcome sir: " + l);
            		loginModel.setId(l);
            		httpSession.setAttribute("USERID", l);
            		user tempUser = jp.getUser(l);
            		httpSession.setAttribute("USERNAME", tempUser.getName());
            		sessionService.setHttpSession(httpSession);
            		System.out.println("my userid in session is" + httpSession.getAttribute("USERID"));
	
          
            		JPABookDAO bookTemp = new JPABookDAO();
            		
            		int orderCount = bookTemp.getOrderCount(loginModel.getId());
            		
            		//get Top recommendations from user category based on rating
            		String[] categories = tempUser.getCategory().split(",");
            		
            		List<MongoBook> recommCatBooks = new ArrayList<MongoBook>();
            		
            		recommCatBooks = bookTemp.searchTop5CategoryBooks(categories);
            		
            		
            		List<Integer> userbasedRecommBookIds = new ArrayList<Integer>();
            		if(orderCount != 0)
            		{
            			//get Apache Mahout recommendations based on previous selections
            			
            			userbasedRecommBookIds = bookTemp.getMahoutRecomm(980);
            			
            		}
            		
            		List<MongoBook> recommendedForYou = new ArrayList<MongoBook>();
            		
            		for(int i=0;i<userbasedRecommBookIds.size();i++){
            			MongoBook bookDetails =  bookTemp.searchBooksInDBByID(String.valueOf(userbasedRecommBookIds.get(i)));
            			System.out.println("author:" + bookDetails.getAuthors().size());
            			recommendedForYou.add(bookDetails);
            		}
            		
//            		for(int m : userbasedRecommBookIds)
//            		{
//            			System.out.println("User based recomm:" + m);
//            		}
            		
            		////End check for recommendations
            		
            		//return new ModelAndView("redirect:/");
            		
            		response.setSuccessFlag("Y");
            		response.setSuccessMessage("Login success");
            		//MongoCrud m = new MongoCrud();
            		return response;

            	}
           	 	
            }
        } catch (Exception e) {
            System.out.println("Exception in FirstController "+e.getMessage());
            e.printStackTrace();
            JsonResponse response = new JsonResponse();
            response.setSuccessFlag("E");
            response.setErrorMessage("Error Occurred while processing request");                 
          	return response;
        }
	}
    
    
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public JsonResponse logoutPage(HttpServletRequest request,  HttpServletResponse response) {
    	httpSession = sessionService.getHttpSession();
    	httpSession.removeAttribute("USERID");
    	httpSession.removeAttribute("USERNAME");
    	httpSession.invalidate();
    	sessionService.setHttpSession(null);
    	System.out.println("in logot");
    	
    	JsonResponse jsonResponse = new JsonResponse();
    	jsonResponse.setSuccessFlag("Y");
    	jsonResponse.setSuccessMessage("logout success");
    	return jsonResponse;
    }
    
    
}
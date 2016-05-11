package edu.sjsu.digitalLibrary.prj.controller;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

























import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.digitalLibrary.prj.dao.*;
import edu.sjsu.digitalLibrary.prj.jsonview.Views;
import edu.sjsu.digitalLibrary.prj.models.JsonResponse;
import edu.sjsu.digitalLibrary.prj.models.Login;
import edu.sjsu.digitalLibrary.prj.models.MongoBook;
import edu.sjsu.digitalLibrary.prj.models.address;
import edu.sjsu.digitalLibrary.prj.models.Registration;
import edu.sjsu.digitalLibrary.prj.models.LandingPage;
import edu.sjsu.digitalLibrary.prj.models.LoginSamplee;
import edu.sjsu.digitalLibrary.prj.models.category;
import edu.sjsu.digitalLibrary.prj.models.internalCategory;
import edu.sjsu.digitalLibrary.prj.models.payment;
import edu.sjsu.digitalLibrary.prj.models.user;
import edu.sjsu.digitalLibrary.prj.utils.CheckSession;
import edu.sjsu.digitalLibrary.prj.utils.PlayPP;
import edu.sjsu.digitalLibrary.prjservices.SearchServiceImpl;

 
@SuppressWarnings("unused")
@Controller
public class PaymentController {

    int passwordDiff = 0;
    //private internalCategory homepageModel;
    private user userModel;
    //private book bookModel;
    //private category categoryModel;
    private LandingPage landingPage;
    HttpSession session;
    private static Jedis jedis;
    
   
    @Autowired
   	private HttpSession httpSession;
   	
   	@Autowired
   	private CheckSession sessionService;
    
   	private MongoBook bookModel;
   	
   	private SearchServiceImpl searchService= new SearchServiceImpl();
   	
    @RequestMapping(value = "/payment/{userId}",method = RequestMethod.GET)
    public Object paymentGet(@PathVariable int userId, HttpServletRequest request) {
    	if (!sessionService.checkAuth()) {
			
    		System.out.println("Invalid session");

			return "redirect:/";

		}
    	
    	if(userId != Integer.parseInt(httpSession.getAttribute("USERID").toString())){
    		System.out.println("Don't try to sneek other's record");
    		return "redirect:/";
    	}
    	
    	ModelAndView mv = new ModelAndView();
    	JPAPaymentDAO jpPy = new JPAPaymentDAO();
    	payment py = jpPy.get(userId);
    	
    	if(py == null)
    		py = new payment();
    	
        mv.addObject("paymentDetails",py);
        
        mv.setViewName("payment");
        
       return mv;
    }
    
    
    
    @RequestMapping(value = "/payment",method = RequestMethod.POST)
    public Object paymentPost(@ModelAttribute("paymentDetails")payment
    		payment, HttpServletRequest request) {
    	
    	System.out.println("hereeee");
    	
    	if (!sessionService.checkAuth()) {
			
    		System.out.println("Invalid session");

			return "redirect:/";

		}
    	int userId = Integer.parseInt(httpSession.getAttribute("USERID").toString());
    	
    	
    	ModelAndView mv = new ModelAndView();
    	JPAPaymentDAO jpPy = new JPAPaymentDAO();
    	payment py = jpPy.get(userId);
    	
    	
    	if(py == null)
    	{
    		//py = new payment();
    		jpPy.insert(payment);
    		mv.addObject("paymentDetails",payment);
    	}
    	else{
	    	py.setCardNumber(payment.getCardNumber());
	    	py.setCardType(payment.getCardType());
	    	py.setCvv(payment.getCvv());
	    	py.setName(payment.getName());
	    	py.setValidTillMonth(payment.getValidTillMonth());
	    	py.setValidTillYear(payment.getValidTillYear());
	    	py.setActive(1);
	    	jpPy.update(py);
	    	mv.addObject("paymentDetails",py);
    	}
    	
    	
        
        
        mv.setViewName("payment");
        
       return mv;
    }
    
    
}
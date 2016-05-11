package edu.sjsu.digitalLibrary.prj.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sjsu.digitalLibrary.prj.models.LoginSamplee;




@SuppressWarnings("unused")
@Service("sessionService")
public class CheckSession {
	
	@Autowired
	private HttpSession httpSession;
	
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	
	public boolean checkAuth() {
		
		LoginSamplee login = new LoginSamplee();

		if(httpSession==null)
    	{
    		System.out.println("user not logged in so redirecting to login");
    		        	
         return false;
    		
    	}
    	
    	if(httpSession.getAttribute("USERID")==null || httpSession.getAttribute("USERID").toString().isEmpty())
    	{
    		System.out.println("userid not fetched from session, some problem");
    		
        	
    		
    		return false;
    		
    	}
		return true;
        
        }
         
    }
	

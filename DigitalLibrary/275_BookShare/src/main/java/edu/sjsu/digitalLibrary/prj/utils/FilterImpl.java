package edu.sjsu.digitalLibrary.prj.utils;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 











import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sjsu.digitalLibrary.prj.models.LoginSamplee;

@SuppressWarnings("unused")

public class FilterImpl implements Filter{
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	HttpServletResponse hsr = (HttpServletResponse) res;
	hsr.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	hsr.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	hsr.setDateHeader("Expires", 0); // Proxies.
	chain.doFilter(req, res);
	
	 }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
    }
	

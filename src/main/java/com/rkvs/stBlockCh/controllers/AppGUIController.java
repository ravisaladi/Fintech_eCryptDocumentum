package com.rkvs.stBlockCh.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rkvs.stBlockCh.model.UserAuthentication;
import com.rkvs.stBlockCh.utilities.AuthenticatorUtils;

/**
 * @author rsaladi
 *
 */
@Controller
public class AppGUIController {

	/**
	 * 
	 */
	public AppGUIController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping({ "/" })
	public String indexHandler(Model model) {
		return "index";
	}
	
	@RequestMapping(value="/ecryptmain")
	public String ecptMainHandler(Model model) {
		model.addAttribute("userObj", new UserAuthentication());
		return "appmain";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginHandler_Get() {
		return "index";
	}
	

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginHandler(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session,
			@ModelAttribute UserAuthentication userObj) {
		
		String field = request.getParameter("action");
		
		System.out.println(userObj.getUser());
		
	    if( field.equals("Get Code") ){
	        if(userObj.getUser()=="") {
	        	model.addAttribute("eterces", "empty");		
	        } else {
	        	String s = AuthenticatorUtils.getAuthCode();
	        	model.addAttribute("eterces", s);
	        	String qrCode = AuthenticatorUtils.getQRBarcodeURL("ravisaladi", "linux",s);
	        	model.addAttribute("qrcode", qrCode);
	        	System.out.println(qrCode);
	        }
	        
	    } else if( field.equals("Submit") ){
	    	
	    	
	    	
	    }
		
		model.addAttribute("userObj", userObj);
		return "appmain";
	}
	
	

}

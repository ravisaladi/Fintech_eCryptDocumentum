package com.rkvs.stBlockCh.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rkvs.stBlockCh.core.ECryptServices;
import com.rkvs.stBlockCh.core.MasterECryptEngine;
import com.rkvs.utilities.StorageService;
import com.bessapps.stBlockCh.model.UserDetails;
import com.bessapps.stBlockCh.utilities.ApplicatonGlobalConfig;
import com.bessapps.stBlockCh.utilities.AuthenticatorUtils;
import com.bessapps.stBlockCh.wrappers.DataStoreWrapper;

/**
 * @author rsaladi
 *
 */
@Controller
public class AppGUIController {
	private  StorageService storageService;
    
	private static ECryptServices mCrypt = new MasterECryptEngine();

	/**
	 * 
	 */
    @Autowired
	public AppGUIController(StorageService storageService) {
		 this.storageService = storageService;
	}

	@RequestMapping({ "/" })
	public String indexHandler(Model model) {
		return "index";
	}
	
	@RequestMapping("/logout")
	public String handleLogout(HttpSession session) {
		session.removeAttribute("activeuser");
		return "index";
	}

	@RequestMapping(value="/ecryptmain")
	public String ecptMainHandler(Model model, HttpSession session) {
		if(session.getAttribute("activeuser") != null) {
			return "eCryptDoc";
		} else {
			model.addAttribute("userObj", new UserDetails());
			model.addAttribute("errorMsg", "");
			return "appmain";
		}
	}
	
	@RequestMapping(value="/eCryptDoc")
	public String handleECryptDoc() {
		return "index";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginHandler_Get() {
		return "index";
	}


	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginHandler(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session,
			@ModelAttribute UserDetails userObj) {

		model.addAttribute("errorMsg", "");
		
		String field = request.getParameter("action");
		System.out.println(field);
		

		if( field.equals("Get Code") ){
			if(userObj.getUser()=="") {
				model.addAttribute("eterces", "empty");		
			} else {
				String s = AuthenticatorUtils.getAuthCode();
				model.addAttribute("eterces", s);
				session.setAttribute("key", s);
				session.setAttribute("userid", userObj.getUser());
				String qrCode = AuthenticatorUtils.getQRBarcodeURL(userObj.getUser(), "linux", s);
				model.addAttribute("qrcode", qrCode);
				//System.out.println(qrCode);
			}

		} else if(field.equals("Submit2")) {
			ApplicatonGlobalConfig.printExistingMap(ApplicatonGlobalConfig.getMapData().getHashMap());

			if(userObj.getUser() == "" || userObj.getSecrete() == "") {
				model.addAttribute("errorMsg", "User Id or Secret should not be NULL! Try again!");
				model.addAttribute("userObj", new UserDetails());
				return "appmain";
			}
			
			if((AuthenticatorUtils.isItValidKey((String)session.getAttribute("userid"),
							(String)session.getAttribute("key"), 
							Integer.parseInt(userObj.getSecrete())))) {
				userObj.setBlockID(mCrypt.iniateBlock(userObj));
				ApplicatonGlobalConfig.addtoKeyMap(userObj, (String)session.getAttribute("key"));
				session.setAttribute("activeuser", userObj);
				return "eCryptDoc";
			} else {
				model.addAttribute("errorMsg", "User id password is not matching try again!");
			}
		}
		
		if(field.equals("Submit1")) {
			if(userObj.getUser() == "" || userObj.getSecrete() == "") {
				model.addAttribute("errorMsg", "User Id or Secret should not be NULL! Try again!");
				model.addAttribute("userObj", userObj);
				return "appmain";
			}
			
			//Temporary
			boolean debug = true;
			
			if(ApplicatonGlobalConfig.getMapValue(userObj.getUser()) != null ) {
				if(debug || (AuthenticatorUtils.isItValidKey(userObj.getUser(),
						ApplicatonGlobalConfig.getMapValue(userObj.getUser()), 
						Integer.parseInt(userObj.getSecrete())))) {
					session.setAttribute("activeuser", userObj);
					return "eCryptDoc";
				}
			} else {
				model.addAttribute("errorMsg", "Email and Password do not match! Try again or create Secret...");
				model.addAttribute("userObj", new UserDetails());
				return "appmain";
			}
			
		}

		model.addAttribute("userObj", userObj);
		return "appmain";
	}
	
	@RequestMapping("/addfile")
	public String handleAddFile(Model model, HttpSession session) {
		if(session.getAttribute("activeuser") != null) {
			model.addAttribute("data", new DataStoreWrapper());
			return "addfile";
		} else {
			model.addAttribute("userObj",  new UserDetails());
			return "appmain";
		}
	}
	
	@RequestMapping("/addblock")
	public String addBlockHandler(Model model, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute DataStoreWrapper dSW, @RequestParam("file") MultipartFile file) {

		//System.out.println(dSW.getFileName() + " -- " + dSW.getDescription());
		String fLocation = storageService.store(file);
		dSW.setFileName(fLocation);
		mCrypt.insertDocument(dSW);
		
		if(session.getAttribute("activeuser") == null) { 
			return "index";
		}
		return "eCryptDoc";
	}

	@RequestMapping("/test")
	public String handleTest() {
		return "mytest";
	}
}

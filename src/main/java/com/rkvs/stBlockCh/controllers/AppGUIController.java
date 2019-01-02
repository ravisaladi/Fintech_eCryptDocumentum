package com.rkvs.stBlockCh.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rkvs.stBlockCh.core.Block;
import com.rkvs.stBlockCh.core.DataStore;
import com.rkvs.stBlockCh.core.ECryptServices;
import com.rkvs.stBlockCh.core.MasterBlockHandler;
import com.rkvs.stBlockCh.core.MasterECryptEngine;
import com.rkvs.stBlockCh.model.DataStoreWrapper;
import com.rkvs.stBlockCh.model.UserDetails;
import com.rkvs.stBlockCh.utilities.AppConfigData;
import com.rkvs.stBlockCh.utilities.ApplicatonGlobalConfig;
import com.rkvs.stBlockCh.utilities.AuthenticatorUtils;
import com.rkvs.stBlockCh.utilities.MapData;
import com.rkvs.stBlockCh.utilities.StorageService;
import com.rkvs.stBlockCh.wrappers.UserDataWrapper;

/**
 * @author rsaladi
 *
 */
@Controller
public class AppGUIController {
	private final StorageService storageService;
    
	private static ECryptServices mCrypt = new MasterECryptEngine();
	
	/**
	 * 
	 */
    @Autowired
	public AppGUIController(StorageService storageService) {
		 this.storageService = storageService;
	}
    
	@RequestMapping({ "/" })
	public String index(Model model) {
		return "redirect:/logout";
	}

	@RequestMapping({ "/index" })
	public String indexHandler(Model model) {
		return "index";
	}
	
	@RequestMapping("/logout")
	public String handleLogout(HttpSession session) {
		//session.removeAttribute("activeuser");
		session.invalidate();
		return "index";
	}

	@RequestMapping(value="/ecryptmain")
	public String ecptMainHandler(Model model, HttpSession session) {
		if(session.getAttribute("activeuser") != null) {
			UserDetails user = ApplicatonGlobalConfig.getMapValue((String) session.getAttribute("activeuser"));
			HashMap<String, Block> fileMap = MasterBlockHandler.getUserObjects(user);
			model.addAttribute("mapsList", fileMap);
			return "eCryptDoc";
		} else {
			model.addAttribute("userObj", new UserDataWrapper());
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
			@ModelAttribute UserDataWrapper userdata) {

		boolean newAccount = false;
		model.addAttribute("errorMsg", "");
		
		String field = request.getParameter("action");
		//System.out.println(field);
		

		if( field.equals("Get GAuthenticatino Code") ){
			if(userdata.getUser()=="") {
				model.addAttribute("eterces", "empty");		
			} else {
				String s = AuthenticatorUtils.getAuthCode();
				model.addAttribute("eterces", s);
				session.setAttribute("key", s);
				session.setAttribute("userid", userdata.getUser());
				String qrCode = AuthenticatorUtils.getQRBarcodeURL(userdata.getUser(), "linux", s);
				model.addAttribute("qrcode", qrCode);
				//System.out.println(qrCode);
			}

		} else if(field.equals("Submit")) {
			if(userdata.getUser() == "" || userdata.getSecrete() == "") {
				model.addAttribute("errorMsg", "Email Id or Secret should not be NULL! Try again!");
				model.addAttribute("userObj", new UserDataWrapper());
				return "appmain";
			}
			
			UserDetails user = ApplicatonGlobalConfig.getMapValue(userdata.getUser());
			if(user == null) {
				if((String)session.getAttribute("key") == null) {
					model.addAttribute("errorMsg", "Looks like Email id is not registerd with the system!");
					model.addAttribute("userObj", new UserDataWrapper());
					return "appmain";
				} else {
					user = new UserDetails();
					user.setUser(userdata.getUser());
					user.setGAuthCode((String)session.getAttribute("key"));
					newAccount = true;
				}
			} 
			
			if((AuthenticatorUtils.isItValidKey(user, Integer.parseInt(userdata.getSecrete())))) {
				if(newAccount) {
					user.setBlockID(++AppConfigData.lastBlockID);					
					user.setBlockID(mCrypt.iniateBlock(user));
					ApplicatonGlobalConfig.addtoKeyMap(user);
				}
				session.setAttribute("activeuser", user.getUser());
				session.removeAttribute("key");
				HashMap<String, Block> fileMap = MasterBlockHandler.getUserObjects(user);
				model.addAttribute("mapsList", fileMap);
				return "eCryptDoc";
			} else {
				model.addAttribute("errorMsg", "Entered Email ID and passcode is not matching try again!");
			}
		}
		
		model.addAttribute("userObj", userdata);
		return "appmain";
	}
	
	@RequestMapping("/addfile")
	public String handleAddFile(Model model, HttpSession session) {
		if(session.getAttribute("activeuser") != null) {
			model.addAttribute("data", new DataStoreWrapper());
			return "addfile";
		} else {
			model.addAttribute("userObj",  new UserDataWrapper());
			return "appmain";
		}
	}
	
	@RequestMapping("/addblock")
	public String addBlockHandler(Model model, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute DataStoreWrapper dSW, @RequestParam("file") MultipartFile file) {

		UserDetails user = ApplicatonGlobalConfig.getMapValue((String) session.getAttribute("activeuser"));
		String fLocation = storageService.store(file, false, user);
		dSW.setAbsFilePath(fLocation);
	
		mCrypt.insertDocument(dSW, user);
		
		if(session.getAttribute("activeuser") == null) { 
			return "index";
		}
		HashMap<String, Block> fileMap = MasterBlockHandler.getUserObjects(user);
		model.addAttribute("mapsList", fileMap);
		return "eCryptDoc";
	}

	@RequestMapping("/test")
	public String handleTest() {
		return "mytest";
	}
	
	@RequestMapping("/blocks")
	public String handleBlocksReq(Model model, HttpSession session) {
		System.out.println("In block printing method!");
		UserDetails user = ApplicatonGlobalConfig.getMapValue((String) session.getAttribute("activeuser"));
		if(user != null) {
			//MasterBlockHandler.printBlockMap();
			HashMap<String, Block> fileMap = MasterBlockHandler.getUserObjects(user);
			for(String x: fileMap.keySet()) {
				String value = fileMap.get(x).getdStoreWrap().getFileName();
				System.out.println(x + "-- " + value);
			}
			
		} else {
			System.out.println("User is null!");
		}
		return "mytest";
	}
	
	@RequestMapping("/update@{id}")
	public String handleUpdateBlock(Model model, HttpSession session,
			@PathVariable("id") String blockId) {
		
		UserDetails user = ApplicatonGlobalConfig.getMapValue((String) session.getAttribute("activeuser"));
		HashMap<String, Block> fileMap = MasterBlockHandler.getUserObjects(user);
		
		Block blkObj = fileMap.get(blockId);
		model.addAttribute("data", blkObj.getdStoreWrap());
		model.addAttribute("blkobj", blkObj);
		return "updateBlock";
	}
	
	@RequestMapping("/update{id}")
	public String handleUpdateBlock_r(Model model, HttpSession session,
			@PathVariable("id") String blockId) {
		
		UserDetails user = ApplicatonGlobalConfig.getMapValue((String) session.getAttribute("activeuser"));
		HashMap<String, Block> fileMap = MasterBlockHandler.getUserObjects(user);
		
		//Block blkObj = fileMap.get(blockId);
		//model.addAttribute("data", blkObj.getdStoreWrap());
		//model.addAttribute("blkobj", blkObj);
		model.addAttribute("mapsList", fileMap);
		return "eCryptDoc";
	}
}

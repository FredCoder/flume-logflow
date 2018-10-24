package com.log.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.log.web.service.ILoginService;

/**
 * 简单的登录页
 * 
 * @author Kevin.luo
 *
 */
@Controller
@RequestMapping("/user/")
@SessionAttributes("username")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String SESSION_KEY = "username";
	
	@Resource(name = "loginServiceImpl")
	private ILoginService loginService;

	@RequestMapping(value = "/login")
	public String login(String username, String password, ModelMap model) {
		boolean flag = loginService.userLogin(username, password);
		if(flag){
			model.addAttribute(SESSION_KEY, username);
			return "redirect:/logflow/index.html";
		} else {
			return "redirect:/index.html";
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout(SessionStatus sessionStatus){
		sessionStatus.setComplete();
		return "redirect:/index.html";
	}
}

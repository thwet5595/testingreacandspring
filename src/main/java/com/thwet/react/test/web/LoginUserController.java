package com.thwet.react.test.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thwet.react.test.entity.User;
import com.thwet.react.test.service.UserService;
import com.thwet.react.test.validator.UserFormValidator;

@Controller
public class LoginUserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("userFormValidator")
	private UserFormValidator userFormValidator;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEnconder;

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String adminLoginPage(Model model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		model.addAttribute("user", new User());
		if (error != null) {
			model.addAttribute("loginError", "Invalid email or password.");
		}
		if (logout != null) {
			model.addAttribute("logout", "You have been logged out.");
		}
		return "login";
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return "redirect:/user/login?logout";
	}

//	@RequestMapping(value = "/user", method = RequestMethod.GET)
//	public String showUser(HttpServletRequest request, HttpServletResponse response) {
//		logger.info("Enter user success::::");
//		return "questionHome";
//	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Validated @ModelAttribute User user, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		if (result.hasErrors()) {
			return "register";
		}

		userFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "register";
		}
		user.setPassword(passwordEnconder.encode(user.getPassword()));
		userService.save(user);
		return "redirect:/user/login";
	}
}

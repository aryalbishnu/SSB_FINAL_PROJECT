package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("")
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "SSB_home_page");
		return "home";

	}

	@GetMapping("/abouts")
	public String abouts(Model model) {
		model.addAttribute("title", "SSB_about_page");
		return "abouts";

	}

	@GetMapping("/services")
	public String services(Model model) {
		model.addAttribute("title", "SSB_sercices_page");
		return "services";
	}

	@GetMapping("/chooseUser")
	public String chooseUseer(Model model, HttpSession session, SessionStatus status) {
		model.addAttribute("title", "SSB_User_Choosee");
		status.setComplete();
    session.removeAttribute("bishnuDto");
		return "login/chooseUser";
	}

	@GetMapping("/gmail")
	public String sengemail(Model model) {
		model.addAttribute("title", "SSB_client_support_email_page");
		return "gmail";
	}

}
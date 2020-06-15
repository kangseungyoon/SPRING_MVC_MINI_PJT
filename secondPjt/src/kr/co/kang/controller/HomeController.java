package kr.co.kang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		//System.out.println(request.getServletContext().getRealPath("/"));
		return "redirect:main";
	}
}

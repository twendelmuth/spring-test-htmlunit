package spring.test.htmlunit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String init(Model model) {
		model.addAttribute("myParam", "Guillaume");
		return "welcome";
	}

	@PostMapping("/test/post")
	public @ResponseBody String post(@RequestParam String subject, @RequestParam String message, Model model) {
		return subject + " " + message;
	}
}

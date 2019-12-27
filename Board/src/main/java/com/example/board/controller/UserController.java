package com.example.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.model.User;
import com.example.board.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/";
	}

	@Autowired
	HttpSession session;

	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute User user) {
		//findByEmailAndPwd에서 해당 요소는 반드시 User.java 안에 들어있어야 함. camel case
		//findByEmailAndPwd는 UserRepository.java에 메소드를 만들어두어야 함.
		User dbUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if (dbUser != null) {
		}
		session.setAttribute("user_info", dbUser);
		return "redirect:/";
	}

	@GetMapping("/signout")
	public String signout() {
		session.invalidate(); //요청을 보낸 client의 세션만 삭제됨
		return "redirect:/";
	}
}
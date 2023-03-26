package com.kimchang.rpa.trial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/trial")
public class TrialController {

	@GetMapping("/search")
	public void search() {
		String url = "http://127.0.0.1:8000/items?txtTrlNo01=2023&txtTrlNo02=106&txtTrlNo03=15&txtTrlNo04=%EA%B9%80";
        ResponseEntity<String> res = new RestTemplate().getForEntity(url, String.class);
        System.out.println(res.getBody());
	}
}

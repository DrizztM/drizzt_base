package cn.drizzt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

	@RequestMapping(value = "test", method = { RequestMethod.POST, RequestMethod.GET })
	public String test(HttpServletRequest request) {
		return "test";
	}

}
package com.albo.challengealbomarvel.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DefaultController implements ErrorController {

	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
	public void handleErrorWithRedirect(HttpServletResponse response) throws IOException {
		response.sendRedirect("/swagger-ui.html");
	}
}

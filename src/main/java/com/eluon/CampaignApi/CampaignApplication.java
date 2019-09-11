package com.eluon.CampaignApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CampaignApplication  // extends SpringBootServletInitializer
{
//
	public static void main(String[] args)
	{
		SpringApplication.run(CampaignApplication.class, args);
	}
	@RequestMapping("/hello")
	public String sayHello()
	{
		return "helo";

	}


}

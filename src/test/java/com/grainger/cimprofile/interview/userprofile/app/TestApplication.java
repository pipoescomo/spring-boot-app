package com.grainger.cimprofile.interview.userprofile.app;

import com.grainger.cimprofile.interview.userprofile.Application;
import org.springframework.boot.SpringApplication;

public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}

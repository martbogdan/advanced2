package com.advanced.taracat;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaracatApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaracatApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository){
		return (args)->{
			User u = new User();
			u.setUserName("bob");
			u.setPassword("123");
			userRepository.save(u);

			u = new User();
			u.setUserName("vvv");
			u.setPassword("123");
			userRepository.save(u);
		};
	}
}

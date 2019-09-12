package com.advanced.taracat;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.CatRepository;
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
	public CommandLineRunner demo(UserRepository userRepository, CatRepository catRepository){
		return (args)->{
			User u = new User();
			u.setUsername("bob");
			u.setPassword("123");
			u.setActive(true);
			userRepository.save(u);

			u = new User();
			u.setUsername("vvv");
			u.setPassword("123");
			u.setActive(true);
			userRepository.save(u);

			Cat c = new Cat();
			c.setName("Kity");
			c.setUser(userRepository.findByUsername("bob"));
			c.setCat_level(1);
			catRepository.save(c);

			c = new Cat();
			c.setName("Tiger");
			c.setUser(userRepository.findByUsername("bob"));
			c.setCat_level(2);
			catRepository.save(c);

			c = new Cat();
			c.setName("Lion");
			c.setUser(userRepository.findByUsername("bob"));
			c.setCat_level(3);
			catRepository.save(c);
			
		};
	}
}

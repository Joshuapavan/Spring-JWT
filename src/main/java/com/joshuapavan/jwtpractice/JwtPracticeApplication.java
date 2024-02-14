package com.joshuapavan.jwtpractice;

import com.joshuapavan.jwtpractice.entities.User;
import com.joshuapavan.jwtpractice.enums.Role;
import com.joshuapavan.jwtpractice.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtPracticeApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JwtPracticeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<User> adminUser = userRepository.findByRole(Role.ADMIN);

		if(adminUser.isEmpty()){
			User admin = new User();
			admin.setEmail("admin@grandeur.com");
			admin.setPassword(new BCryptPasswordEncoder().encode("Admin@123"));
			admin.setRole(Role.ADMIN);

			userRepository.save(admin);
		}
	}
}

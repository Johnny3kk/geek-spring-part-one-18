package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;

@SpringBootApplication
public class Lesson6SpringBootApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lesson6SpringBootApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomething() {
        User user = userRepository.findById(1).get();
        System.out.println(user);
    }

}

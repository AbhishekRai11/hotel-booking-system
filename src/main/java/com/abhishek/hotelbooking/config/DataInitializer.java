package com.abhishek.hotelbooking.config;
import com.abhishek.hotelbooking.entity.User; import com.abhishek.hotelbooking.enums.Role; import com.abhishek.hotelbooking.repository.UserRepository; import org.springframework.beans.factory.annotation.Value; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class DataInitializer {
  @Bean CommandLineRunner seed(UserRepository users,PasswordEncoder encoder,
      @Value("${ADMIN_EMAIL:admin@hotel.local}") String email,
      @Value("${ADMIN_PASSWORD:Admin@12345}") String password){
    return args->{ if(!users.existsByEmail(email)) users.save(User.builder().email(email).password(encoder.encode(password)).fullName("System Admin").role(Role.ADMIN).build()); };
  }
}
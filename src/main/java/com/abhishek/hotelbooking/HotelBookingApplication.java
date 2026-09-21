package com.abhishek.hotelbooking;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class HotelBookingApplication {
  public static void main(String[] args){ SpringApplication.run(HotelBookingApplication.class,args); }
}
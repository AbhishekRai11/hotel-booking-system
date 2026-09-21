package com.abhishek.hotelbooking.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="hotels") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hotel { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; @Column(nullable=false) private String city; private String address; private String description; }
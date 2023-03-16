package com.seyma.socialmediaapplication.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

   @Column(name = "password")
    private String password;

   @Column(name ="avatar")
    private int avatar;

}

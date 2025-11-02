package com.myreflectionthoughts.user.datamodel.entity;

import com.myreflectionthoughts.user.datamodel.role.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity(name = "users")
public class User{

    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="user_name")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "joined")
    private String joined;

    @Column(name="emailverified")
    private boolean emailVerified;
}

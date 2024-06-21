package com.scm.entity;

import com.scm.enums.Providers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false,length = 64)
    private String password;
    @Column(nullable = false,unique = true,length = 30)
    private String email;
    private String profilePicture;
    private String phone;
    private boolean phoneVerified;
    //facebook,GitHub,google
    private Providers provider=Providers.SELF;
    private String providerUserId;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contactList = new ArrayList<>();
}

package com.scm.entity;

import com.scm.enums.Providers;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private String userId;
    @Getter
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false,length = 64)
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(nullable = false,length = 30)
    private String email;
    private String profilePicture;
    private String phone;
    private boolean phoneVerified;
    //facebook,GitHub,google
    @Enumerated(value = EnumType.STRING)
    private Providers provider=Providers.SELF;
    private String providerUserId;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contactList = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolesList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //list of roles [USER<ADMIN]
        Collection roles =
                    rolesList.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

package com.scm.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {

    @Id
    private String id;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;
    private boolean favorite = false;
    private String picture;
    private String websiteLink;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> socialLinkLists= new ArrayList<SocialLink>();
}

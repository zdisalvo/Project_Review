package com.hcc.entities;


import com.hcc.enums.AuthorityEnum;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "authority")
    String authority;

//    @ElementCollection
//    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "id"))

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "users",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "id")
//    )

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@Column(name = "user_id")
    User user;

//    @Column(name = "authorities_id")
//    Long authoritiesId;

    public Authority(Long id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
//        this.authoritiesId = id;
    }

    public Authority(String authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    public Authority(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    public Long getAuthoritiesId() {
//        return authoritiesId;
//    }
//
//    public void setAuthoritiesId(Long authoritiesId) {
//        this.authoritiesId = authoritiesId;
//    }
}

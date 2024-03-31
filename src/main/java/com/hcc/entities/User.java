package com.hcc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cohort_start_date")
    private Date cohortStartDate;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;







//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "authorities",
//            joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)

//    @ElementCollection
//    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))


//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "authorities",
//            joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )

//    @OneToMany
//    @Column(name = "id")

//    @ElementCollection
//    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "authority")

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Authority> authorities;

    public User(Long id, Date cohortStartDate, String username, String password) {
        this.id = id;
        this.cohortStartDate = cohortStartDate;
        this.username = username;
        this.password = password;
        //this.authorities = authorities;
    }

    public User(Date cohortStartDate, String username, String password) {
        this.cohortStartDate = cohortStartDate;
        this.username = username;
        this.password = password;
        //this.authorities = authorities;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCohortStartDate() {
        return cohortStartDate;
    }

    public void setCohortStartDate(Date cohortStartDate) {
        this.cohortStartDate = cohortStartDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}

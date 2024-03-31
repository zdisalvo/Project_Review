package com.hcc.entities;

import javax.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;
    @Column(name = "number")
    private Integer number;

    @Column(name = "github_url")
    private String githubUrl;
    @Column(name = "branch")
    private String branch;
    @Column(name = "code_review_video_url")
    private String reviewVideoUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User codeReviewer;

    public Assignment(Long id, String status, String branch, String reviewVideoUrl,
                      String githubUrl, Integer number, User user, User codeReviewer) {
        this.id = id;
        this.status = status;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.githubUrl = githubUrl;
        this.number = number;
        this.user = user;
        this.codeReviewer = codeReviewer;
    }

    public Assignment(String status, String branch, String reviewVideoUrl,
                      String githubUrl, Integer number, User user, User codeReviewer) {
        this.status = status;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.githubUrl = githubUrl;
        this.number = number;
        this.user = user;
        this.codeReviewer = codeReviewer;
    }

    public Assignment(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getReviewVideoUrl() {
        return reviewVideoUrl;
    }

    public void setReviewVideoUrl(String reviewVideoUrl) {
        this.reviewVideoUrl = reviewVideoUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCodeReviewer() {
        return codeReviewer;
    }

    public void setCodeReviewer(User codeReviewer) {
        this.codeReviewer = codeReviewer;
    }
}

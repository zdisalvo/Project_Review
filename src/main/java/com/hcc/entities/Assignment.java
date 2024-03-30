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

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "code_reviewer_id")
    private Long codeReviewerId;

    public Assignment(Long id, String status, String branch, String reviewVideoUrl,
                      String githubUrl, Integer number, Long userId, Long codeReviewerId) {
        this.id = id;
        this.status = status;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.githubUrl = githubUrl;
        this.number = number;
        this.userId = userId;
        this.codeReviewerId = codeReviewerId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCodeReviewerId() {
        return codeReviewerId;
    }

    public void setCodeReviewerId(Long codeReviewerId) {
        this.codeReviewerId = codeReviewerId;
    }
}

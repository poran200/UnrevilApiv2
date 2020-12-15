package com.unriviel.api.model;

import com.unriviel.api.model.audit.DateAudit;
import com.unriviel.api.validation.annotation.NullOrNotBlank;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "USER")
public class User extends DateAudit {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "User email cannot be null")
    private String email;

    @Column(name = "USERNAME", unique = true)
    @NullOrNotBlank(message = "Username can not be blank")
    private String username;

    @Column(name = "PASSWORD")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column(name = "FULL_NAME")
    @NullOrNotBlank(message = "Full name can not be blank")
    private String fullName;


    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    private Set<Role> roles ;

    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
    private Boolean isEmailVerified;
    private int  totalUpload;
    private  int totalApproved;

    private  int totalAssigned;
    private int totalReviewed;

    public User() {
        super();
    }

    public User(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        fullName = user.getFullName();
        email = user.getEmail();
        active = user.getActive();
        roles = user.getRoles();
        isEmailVerified = user.getEmailVerified();
    }

    public  void increaseUpload(){
        this.totalUpload = this.getTotalUpload()+1;
    }
    public  void increaseApproved(){
        this.totalApproved = this.getTotalApproved()+1;
    }
    public  void increaseAssigned(){
        this.totalAssigned = this.getTotalAssigned()+1;
    }
    public  void increaseReviewed(){
        this.totalReviewed = this.getTotalReviewed()+1;
    }
    public int getTotalUpload() {
        return totalUpload;
    }

    public int getTotalApproved() {
        return totalApproved;
    }

    public int getTotalAssigned() {
        return totalAssigned;
    }

    public int getTotalReviewed() {
        return totalReviewed;
    }

    public void addRole(Role role) {
        if (roles== null){
            this.roles = new HashSet<>();
        }
        roles.add(role);
        role.getUserList().add(this);
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void removeRole(Role role) {
        roles.remove(role);

    }

    public void markVerificationConfirmed() {
        setEmailVerified(true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> authorities) {
        if (roles== null) this.roles = new HashSet<>();
        roles = authorities;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + '\'' + ", username='" + username + '\'' + ", password='"
                + password + '\'' + ", fullName='" + fullName + '\'' +  '\'' + ", active="
                + active + ", roles=" + roles + ", isEmailVerified=" + isEmailVerified + '}';
    }
}

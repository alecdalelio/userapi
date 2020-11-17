package com.tts.usersapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class UserV2 {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_Id")
    private Long id;
    
    @Length(max=20)
    @ApiModelProperty(notes = "User's first name")
    private String firstName;

    @Length(max=2)
    @ApiModelProperty(notes = "User's last name")
    private String lastName;
    
    @Length(min=4)
    @Length(max=20)
    @ApiModelProperty(notes = "User's state of residence")
    private String state;

    public UserV2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", state=" + state + "]";
    }

    public UserV2(String firstName, String lastName, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = state;
    }

}


package com.lakesidemutual.claimprocessing.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PolicyHolder {
    
    @Id
    private String id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    
    @Embedded
    private Address address;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Required by JPA
    protected PolicyHolder() {}
    
    public PolicyHolder(String firstName, String lastName, String email, String phoneNumber, Address address) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void updateContactInfo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateAddress(Address address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }
}

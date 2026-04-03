package com.crm.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interactions")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(length = 20)
    private String type; // CALL, EMAIL, MEETING

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "interaction_date")
    private LocalDateTime interactionDate;

    // ==========================================
    // Constructors
    // ==========================================

    public Interaction() {
    }

    public Interaction(Long id, Long customerId, String userName,
                       String type, String notes, LocalDateTime interactionDate) {
        this.id = id;
        this.customerId = customerId;
        this.userName = userName;
        this.type = type;
        this.notes = notes;
        this.interactionDate = interactionDate;
    }

    @PrePersist
    protected void onCreate() {
        if (this.interactionDate == null) {
            this.interactionDate = LocalDateTime.now();
        }
    }

    // ==========================================
    // Getters
    // ==========================================

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getInteractionDate() {
        return interactionDate;
    }

    // ==========================================
    // Setters
    // ==========================================

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setInteractionDate(LocalDateTime interactionDate) {
        this.interactionDate = interactionDate;
    }

    // ==========================================
    // toString()
    // ==========================================

    @Override
    public String toString() {
        return "Interaction{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", notes='" + notes + '\'' +
                ", interactionDate=" + interactionDate +
                '}';
    }
}

package com.crm.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunities")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "deal_name", nullable = false, length = 150)
    private String dealName;

    @Column(precision = 10)
    private Double amount;

    @Column(length = 20)
    private String stage; // PROSPECT, NEGOTIATION, WON, LOST

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ==========================================
    // Constructors
    // ==========================================

    public Opportunity() {
    }

    public Opportunity(Long id, Long customerId, String dealName, Double amount,
                       String stage, LocalDate closeDate, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.dealName = dealName;
        this.amount = amount;
        this.stage = stage;
        this.closeDate = closeDate;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
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

    public String getDealName() {
        return dealName;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStage() {
        return stage;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ==========================================
    // toString()
    // ==========================================

    @Override
    public String toString() {
        return "Opportunity{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", dealName='" + dealName + '\'' +
                ", amount=" + amount +
                ", stage='" + stage + '\'' +
                ", closeDate=" + closeDate +
                ", createdAt=" + createdAt +
                '}';
    }
}

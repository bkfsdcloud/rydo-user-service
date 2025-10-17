package com.adroitfirm.rydo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "driver_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Driver reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "document_type", length = 50)
    private String documentType; // LICENSE, AADHAR, PAN, RC, INSURANCE

    @Column(name = "document_number", length = 50)
    private String documentNumber;

    @Column(name = "document_url", length = 255)
    private String documentUrl;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    private Boolean verified = false;

    @Column(name = "uploaded_at")
    private java.time.LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = java.time.LocalDateTime.now();
    }
}
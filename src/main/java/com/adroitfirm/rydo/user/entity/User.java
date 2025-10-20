package com.adroitfirm.rydo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    private String phone;

    @Column(length = 255)
    private String password;

    @Column(length = 20)
    private String role; // CUSTOMER, DRIVER, ADMIN

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    @Column(name = "is_active")
    @Default
    private Boolean isActive = true;

    @Column(name = "device_id", length = 100)
    private String deviceId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
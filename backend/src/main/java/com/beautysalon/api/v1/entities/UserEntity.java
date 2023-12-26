package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity /*implements UserDetails*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "BIT(1) NOT NULL DEFAULT TRUE")
    private boolean accountNonExpired;

    @Column(nullable = false, columnDefinition = "BIT(1) NOT NULL DEFAULT TRUE")
    private boolean accountNonLocked;

    @Column(nullable = false, columnDefinition = "BIT(1) NOT NULL DEFAULT TRUE")
    private boolean credentialsNonExpired;

    @Column(nullable = false, columnDefinition = "BIT(1) NOT NULL DEFAULT TRUE")
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "master_id")
    private Master master;

//    @OneToOne
//    @JoinColumn(name = "administrator_id")
//    private Administrator administrator;

//    @ManyToMany
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id")}
//    )
//    private Set<UserRole> authorities;

    @CreationTimestamp
    private LocalDateTime dateTimeOfCreated;

    @UpdateTimestamp
    private LocalDateTime dateTimeOfUpdated;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
//            mappedBy = "user")
//    private List<Image> images = new ArrayList<>();

    private Long previewImageId;

    public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Master master, LocalDateTime dateTimeOfCreated, LocalDateTime dateTimeOfUpdated, List<Image> images, Long previewImageId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.master = master;
        this.dateTimeOfCreated = dateTimeOfCreated;
        this.dateTimeOfUpdated = dateTimeOfUpdated;
        this.previewImageId = previewImageId;
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

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public LocalDateTime getDateTimeOfCreated() {
        return dateTimeOfCreated;
    }

    public void setDateTimeOfCreated(LocalDateTime dateTimeOfCreated) {
        this.dateTimeOfCreated = dateTimeOfCreated;
    }

    public LocalDateTime getDateTimeOfUpdated() {
        return dateTimeOfUpdated;
    }

    public void setDateTimeOfUpdated(LocalDateTime dateTimeOfUpdated) {
        this.dateTimeOfUpdated = dateTimeOfUpdated;
    }

//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }

//    public void addImageToProduct(Image image) {
////        image.setUser(this);
//        images.add(image);
//    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return accountNonExpired == that.accountNonExpired && accountNonLocked == that.accountNonLocked && credentialsNonExpired == that.credentialsNonExpired && enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(master, that.master) && Objects.equals(dateTimeOfCreated, that.dateTimeOfCreated) && Objects.equals(dateTimeOfUpdated, that.dateTimeOfUpdated) && Objects.equals(previewImageId, that.previewImageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, master, dateTimeOfCreated, dateTimeOfUpdated, previewImageId);
    }
}

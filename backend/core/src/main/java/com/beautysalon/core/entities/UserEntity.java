package com.beautysalon.core.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonExpired = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonLocked = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean credentialsNonExpired = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Master master;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Administrator administrator;

    @Column(name = "role", nullable = false, unique = true)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime dateTimeOfCreated;

    @UpdateTimestamp
    private LocalDateTime dateTimeOfUpdated;

    @OneToMany(mappedBy = "user")
    private List<Image> images = new ArrayList<>();


    public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Master master, Administrator administrator, Set<UserRole> authorities, LocalDateTime dateTimeOfCreated, LocalDateTime dateTimeOfUpdated, List<Image> images, Long previewImageId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.master = master;
        this.administrator = administrator;
        this.roles = authorities;
        this.dateTimeOfCreated = dateTimeOfCreated;
        this.dateTimeOfUpdated = dateTimeOfUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
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

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public Set<UserRole> getAuthorities() {
        return roles;
    }

    public void setAuthorities(Set<UserRole> authorities) {
        this.roles = authorities;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        image.setUser(this);
        images.add(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return accountNonExpired == that.accountNonExpired && accountNonLocked == that.accountNonLocked && credentialsNonExpired == that.credentialsNonExpired && enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(master, that.master) && Objects.equals(administrator, that.administrator) && Objects.equals(roles, that.roles) && Objects.equals(dateTimeOfCreated, that.dateTimeOfCreated) && Objects.equals(dateTimeOfUpdated, that.dateTimeOfUpdated) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, roles, dateTimeOfCreated, dateTimeOfUpdated, images);
    }
}

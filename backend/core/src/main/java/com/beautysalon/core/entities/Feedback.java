package com.beautysalon.core.entities;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "feedbacks")
@Check(constraints = "rating >= 1 AND rating <= 5")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Client author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "feedbacks_administrators",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "administrator_id")
    )
    private Administrator administrator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "feedbacks_masters",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "master_id")
    )
    private Master master;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    @Column(name = "rating")
    private int rating;

    @Column(name = "text")
    private String text;


    public Feedback() {

    }

    public Feedback(Long id, Client author, Master master, Administrator administrator, LocalDateTime dateCreated, LocalDateTime dateUpdated, int rating, String text) {
        this.id = id;
        this.author = author;
        this.master = master;
        this.administrator = administrator;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.rating = rating;
        this.text = text;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getAuthor() {
        return author;
    }

    public void setAuthor(Client author) {
        this.author = author;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return rating == feedback.rating && Objects.equals(id, feedback.id) && Objects.equals(author, feedback.author) && Objects.equals(master, feedback.master) && Objects.equals(administrator, feedback.administrator) && Objects.equals(dateCreated, feedback.dateCreated) && Objects.equals(dateUpdated, feedback.dateUpdated) && Objects.equals(text, feedback.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, master, administrator, dateCreated, dateUpdated, rating, text);
    }
}
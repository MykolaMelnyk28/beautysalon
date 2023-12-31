package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    public Feedback(Long id, Client author, LocalDateTime dateCreated, LocalDateTime dateUpdated, int rating, String text) {
        this.id = id;
        this.author = author;
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
        return rating == feedback.rating && Objects.equals(id, feedback.id) && Objects.equals(author, feedback.author) && Objects.equals(dateCreated, feedback.dateCreated) && Objects.equals(dateUpdated, feedback.dateUpdated) && Objects.equals(text, feedback.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, dateCreated, dateUpdated, rating, text);
    }
}
package com.beautysalon.api.v1.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FeedbackDto {

    private Long id;
    private ClientDto author;
    private EmployeeDto recipient;
    private LocalDateTime dateCreated;
    private int rating;
    private String text;

    public FeedbackDto() {
    }

    public FeedbackDto(Long id, ClientDto author, EmployeeDto recipient, LocalDateTime dateCreated, int rating, String text) {
        this.id = id;
        this.author = author;
        this.recipient = recipient;
        this.dateCreated = dateCreated;
        this.rating = rating;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDto getAuthor() {
        return author;
    }

    public void setAuthor(ClientDto author) {
        this.author = author;
    }

    public EmployeeDto getRecipient() {
        return recipient;
    }

    public void setRecipient(EmployeeDto recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
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
        FeedbackDto that = (FeedbackDto) o;
        return rating == that.rating && Objects.equals(id, that.id) && Objects.equals(author, that.author) && Objects.equals(recipient, that.recipient) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, recipient, dateCreated, rating, text);
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                "id=" + id +
                ", author=" + author +
                ", recipient=" + recipient +
                ", dateCreated=" + dateCreated +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                '}';
    }
}

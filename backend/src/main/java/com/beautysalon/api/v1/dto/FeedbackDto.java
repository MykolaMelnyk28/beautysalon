package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.dto.validation.OnAlways;
import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPatch;
import com.beautysalon.api.v1.dto.validation.OnPut;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.aspectj.lang.annotation.Before;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class FeedbackDto {

    @Min(value = 1, message = "Id can not be negative.",
            groups = {OnAlways.class})
    private Long id;
    @Valid
    @NotNull(message = "must not be null",
            groups = {OnCreate.class, OnPut.class})
    private ClientDto author;
    @Valid
    @NotNull(message = "must not be null",
            groups = {OnCreate.class, OnPut.class})
    private EmployeeDto recipient;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateCreated;
    @Range(min = 1, max = 5, message = "rating must in range 1-5",
            groups = {OnAlways.class})
    private int rating;
    @NotBlank(message = "text must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Length(max = 20, message = "text must be 20 characters or less.",
            groups = {OnAlways.class})
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

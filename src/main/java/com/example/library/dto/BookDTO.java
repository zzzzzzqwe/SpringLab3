package com.example.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private Long id;
    private String title;
    private AuthorDTO author;
    private PublisherDTO publisher;
    private List<CategoryDTO> categories;

    public BookDTO() {}

    public BookDTO(Long id, String title, AuthorDTO author, PublisherDTO publisher, List<CategoryDTO> categories) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.categories = categories;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public AuthorDTO getAuthor() { return author; }
    public void setAuthor(AuthorDTO author) { this.author = author; }

    public PublisherDTO getPublisher() { return publisher; }
    public void setPublisher(PublisherDTO publisher) { this.publisher = publisher; }

    public List<CategoryDTO> getCategories() { return categories; }
    public void setCategories(List<CategoryDTO> categories) { this.categories = categories; }
}
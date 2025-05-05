package com.example.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublisherDTO {
    private Long id;
    private String name;
    private List<BookDTO> books;

    public PublisherDTO() {}

    public PublisherDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PublisherDTO(Long id, String name, List<BookDTO> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<BookDTO> getBooks() { return books; }
    public void setBooks(List<BookDTO> books) { this.books = books; }
}

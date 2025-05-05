package com.example.library.mapper;

import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookDTO;
import com.example.library.dto.CategoryDTO;
import com.example.library.dto.PublisherDTO;
import com.example.library.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor() != null ? new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName()) : null,
                book.getPublisher() != null ? new PublisherDTO(book.getPublisher().getId(), book.getPublisher().getName(), null) : null,
                book.getCategories() != null
                        ? book.getCategories().stream()
                        .map(cat -> new CategoryDTO(cat.getId(), cat.getName(), null))
                        .collect(Collectors.toList())
                        : List.of()
        );
    }
}

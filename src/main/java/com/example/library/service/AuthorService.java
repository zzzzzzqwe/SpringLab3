package com.example.library.service;

import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorJdbcRepository;
import com.example.library.repository.BookJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorJdbcRepository authorRepository;
    private final BookJdbcRepository bookRepository;

    public AuthorService(AuthorJdbcRepository authorRepository, BookJdbcRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(author -> {
                    List<BookDTO> books = bookRepository.findAll().stream()
                            .filter(book -> book.getAuthor().getId().equals(author.getId()))
                            .map(BookMapper::toDTO)
                            .collect(Collectors.toList());
                    return new AuthorDTO(author.getId(), author.getName(), books);
                })
                .collect(Collectors.toList());
    }

    public AuthorDTO getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<BookDTO> books = bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().getId().equals(author.getId()))
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());

        return new AuthorDTO(author.getId(), author.getName(), books);
    }

    public void create(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        authorRepository.save(author);
    }

    public void update(Long id, AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        authorRepository.update(id, author);
    }

    public void delete(Long id) {
        authorRepository.delete(id);
    }
}

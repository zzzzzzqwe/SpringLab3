package com.example.library.service;

import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookCreateDTO;
import com.example.library.dto.BookDTO;
import com.example.library.dto.CategoryDTO;
import com.example.library.dto.PublisherDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.model.Publisher;
import com.example.library.repository.AuthorJdbcRepository;
import com.example.library.repository.BookJdbcRepository;
import com.example.library.repository.CategoryJdbcRepository;
import com.example.library.repository.PublisherJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookJdbcRepository bookRepository;
    private final AuthorJdbcRepository authorRepository;
    private final PublisherJdbcRepository publisherRepository;
    private final CategoryJdbcRepository categoryRepository;

    public BookService(BookJdbcRepository bookRepository, AuthorJdbcRepository authorRepository,
                       PublisherJdbcRepository publisherRepository, CategoryJdbcRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BookDTO> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(book -> {
                    AuthorDTO author = new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName());
                    PublisherDTO publisher = new PublisherDTO(book.getPublisher().getId(), book.getPublisher().getName());
                    List<CategoryDTO> categories = book.getCategories() != null
                            ? book.getCategories().stream()
                            .map(cat -> new CategoryDTO(cat.getId(), cat.getName()))
                            .collect(Collectors.toList())
                            : List.of();
                    return new BookDTO(book.getId(), book.getTitle(), author, publisher, categories);
                })
                .collect(Collectors.toList());
    }

    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        AuthorDTO author = new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName());
        PublisherDTO publisher = new PublisherDTO(book.getPublisher().getId(), book.getPublisher().getName());
        List<CategoryDTO> categories = book.getCategories() != null
                ? book.getCategories().stream()
                .map(cat -> new CategoryDTO(cat.getId(), cat.getName()))
                .collect(Collectors.toList())
                : List.of();

        return new BookDTO(book.getId(), book.getTitle(), author, publisher, categories);
    }

    public void create(BookCreateDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        List<Category> categories = bookDTO.getCategoryIds()
                .stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategories(categories);

        bookRepository.save(book);
    }

    public void update(Long id, BookCreateDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        List<Category> categories = bookDTO.getCategoryIds()
                .stream()
                .map(catId -> categoryRepository.findById(catId)
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategories(categories);

        bookRepository.update(id, book);
    }

    public void delete(Long id) {
        bookRepository.delete(id);
    }
}
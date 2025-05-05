package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.dto.PublisherDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Publisher;
import com.example.library.repository.BookJdbcRepository;
import com.example.library.repository.PublisherJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherJdbcRepository publisherRepository;
    private final BookJdbcRepository bookRepository;

    public PublisherService(PublisherJdbcRepository publisherRepository, BookJdbcRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public List<PublisherDTO> getAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisher -> {
                    List<BookDTO> books = bookRepository.findAll().stream()
                            .filter(book -> book.getPublisher().getId().equals(publisher.getId()))
                            .map(BookMapper::toDTO)
                            .collect(Collectors.toList());
                    return new PublisherDTO(publisher.getId(), publisher.getName(), books);
                })
                .collect(Collectors.toList());
    }

    public PublisherDTO getById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        List<BookDTO> books = bookRepository.findAll().stream()
                .filter(book -> book.getPublisher().getId().equals(publisher.getId()))
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());

        return new PublisherDTO(publisher.getId(), publisher.getName(), books);
    }

    public void create(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisherRepository.save(publisher);
    }

    public void update(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisherRepository.update(id, publisher);
    }

    public void delete(Long id) {
        publisherRepository.delete(id);
    }
}

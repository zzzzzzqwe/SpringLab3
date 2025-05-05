package com.example.library.controller;

import com.example.library.dto.PublisherDTO;
import com.example.library.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherDTO> getAll() {
        return publisherService.getAll();
    }

    @GetMapping("/{id}")
    public PublisherDTO getById(@PathVariable Long id) {
        return publisherService.getById(id);
    }

    @PostMapping
    public void create(@RequestBody PublisherDTO dto) {
        publisherService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PublisherDTO dto) {
        publisherService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }
}

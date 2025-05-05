package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.dto.CategoryDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Category;
import com.example.library.repository.BookJdbcRepository;
import com.example.library.repository.CategoryJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryJdbcRepository categoryRepository;
    private final BookJdbcRepository bookRepository;

    public CategoryService(CategoryJdbcRepository categoryRepository, BookJdbcRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    List<BookDTO> books = bookRepository.findAll().stream()
                            .filter(book -> book.getCategories().stream()
                                    .anyMatch(cat -> cat.getId().equals(category.getId())))
                            .map(BookMapper::toDTO)
                            .collect(Collectors.toList());
                    return new CategoryDTO(category.getId(), category.getName(), books);
                })
                .collect(Collectors.toList());
    }

    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<BookDTO> books = bookRepository.findAll().stream()
                .filter(book -> book.getCategories().stream()
                        .anyMatch(cat -> cat.getId().equals(category.getId())))
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());

        return new CategoryDTO(category.getId(), category.getName(), books);
    }

    public void create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    public void update(Long id, CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.update(id, category);
    }

    public void delete(Long id) {
        categoryRepository.delete(id);
    }
}

package com.example.librarysystem.entity.concrete;

import com.example.librarysystem.entity.enums.Category;
import java.util.Objects;

public class Book {
    private Long id;
    private Author author;
    private String name;
    private Double price;
    private String edition;
    private Category category;

    // Basic constructor
    public Book(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }

    // Detailed constructor
    public Book(Long id, Author author, String name, Double price, String edition, Category category) {
        this.setId(id);
        this.setAuthor(author);
        this.setName(name);
        this.setPrice(price);
        this.setEdition(edition);
        this.setCategory(category);
    }

    public Long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getEdition() {
        return edition;
    }

    public Category getCategory() {
        return category;
    }

    private void setId(Long id) {
        Objects.requireNonNull(id, "ID argument cannot be null");
        if (id <= 0) {
            throw new IllegalArgumentException("ID cannot be equal or less than zero");
        }
        this.id = id;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "Name argument cannot be null");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name argument cannot be empty");
        }
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPrice(Double price) {
        Objects.requireNonNull(price, "Price argument cannot be null");
        if (price <= 0) {
            throw new IllegalArgumentException("Price argument must be greater than zero");
        }
        this.price = price;
    }

    public void setEdition(String edition) {
        Objects.requireNonNull(edition, "Edition argument cannot be null");
        this.edition = edition;
    }

    public void setCategory(Category category) {
        Objects.requireNonNull(category, "Category argument cannot be null");
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book: " + name + " | Author: " + (author != null ? author.getName() : "Unknown") + " | Edition: " + edition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
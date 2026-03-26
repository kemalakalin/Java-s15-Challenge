package com.example.librarysystem.entity.concrete;

import java.util.Objects;

public class Librarian {
    private Long librarianId;
    private String name;
    private String password;
    private Library library;

    public Librarian() {}

    public Librarian(Long librarianId, String name, String password, Library library) {
        this.setLibrarianId(librarianId);
        this.setName(name);
        this.setPassword(password);
        this.setLibrary(library);
    }

    public Long getLibrarianId() {
        return librarianId;
    }

    public String getName() {
        return name;
    }

    public Library getLibrary() {
        return library;
    }

    public String getPassword() {
        return password;
    }

    private void setLibrarianId(Long librarianId) {
        Objects.requireNonNull(librarianId, "Librarian ID cannot be null");
        if (librarianId <= 0) {
            throw new IllegalArgumentException("Librarian ID must be greater than zero");
        }
        this.librarianId = librarianId;
    }

    public void setLibrary(Library library) {
        Objects.requireNonNull(library, "Library argument cannot be null");
        this.library = library;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "Name argument cannot be null");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name argument cannot be empty");
        }
        this.name = name;
    }

    private void setPassword(String password) {
        Objects.requireNonNull(password, "Password cannot be null");
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
    }

    public String createBill(Reader reader, Book book) {
        Objects.requireNonNull(reader, "Reader cannot be null");
        Objects.requireNonNull(book, "Book cannot be null");

        Double price = book.getPrice();
        return String.format("\n--- INVOICE ---\nReader: %s\nBook: %s\nAmount: %.2f TL\n---------------",
                reader.getName(), book.getName(), price);
    }

    public String searchBook(String name) {
        for (Book book : library.getBooks()) {
            if (book.getName().equalsIgnoreCase(name)) {
                return "The book " + book.getName() + " has been found.";
            }
        }
        return "Searched book is not in the library";
    }

    public boolean verifyMember(Reader reader) {
        return library.getReaders().contains(reader) && reader.getMember() != null;
    }

    @Override
    public String toString() {
        return "Librarian: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Librarian librarian = (Librarian) o;
        return Objects.equals(librarianId, librarian.librarianId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(librarianId);
    }
}
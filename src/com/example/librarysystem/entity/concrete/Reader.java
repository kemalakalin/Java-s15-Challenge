package com.example.librarysystem.entity.concrete;

import com.example.librarysystem.entity.AbstractPerson;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Reader extends AbstractPerson {
    private Member member;
    private final Set<Book> books = new HashSet<>();
    private Double budget;

    public Reader(Long id, String name, Member member) {
        super(id, name);
        this.setMember(member);
        this.setBudget(0.0);
    }

    public Reader(Long id, String name, Address address, String phoneNumber, String email, Member member, Double budget) {
        super(id, name, address, phoneNumber, email);
        this.setMember(member);
        this.setBudget(budget);
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

    public Member getMember() { return member; }

    public Double getBudget() { return budget; }

    public void setMember(Member member) {
        this.member = Objects.requireNonNull(member, "Member cannot be null");
    }

    public void setBudget(Double budget) {
        if (budget < 0) throw new IllegalArgumentException("Budget cannot be negative");
        this.budget = budget;
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book);
        if (books.size() >= 5) throw new IllegalStateException("Limit exceeded (Max 5 books)");
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    @Override
    public void whoYouAre() { System.out.println("I'm reader " + getName()); }

    @Override
    public String toString() {
        return String.format("Reader: %s | Budget: %.2f | Books: %d",
                getName(), budget, books.size());
    }
}
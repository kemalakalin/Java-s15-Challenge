package com.example.librarysystem.entity;

import com.example.librarysystem.entity.concrete.Address;
import java.util.Objects;

/**
 * Bu sınıf, sistemdeki tüm insan figürleri (Yazar, Okuyucu, Kütüphaneci vb.) için
 * ortak özellikleri ve davranışları tanımlayan soyut bir temel sınıftır.
 */
public abstract class AbstractPerson {
    private Long id;
    private String name;
    private Address address;
    private String phoneNumber;
    private String email;

    // Temel yapıcı metot
    public AbstractPerson(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }

    // Detaylı yapıcı metot
    public AbstractPerson(Long id, String name, Address address, String phoneNumber, String email) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }

    // Getter Metotları
    public Long getId() { return id; }
    public String getName() { return name; }
    public Address getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    // Setter Metotları ve Validasyonlar
    private void setId(Long id) {
        Objects.requireNonNull(id, "ID argument cannot be null");
        if (id <= 0) {
            throw new IllegalArgumentException("ID argument cannot be equal or less than zero");
        }
        this.id = id;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "Name argument cannot be null");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name argument cannot be empty");
        }
        this.name = name;
    }

    public void setAddress(Address address) {
        Objects.requireNonNull(address, "Address argument cannot be null");
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        Objects.requireNonNull(phoneNumber, "Phone number argument cannot be null");
        if (phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number argument cannot be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        Objects.requireNonNull(email, "Email argument cannot be null");
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email argument cannot be empty");
        }
        this.email = email;
    }

    /**
     * Alt sınıfların kendilerini tanıtması için zorunlu kılınan soyut metot.
     */
    public abstract void whoYouAre();

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s", id, name, (email != null ? email : "N/A"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPerson person = (AbstractPerson) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
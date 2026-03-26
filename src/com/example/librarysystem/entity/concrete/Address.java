package com.example.librarysystem.entity.concrete;

import java.util.Objects;

public class Address {
    private String city;
    private Integer postalCode;
    private String street;

    public Address(String city, Integer postalCode, String street) {
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getPostalCode() { return postalCode; }
    public void setPostalCode(Integer postalCode) { this.postalCode = postalCode; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    @Override
    public String toString() {
        return String.format("City: %s, Street: %s, Postal Code: %d", city, street, postalCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        // Checking all fields for better consistency
        return Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, postalCode, street);
    }
}
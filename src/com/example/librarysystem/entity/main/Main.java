package com.example.librarysystem.entity.main;

import com.example.librarysystem.entity.concrete.*;
import com.example.librarysystem.entity.enums.Category;
import com.example.librarysystem.entity.enums.MemberShipType;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        // Librarian nesnesini kütüphane ile ilişkilendirerek oluşturuyoruz
        Librarian librarian = new Librarian(1L, "Admin", "1234", library);
        library.setLibrarian(librarian);

        setupMockData(library);

        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Book Operations");
            System.out.println("2. Reader Operations");
            System.out.println("3. Author Operations");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            if (!userInput.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                userInput.next();
                continue;
            }

            int choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1: handleBookOperations(library, userInput); break;
                case 2: handleReaderOperations(library, userInput); break;
                case 3: handleAuthorOperations(library, userInput); break;
                case 4:
                    System.out.println("Exiting system... Goodbye!");
                    System.exit(0);
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void handleBookOperations(Library library, Scanner sc) {
        System.out.println("\n--- Book Operations ---");
        System.out.println("1. Add Book (Detailed)\n2. Search By Name\n3. List All Books\n0. Back");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                try {
                    System.out.print("Book ID: "); Long id = sc.nextLong(); sc.nextLine();
                    System.out.print("Book Name: "); String name = sc.nextLine();
                    System.out.print("Price: "); Double price = sc.nextDouble(); sc.nextLine();
                    System.out.print("Edition: "); String edition = sc.nextLine();
                    System.out.println("Category (FICTION, SCIENCE, etc.): ");
                    Category category = Category.valueOf(sc.nextLine().toUpperCase());

                    // Mevcut yazarlardan birini seçmek veya yeni oluşturmak gerekir.
                    // Test amaçlı MockData'daki Stefan Zweig'ı kullanıyoruz:
                    Author defaultAuthor = library.getAuthors().iterator().next();

                    Book newBook = new Book(id, defaultAuthor, name, price, edition, category);
                    library.addBook(newBook);
                    System.out.println("Book added successfully.");
                } catch (Exception e) {
                    System.out.println("Error adding book: " + e.getMessage());
                }
                break;
            case 2:
                System.out.print("Enter Book Name: ");
                String search = sc.nextLine();
                System.out.println(library.getLibrarian().searchBook(search));
                break;
            case 3:
                System.out.println("\n--- Library Collection ---");
                library.getBooks().forEach(System.out::println);
                break;
        }
    }

    private static void handleReaderOperations(Library library, Scanner sc) {
        System.out.println("\n--- Reader Operations ---");
        System.out.println("1. Borrow Book\n2. Return Book\n3. Show Reader Info\n0. Back");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        System.out.print("Reader Name: "); String rName = sc.nextLine();
        Reader reader = library.getReaders().stream()
                .filter(r -> r.getName().equalsIgnoreCase(rName))
                .findFirst().orElse(null);

        if (reader == null) {
            System.out.println("Reader not found!");
            return;
        }

        switch (choice) {
            case 1:
                System.out.print("Book Name to Borrow: "); String bName = sc.nextLine();
                Book bookToLend = library.getBooks().stream()
                        .filter(b -> b.getName().equalsIgnoreCase(bName))
                        .findFirst().orElse(null);
                if (bookToLend != null) {
                    library.lendBook(reader, bookToLend);
                } else {
                    System.out.println("Book not in library stock!");
                }
                break;
            case 2:
                System.out.print("Book Name to Return: "); String retName = sc.nextLine();
                Book bookToReturn = reader.getBooks().stream()
                        .filter(b -> b.getName().equalsIgnoreCase(retName))
                        .findFirst().orElse(null);
                if (bookToReturn != null) {
                    library.takeBook(reader, bookToReturn);
                    System.out.println("Return successful.");
                } else {
                    System.out.println("Reader doesn't have this book.");
                }
                break;
            case 3:
                System.out.println(reader);
                System.out.println("Membership Date: " + reader.getMember().getMemberShipDate());
                System.out.println("Current Books: " + reader.getBooks());
                break;
        }
    }

    private static void handleAuthorOperations(Library library, Scanner sc) {
        System.out.println("\n--- Author List ---");
        if (library.getAuthors().isEmpty()) {
            System.out.println("No authors registered.");
        } else {
            library.getAuthors().forEach(a ->
                    System.out.println(a.getName() + " | Written Books: " + a.getBooks().size()));
        }
    }

    private static void setupMockData(Library library) {
        // Yazar ve Kitap oluşturma
        Author stefan = new Author(1L, "Stefan Zweig");
        Book theRoyalGame = new Book(1L, stefan, "The Royal Game", 250.0, "16", Category.FICTION);

        // Çift yönlü ilişkiyi kurma
        stefan.addBook(theRoyalGame);

        // Kütüphaneye ekleme
        library.addAuthor(stefan);
        library.addBook(theRoyalGame);

        // Okuyucu oluşturma
        Member member = new Member(1L, MemberShipType.BASIC);
        Reader reader = new Reader(1L, "Dila", member);
        reader.setBudget(1000.0);
        library.addReader(reader);
    }
}
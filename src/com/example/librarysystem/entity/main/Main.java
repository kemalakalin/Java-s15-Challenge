package com.example.librarysystem.entity.main;

import com.example.librarysystem.entity.concrete.*;
import com.example.librarysystem.entity.enums.Category;
import com.example.librarysystem.entity.enums.MemberShipType;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Librarian librarian = new Librarian(1L, "Admin", "1234", library);
        library.setLibrarian(librarian);

        setupMockData(library);

        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("\n========= LIBRARY MANAGEMENT SYSTEM =========");
            System.out.println("1. Book Operations (Add, Search, List, Update, Delete)");
            System.out.println("2. Reader Operations (Borrow, Return, Info)");
            System.out.println("3. Author Operations (List Authors)");
            System.out.println("4. Exit");
            System.out.print("Main Choice: ");

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
        System.out.println("1. Add New Book\n2. Search By Name\n3. List All Books\n4. Update Book Info\n5. Delete Book\n0. Back");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1: // ADD
                try {
                    System.out.print("Book ID: "); Long id = sc.nextLong(); sc.nextLine();
                    System.out.print("Book Name: "); String name = sc.nextLine();
                    System.out.print("Price: "); Double price = sc.nextDouble(); sc.nextLine();
                    System.out.print("Edition: "); String edition = sc.nextLine();
                    System.out.print("Category (FICTION, SCIENCE, HISTORY, etc.): ");
                    Category category = Category.valueOf(sc.nextLine().toUpperCase());

                    Author defaultAuthor = library.getAuthors().iterator().next();
                    Book newBook = new Book(id, defaultAuthor, name, price, edition, category);
                    library.addBook(newBook);
                    System.out.println("Book added successfully.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 2: // SEARCH
                System.out.print("Enter Book Name: ");
                System.out.println(library.getLibrarian().searchBook(sc.nextLine()));
                break;
            case 3: // LIST
                System.out.println("\n--- Current Inventory ---");
                library.getBooks().forEach(System.out::println);
                break;
            case 4: // UPDATE (İSTER: Bilgi Güncelleme)
                System.out.print("Enter Book ID to update: ");
                Long upId = sc.nextLong(); sc.nextLine();
                Book toUpdate = library.getBooks().stream().filter(b -> b.getId().equals(upId)).findFirst().orElse(null);
                if (toUpdate != null) {
                    System.out.print("New Name: "); toUpdate.setName(sc.nextLine());
                    System.out.print("New Price: "); toUpdate.setPrice(sc.nextDouble());
                    System.out.println("Update successful.");
                } else { System.out.println("Book not found!"); }
                break;
            case 5: // DELETE (İSTER: Kitap Silme)
                System.out.print("Enter Book ID to delete: ");
                Long delId = sc.nextLong();
                Book toDelete = library.getBooks().stream().filter(b -> b.getId().equals(delId)).findFirst().orElse(null);
                if (toDelete != null) {
                    library.removeBook(toDelete);
                    System.out.println("Book deleted successfully.");
                } else { System.out.println("Book not found!"); }
                break;
        }
    }

    private static void handleReaderOperations(Library library, Scanner sc) {
        System.out.println("\n--- Reader Operations ---");
        System.out.println("1. Borrow Book\n2. Return Book\n3. Show Reader Info\n0. Back");
        int choice = sc.nextInt(); sc.nextLine();
        if (choice == 0) return;

        System.out.print("Reader Name: "); String rName = sc.nextLine();
        Reader reader = library.getReaders().stream()
                .filter(r -> r.getName().equalsIgnoreCase(rName)).findFirst().orElse(null);

        if (reader == null) { System.out.println("Reader not registered!"); return; }

        switch (choice) {
            case 1: // BORROW
                System.out.print("Book Name: "); String bName = sc.nextLine();
                Book bToLend = library.getBooks().stream()
                        .filter(b -> b.getName().equalsIgnoreCase(bName)).findFirst().orElse(null);
                if (bToLend != null) library.lendBook(reader, bToLend);
                else System.out.println("Book not available.");
                break;
            case 2: // RETURN (Ücret iadesi otomatik Library içinde tetiklenir)
                System.out.print("Book Name: "); String retName = sc.nextLine();
                Book bToRet = reader.getBooks().stream()
                        .filter(b -> b.getName().equalsIgnoreCase(retName)).findFirst().orElse(null);
                if (bToRet != null) library.takeBook(reader, bToRet);
                else System.out.println("This reader doesn't have that book.");
                break;
            case 3: // INFO
                System.out.println(reader);
                System.out.println("Borrowed Books: " + reader.getBooks());
                break;
        }
    }

    private static void handleAuthorOperations(Library library, Scanner sc) {
        library.getAuthors().forEach(a -> System.out.println(a.getName() + " [Books: " + a.getBooks().size() + "]"));
    }

    private static void setupMockData(Library library) {
        Author stefan = new Author(1L, "Stefan Zweig");
        Book theRoyalGame = new Book(1L, stefan, "The Royal Game", 150.0, "16th Ed", Category.FICTION);
        stefan.addBook(theRoyalGame);
        library.addAuthor(stefan);
        library.addBook(theRoyalGame);

        Member member = new Member(1L, MemberShipType.BASIC);
        Reader reader = new Reader(1L, "Dila", member);
        reader.setBudget(500.0);
        library.addReader(reader);
    }
}
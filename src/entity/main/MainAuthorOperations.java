package entity.main;

import entity.Book;
import entity.concrete.*;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

abstract class MainAuthorOperations {
    private MainAuthorOperations() {
    }

    protected static void authorOperations(Scanner scanner, Librarian librarian) {

        String selectedOptionAuthor = "-1";
        do {

            System.out.println("--------------------");
            System.out.println("0-Return Main Menu");
            System.out.println("1-Add Author");
            System.out.println("2-List All Authors");
            System.out.println("3-Add New Book To Author");
            System.out.println("4-List Selected Authors Book");
            System.out.println("5-Update Selected Authors Book");
            System.out.println("6-Delete Selected Authors Book");
            selectedOptionAuthor = scanner.nextLine();


            switch (selectedOptionAuthor) {
                case "0":
                    System.out.println("Returning to main menu");
                    break;
                case "1":
                    addNewAuthortoLibrary(scanner, librarian);
                    break;
                case "2":
                    listAllAuthors(librarian);
                    break;
                case "3":
                    addNewBooktoAuthor(scanner, librarian);
                    break;
                case "4":
                    ListAllBooksofAuthor(scanner, librarian);
                    break;
                case "5":
                    updateAuthorBook(scanner, librarian);
                    break;
                case "6":
                    deleteAuthorBook(scanner, librarian).ifPresent(book -> System.out.println("Deleted Book Author: " + book));
                    break;
                default:
                    System.out.println("Invalid input, select again");

            }


        } while (!selectedOptionAuthor.equals("0"));


    }

    protected static void addNewAuthortoLibrary(Scanner scanner, Librarian librarian) {

        System.out.println("--------------");
        System.out.println("Enter Name");
        String inputName = scanner.nextLine();
        System.out.println("Enter Address");
        String inputAddress = scanner.nextLine();
        System.out.println("Enter Phone Number");
        String inputPhoneNo = scanner.nextLine();

        Author newAuthor = null;
        try {
            newAuthor = new Author(inputName, inputAddress, inputPhoneNo);
        } catch (IllegalArgumentException exception) {
            System.out.println("There is a problem about author creation:" + exception);
        }
        try {
            librarian.getLibrary().addAuthor(newAuthor);
            System.out.println("Author added");
        } catch (IllegalArgumentException exception) {
            System.out.println("Author couldn't add to library:" + exception);

        }
        System.out.println("Returning to menu");
    }


    protected static void ListAllBooksofAuthor(Scanner scanner, Librarian librarian) {

        Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
        selectedAuthor.ifPresent(author -> MainBookOperations.selectOrDisplayBookFromAuthor(librarian, scanner, author, false));

    }

    protected static ArrayList<Author> listAllAuthors(Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Author> allAuthors = new ArrayList<>(librarian.getLibrary().getAuthors());
        for (Author author : allAuthors) {
            System.out.println(allAuthors.indexOf(author) + "-) " + author);
        }
        if (allAuthors.isEmpty()) System.out.println("There is no Authors");
        return allAuthors;
    }

    protected static ArrayList<Author> listAllAuthorsWithBooks(Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Author> allAuthors = new ArrayList<>(librarian.getLibrary().getAuthors().stream().filter(author -> !author.getBooks().isEmpty()).toList());
        for (Author author : allAuthors) {
            System.out.println(allAuthors.indexOf(author) + "-) " + author);
        }
        if (allAuthors.isEmpty()) System.out.println("There is no Authors");
        return allAuthors;
    }

    protected static Optional<Book> addNewBooktoAuthor(Scanner scanner, Librarian librarian /*String name,String address, int phoneNo*/) {
        ArrayList<Author> allAuthors = listAllAuthors(librarian);
        if (allAuthors.isEmpty()) {
            System.out.println("There is no Author");
            return Optional.empty();
        }
        Optional<Integer> selectedAuthorIndex;
        Author selectedAuthor;
        selectedAuthorIndex = InputSelections.getInputIndex(scanner, "Author");
        if (selectedAuthorIndex.isEmpty()) {
            System.out.println("Invalid input, select again");
            return Optional.empty();
        }

        try {
            selectedAuthor = allAuthors.get(selectedAuthorIndex.get());
            ValidationUtil.requireNoNull(selectedAuthor, "Selected Author can not be null");

        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid input, select again");
            return Optional.empty();
        }
        Optional<Book> newBook = MainBookOperations.createBook(scanner, selectedAuthor, Optional.empty(), selectedAuthor);
        if (newBook.isEmpty()) {
            System.out.println("Kitap eklenemedi");
            return Optional.empty();
        }
        try {
            selectedAuthor.newBook(newBook.get());
            return newBook;
        } catch (Exception exception) {
            System.out.println("Kitap eklenemedi" + exception);
            return Optional.empty();
        }


    }


    protected static Optional<Book> updateAuthorBook(Scanner scanner, Librarian librarian) {

        Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
        if (selectedAuthor.isEmpty()) {
            System.out.println("invalid author");
            return Optional.empty();
        }
        //MainAuthorOperations.listAuthorsBooks(librarian, selectedAuthor.get());
        Optional<Book> selectedBook = MainBookOperations.selectOrDisplayBookFromAuthor(librarian, scanner, selectedAuthor.get(), true);
        if (selectedBook.isEmpty()) {
            System.out.println("invalid book");
            return Optional.empty();
        }

        Optional<Book> newBook = MainBookOperations.updateBook(scanner, selectedAuthor.get(), selectedBook.get());
        if (newBook.isEmpty()) {
            System.out.println("invalid book");
            return Optional.empty();
        } else {
            System.out.println("Updated Book Before : \n" + selectedAuthor.get().getBooks().get(selectedBook.get().getBook_ID()));

            librarian.searchBooks(selectedBook.get()).forEach((key, value) -> librarian.getLibrary().addBook(newBook.get(), key));

            newBook.ifPresent(book -> selectedAuthor.get().addBook(book, book.getBook_ID()));
            System.out.println("Updated Book After : \n" + newBook.get());
            return newBook;
        }
    }

    protected static Optional<Book> deleteAuthorBook(Scanner scanner, Librarian librarian) {
        Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
        if (selectedAuthor.isEmpty()) {
            System.out.println("invalid author");
            return Optional.empty();
        }
        //MainAuthorOperations.listAuthorsBooks(librarian, selectedAuthor.get());
        Optional<Book> selectedBook = MainBookOperations.selectOrDisplayBookFromAuthor(librarian, scanner, selectedAuthor.get(), true);
        if (selectedBook.isEmpty()) {
            System.out.println("invalid book");
            return Optional.empty();
        }

        librarian.searchBooks(selectedBook.get()).entrySet().forEach((entry) -> {
            System.out.println("Removed From Library: " + entry);
            librarian.getLibrary().removeBook(entry.getKey());
        });

        selectedAuthor.get().removeBook(selectedBook.get().getBook_ID());

        return selectedBook;
    }


    protected static ArrayList<Book> listAuthorsBooks(Librarian librarian, Author author) {
        System.out.println("--------------");
        ArrayList<Book> allBooks = new ArrayList<>(author.getBooks().values());
        for (Book book : allBooks) {
            System.out.println(allBooks.indexOf(book) + "-) " + book);
        }
        if (allBooks.isEmpty()) System.out.println("There is no Book");
        return allBooks;
    }

    protected static Optional<Author> selectAuthor(Scanner scanner, Librarian librarian) {
        ArrayList<Author> allAuthors = MainAuthorOperations.listAllAuthorsWithBooks(librarian);
        if (allAuthors.isEmpty()) {
            System.out.println("There is no Author");
            return Optional.empty();
        }

        Author selectedAuthor;

        Optional<Integer> selectedAuthorIndex = InputSelections.getInputIndex(scanner, "Author");
        if (selectedAuthorIndex.isEmpty()) {
            System.out.println("Invalid input");
            return Optional.empty();
        }
        try {
            selectedAuthor = allAuthors.get(selectedAuthorIndex.get());
            ValidationUtil.requireNoNull(selectedAuthor, "Selected Author can not be null");

        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid input, select again : " + indexOutOfBoundsException);
            return Optional.empty();
        }

        return Optional.of(selectedAuthor);
    }


}

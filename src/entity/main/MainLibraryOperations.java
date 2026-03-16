package entity.main;

import entity.Book;
import entity.concrete.*;
import entity.utils.ValidationUtil;

import java.util.*;

abstract class MainLibraryOperations {
    private MainLibraryOperations() {
    }

    protected static void libraryOperations(Scanner scanner, Librarian librarian) {

        String selectedOptionLibrary = "-1";
        do {
            System.out.println("--------------------");
            System.out.println("0-Return Main Menu");
            System.out.println("1-List All Books");
            System.out.println("2-Add New Book To Library");
            System.out.println("3-Search Book");
            selectedOptionLibrary = scanner.nextLine();

            switch (selectedOptionLibrary) {
                case "0":
                    System.out.println("Returning to main menu");
                    break;
                case "1":
                    MainBookOperations.listBooksofLibrary(librarian);
                    break;
                case "2":
                    addNewBooktoLibrary(scanner, librarian);
                    break;
                case "3":
                    searchBook(scanner, librarian);
                    break;
                default:
                    System.out.println("Invalid input, select again");

            }


        } while (!selectedOptionLibrary.equals("0"));


    }

    protected static void searchBook(Scanner scanner, Librarian librarian) {

        String selectedSearch = "-1";

        do {

            System.out.println("--------------------");
            System.out.println("Select Method of Search");
            System.out.println("0-Return Menu");
            System.out.println("1-Search by Category");
            System.out.println("2-Search by ID");
            System.out.println("3-Search by Name");
            System.out.println("4-Search by Book");
            selectedSearch = scanner.nextLine();


            switch (selectedSearch) {
                case "0":
                    System.out.println("Returning to menu");
                    break;
                case "1":
                    getBooksByCategory(scanner, librarian);
                    break;
                case "2":
                    getBooksByID(scanner, librarian);
                    break;
                case "3":
                    getBooksByName(scanner, librarian);
                    break;
                case "4":
                    getBooksByBook(scanner, librarian);
                    break;
                default:
            }


        } while (!selectedSearch.equals("0"));

    }

    protected static void getBooksByBook(Scanner scanner, Librarian librarian) {

        do {
            Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
            if (selectedAuthor.isEmpty()) continue;
            //MainAuthorOperations.listAuthorsBooks(librarian, selectedAuthor.get());
            Optional<Book> selectedBook= MainBookOperations.selectBook(librarian,scanner,selectedAuthor.get());
            if (selectedBook.isEmpty()) continue;

            System.out.println("Result : ");
            System.out.println(librarian.getLibrary().getBooks(selectedBook.get()));
            break;

        } while (true);

    }

    protected static void getBooksByName(Scanner scanner, Librarian librarian) {

        System.out.println("--------------------");
        System.out.println("Type Name of the Book");
        Optional<String> selectedSearch;
        selectedSearch = Optional.of(scanner.nextLine());
        Map<UUID, Book> resultBooks = librarian.getLibrary().getBooks(selectedSearch.get());
        if (resultBooks.isEmpty()) {
            System.out.println("There is no Book with entered name : " + selectedSearch.get());
        } else {
            System.out.println("Result : ");
            System.out.println(resultBooks);
        }

    }

    protected static void getBooksByID(Scanner scanner, Librarian librarian) {

        if (!Optional.of(MainBookOperations.listBooksofLibrary(librarian)).get().isEmpty()) {
            System.out.println("--------------------");
            System.out.println("Type ID of Book");
            Optional<String> selectedSearch;
            selectedSearch = Optional.of(scanner.nextLine());

            Map<UUID, Book> resultBooks = librarian.getLibrary().getBooks(UUID.fromString(selectedSearch.get()));

            if (resultBooks.isEmpty()) {
                System.out.println("There is no Book with entered id : " + selectedSearch.get());
            } else {
                System.out.println("Result : ");
                System.out.println(resultBooks);
            }

        }

    }

    protected static void getBooksByCategory(Scanner scanner, Librarian librarian) {
        String selectedCategory = "-1";
        Optional<Class<?>> selectedClass = Optional.empty();
        do {

            System.out.println("--------------------");
            System.out.println("Select Category of Book");
            System.out.println("0-Return Menu");
            System.out.println("1-Study");
            System.out.println("2-Magazine");
            System.out.println("3-Journal");
            selectedCategory = scanner.nextLine();


            switch (selectedCategory) {
                case "0":
                    System.out.println("Returning to menu");
                    break;
                case "1":
                    selectedClass = Optional.of(StudyBook.class);
                    break;
                case "2":
                    selectedClass = Optional.of(Magazine.class);
                    break;
                case "3":
                    selectedClass = Optional.of(Journal.class);
                    break;
                default:
            }

            selectedClass.ifPresentOrElse(
                    c -> MainBookOperations.listBooksofLibrary(librarian, c),
                    () -> {
                        System.out.println("Invalid input, select again");
                    }
            );


        } while (!selectedCategory.equals("0"));


    }


    protected static void addNewBooktoLibrary(Scanner scanner, Librarian librarian) {
        do {
            Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
            if (selectedAuthor.isEmpty()) break;
            Optional<Book> selectedBook = MainBookOperations.selectBook(librarian, scanner, selectedAuthor.get());
            if (selectedBook.isEmpty()) break;
            try {
                librarian.getLibrary().addBook(selectedBook.get());
                System.out.println("Book added to library");
                break;
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("Book couldn't added to library : " + illegalArgumentException);
                continue;
            }

        } while (true);
    }
}


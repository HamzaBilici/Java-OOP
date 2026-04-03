package entity.main;

import entity.Book;
import entity.concrete.*;
import entity.utils.ValidationUtil;

import java.util.*;
import java.util.stream.Collectors;

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
            System.out.println("4-Remove Book From Library");
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
                case "4":
                    System.out.println(removeBookFromLibrary(scanner, librarian) + "removed");
                    break;
                default:
                    System.out.println("Invalid input, select again");

            }


        } while (!selectedOptionLibrary.equals("0"));


    }

    protected static List<Book> searchBook(Scanner scanner, Librarian librarian) {

        String selectedSearch = "-1";

        do {

            System.out.println("--------------------");
            System.out.println("Select Method of Search");
            System.out.println("0-Return Menu");
            System.out.println("1-Search by Category");
            System.out.println("2-Search by ID");
            System.out.println("3-Search by Name");
            System.out.println("4-Search by Book");
            System.out.println("5-Search by Author");
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
                    System.out.println(getBooksByBook(scanner, librarian));
                    break;
                case "5":
                    getBookByAuthor(scanner,librarian);
                        break;
                default:
            }


        } while (!selectedSearch.equals("0"));

        return Collections.emptyList();
    }

    protected static Optional<List<Map.Entry<UUID, Book>>> getBookByAuthor(Scanner scanner, Librarian librarian) {
        Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
        if (selectedAuthor.isEmpty()) return Optional.empty();

        selectedAuthor.get().getBooks().forEach((uuid, book) ->
                System.out.println(librarian.getLibrary().getBooks().entrySet().stream().filter(entry -> entry.getValue().getBook_ID().equals(book.getBook_ID())).toList())
        );
       return Optional.empty();
    }

    protected static Optional<List<Map.Entry<UUID, Book>>> getBooksByBook(Scanner scanner, Librarian librarian) {
        Optional<Author> selectedAuthor = MainAuthorOperations.selectAuthor(scanner, librarian);
        if (selectedAuthor.isEmpty()) return Optional.empty();
        //MainAuthorOperations.listAuthorsBooks(librarian, selectedAuthor.get());
        Optional<Book> selectedBook = MainBookOperations.selectOrDisplayBookFromAuthor(librarian, scanner, selectedAuthor.get(), true);
        if (selectedBook.isEmpty()) return Optional.empty();

        System.out.println("Result : ");
        return Optional.of(librarian.searchBooks(selectedBook.get()).entrySet().stream().toList());
    }

    protected static Optional<Map.Entry<UUID, Book>> removeBookFromLibrary(Scanner scanner, Librarian librarian) {
        ArrayList<Map.Entry<UUID, Book>> bookList = MainBookOperations.listBooksofLibrary(librarian);
        if (bookList.isEmpty()) {
            System.out.println("There is no book");
            return Optional.empty();
        }
        Optional<Integer> selectedBook = InputSelections.getInputIndex(scanner, "book");
        if (selectedBook.isEmpty()) {
            System.out.println("invalid index");
            return Optional.empty();
        }

        librarian.getLibrary().removeBook(bookList.get(selectedBook.get()).getKey());
        return Optional.ofNullable(bookList.get(selectedBook.get()));
    }

    protected static Optional<Map<UUID, Book>> getBooksByName(Scanner scanner, Librarian librarian) {

        System.out.println("--------------------");
        System.out.println("Type Name of the Book");
        Optional<String> selectedSearch = Optional.of(scanner.nextLine());
        if (selectedSearch.get().isBlank()) {
            return Optional.empty();
        }
        Map<UUID, Book> resultBooks = librarian.searchBooks(selectedSearch.get());
        if (resultBooks.isEmpty()) {
            System.out.println("There is no Book with entered name : " + selectedSearch.get());
        }

        System.out.println("Result : ");
        System.out.println(resultBooks);
        return Optional.of(resultBooks);

    }

    protected static Optional<Map<UUID, Book>> getBooksByID(Scanner scanner, Librarian librarian) {

        if (!Optional.of(MainBookOperations.listBooksofLibrary(librarian)).get().isEmpty()) {
            System.out.println("--------------------");
            System.out.println("Type ID of Book");
            Optional<String> selectedSearch = Optional.of(scanner.nextLine());
            if (selectedSearch.get().isBlank()) {
                System.out.println("ID can not be blank");
                return Optional.empty();
            }

            try {
                Map<UUID, Book> resultBooks = librarian.searchBooks(UUID.fromString(selectedSearch.get()));

                if (resultBooks.isEmpty()) {
                    System.out.println("There is no Book with entered id : " + selectedSearch.get());
                    return Optional.empty();
                } else {
                    System.out.println("Result : ");
                    System.out.println(resultBooks);
                    return Optional.of(resultBooks);
                }

            } catch (IllegalArgumentException exception) {
                System.out.println("There is a problem about author creation:" + exception);
                return Optional.empty();
            }

        }
        return Optional.empty();

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
            Optional<Book> selectedBook = MainBookOperations.selectOrDisplayBookFromAuthor(librarian, scanner, selectedAuthor.get(), true);
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


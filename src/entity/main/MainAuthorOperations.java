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
    private  MainAuthorOperations(){}

    protected static void authorOperations(Scanner scanner, Librarian librarian) {

        String selectedOptionAuthor = "-1";
        do {

            System.out.println("--------------------");
            System.out.println("0-Return Main Menu");
            System.out.println("1-Add Author");
            System.out.println("2-List All Authors");
            System.out.println("3-Add New Book To Author");
            System.out.println("4-List Selected Authors Book");
            selectedOptionAuthor = scanner.nextLine();


            switch (selectedOptionAuthor) {
                case "0":
                    System.out.println("Returning to main menu");
                    break;
                case "1":
                    addNewAuthortoLibrary(scanner, librarian);
                    break;
                case "2":
                    listAllAuthors( librarian);
                    break;
                case "3":
                    addNewBooktoAuthor(scanner, librarian);
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

    protected static ArrayList<Author> listAllAuthors( Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Author> allAuthors = new ArrayList<>(librarian.getLibrary().getAuthors());
        for (Author author : allAuthors) {
            System.out.println(allAuthors.indexOf(author) + "-) " + author);
        }
        if (allAuthors.isEmpty()) System.out.println("There is no Authors");
        return allAuthors;
    }

    protected static ArrayList<Author> listAllAuthorsWithBooks( Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Author> allAuthors = new ArrayList<>(librarian.getLibrary().getAuthors().stream().filter(author -> !author.getBooks().isEmpty()).toList());
        for (Author author : allAuthors) {
            System.out.println(allAuthors.indexOf(author) + "-) " + author);
        }
        if (allAuthors.isEmpty()) System.out.println("There is no Authors");
        return allAuthors;
    }

    protected static void addNewBooktoAuthor(Scanner scanner, Librarian librarian /*String name,String address, int phoneNo*/) {
        ArrayList<Author> allAuthors = listAllAuthors(librarian);
        if (!allAuthors.isEmpty()) {
            int selectedAuthorIndex = -1;
            Author selectedAuthor ;
            System.out.println("Select Author By their number");
            do {
                try {
                    selectedAuthorIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Invalid input, select again");
                    continue;
                }

                try {
                    selectedAuthor = allAuthors.get(selectedAuthorIndex);
                    ValidationUtil.requireNoNull(selectedAuthor, "Selected Author can not be null");

                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    System.out.println("Invalid input, select again");
                    continue;
                }
                Optional<Book> newBook= MainBookOperations.createBook(scanner,selectedAuthor);
                if (newBook.isPresent()){
                    try {
                        selectedAuthor.newBook(newBook.get());
                        break;
                    } catch (Exception exception) {
                        System.out.println("Kitap eklenemedi" + exception);
                    }
                }else {
                    System.out.println("Kitap eklenemedi");
                    break;
                }

            } while (selectedAuthorIndex != 0);
        }
    }
    protected static ArrayList<Book> listAuthorsBooks(Librarian librarian,Author author){
        System.out.println("--------------");
        ArrayList<Book> allBooks = new ArrayList<>(author.getBooks().values());
        for (Book book : allBooks) {
            System.out.println(allBooks.indexOf(book) + "-) " + book);
        }
        if (allBooks.isEmpty()) System.out.println("There is no Book");
        return allBooks;
    }

    protected static Optional<Author> selectAuthor(Scanner scanner,Librarian librarian){
        ArrayList<Author> allAuthors = MainAuthorOperations.listAllAuthorsWithBooks(librarian);
        if (!allAuthors.isEmpty()) {
            int selectedAuthorIndex = -1;
            Author selectedAuthor;
            System.out.println("Select Author By their number");
            do {
                try {
                    selectedAuthorIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Invalid input, select again : " + numberFormatException);
                    continue;
                }

                try {
                    selectedAuthor = allAuthors.get(selectedAuthorIndex);
                    ValidationUtil.requireNoNull(selectedAuthor, "Selected Author can not be null");

                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    System.out.println("Invalid input, select again : " + indexOutOfBoundsException);
                    continue;
                }

                return Optional.of(selectedAuthor);

            } while (selectedAuthorIndex != 0);
        }
        return Optional.empty();
    }



}

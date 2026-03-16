package entity.main;

import entity.Book;
import entity.Reader;
import entity.concrete.*;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.utils.ValidationUtil;

import javax.swing.text.html.parser.Entity;
import java.util.*;

 abstract class MainBookOperations {
    private MainBookOperations() {
    }


    protected static void bookOperations(Scanner scanner, Librarian librarian) {
        String selectedOptionBook;
        do {

            System.out.println("--------------------");
            System.out.println("0-Return Main Menu");
            System.out.println("1-List Books");
            System.out.println("2-Add Book");
            //System.out.println("3-Change Verification");

            selectedOptionBook = scanner.nextLine();
            switch (selectedOptionBook) {
                case "0":
                    System.out.println("Returning to main menu");
                    break;
                case "1":
                    listBooksofLibrary(librarian);
                    break;
                case "2":
                    //   addReadertoLibrary(scanner, librarian);
                    break;
                case "3":
                    //   changeVerificationofReader(scanner, librarian);
                    break;
                default:
                    System.out.println("Invalid input, select again");
            }
        } while (!selectedOptionBook.equals("0"));

    }

    protected static ArrayList<Map.Entry<UUID, Book>> listBooksofLibrary(Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.getLibrary().getBooks().entrySet());
        for (Map.Entry<UUID, Book> entry : allEntries) {
            System.out.println(allEntries.indexOf(entry) + "-)  "+entry.getKey() +"  " + entry.getValue());
        }
        if (allEntries.isEmpty()) System.out.println("There is no Book");
        return allEntries;
    }


     protected static <T> ArrayList<Map.Entry<UUID, Book>> listBooksofLibrary(Librarian librarian,Class<T> searchedCategory) {
         System.out.println("--------------");
         ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.getLibrary().getBooks(searchedCategory).entrySet());
         for (Map.Entry<UUID, Book> entry : allEntries) {
             System.out.println(allEntries.indexOf(entry) + "-)  "+entry.getKey() +"  " + entry.getValue());
         }
         if (allEntries.isEmpty()) System.out.println("There is no Book");
         return allEntries;
     }

     protected static ArrayList<Map.Entry<UUID, Book>> listBooksofLibrary(Librarian librarian,String bookName) {
         System.out.println("--------------");
         ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.getLibrary().getBooks().entrySet());
         for (Map.Entry<UUID, Book> entry : allEntries) {
             System.out.println(allEntries.indexOf(entry) + "-)  "+entry.getKey() +"  " + entry.getValue());
         }
         if (allEntries.isEmpty()) System.out.println("There is no Book");
         return allEntries;
     }


    protected static Optional<Book> createBook(Scanner scanner, Author selectedAuthor) {

            System.out.println("--------------");
            System.out.println("Enter Name");
            String inputBookName = scanner.nextLine();

            try {

                ValidationUtil.requireUnBlankString(inputBookName,"New Book name can not be empty");

            }catch (IllegalArgumentException illegalArgumentException){
                return Optional.empty();
            }



            float inputPrice;

            System.out.println("Enter Price");
            try {
                inputPrice = Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Invalid input for price, select again");
                return Optional.empty();
            }

            System.out.println("Select BookStatus");
            System.out.println("----------------");

            System.out.println("1-) " + "AVAILABLE");
            System.out.println("2-) " + "INPROCESS");
            String selectedBookStatus = scanner.nextLine();
            BookStatus bookStatus;

            switch (selectedBookStatus) {
                case "1":
                    bookStatus = BookStatus.AVAILABLE;
                    break;
                case "2":
                    bookStatus = BookStatus.INPROCESS;
                    break;
                default:
                    System.out.println("Invalid input for bookstatus");
                    return Optional.empty();
            }


            System.out.println("Select BookEdition");
            System.out.println("----------------");
            System.out.println("1-) " + "LIMITED");
            System.out.println("2-) " + "FIRSTEDITION");
            System.out.println("3-) " + "HARDCOVER");
            System.out.println("4-) " + "REVISEDEDITION");
            System.out.println("5-) " + "PAPERBACK");
            String selectedBookEdition = scanner.nextLine();
            BookEdition bookEdition;
            switch (selectedBookEdition) {
                case "1":
                    bookEdition = BookEdition.LIMITED;
                    break;
                case "2":
                    bookEdition = BookEdition.FIRSTEDITION;
                    break;
                case "3":
                    bookEdition = BookEdition.HARDCOVER;
                    break;
                case "4":
                    bookEdition = BookEdition.REVISEDEDITION;
                    break;
                case "5":
                    bookEdition = BookEdition.PAPERBACK;
                    break;
                default:
                    System.out.println("Invalid input for bookstatus");
                    return Optional.empty();
            }
            Book newBook = null;
            System.out.println("Select Book Type");
            System.out.println("----------------");
            System.out.println("1-) " + "Journal");
            System.out.println("2-) " + "Study Book");
            System.out.println("3-) " + "Magazine");
            String selectedBookType = scanner.nextLine();
            switch (selectedBookType) {
                case "1":
                    newBook = new Journal(selectedAuthor, inputBookName, inputPrice, bookStatus, bookEdition, new Date(), selectedAuthor);
                    break;
                case "2":
                    newBook = new StudyBook(selectedAuthor, inputBookName, inputPrice, bookStatus, bookEdition, new Date(), selectedAuthor);
                    break;
                case "3":
                    newBook = new Magazine(selectedAuthor, inputBookName, inputPrice, bookStatus, bookEdition, new Date(), selectedAuthor);
                    break;
                default:
                    return Optional.empty();
            }
            return Optional.of(newBook);

    }

     protected static Optional<Book> selectBook(Librarian librarian ,Scanner scanner,Author selectedAuthor){
         System.out.println("Select Book By their number");
         ArrayList<Book> selectedAuthorsBooks = MainAuthorOperations.listAuthorsBooks(librarian, selectedAuthor);
         if(!selectedAuthorsBooks.isEmpty()){
             int selectedBookindex = -1;
             Book selectedBook = null;

             try {
                 selectedBookindex = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException numberFormatException) {
                 System.out.println("Invalid input");
                 return Optional.empty();
             }

             try {
                 selectedBook = selectedAuthorsBooks.get(selectedBookindex);
                 ValidationUtil.requireNoNull(selectedBook, "Selected Book can not be null");

             } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                 System.out.println("Invalid input, select again" + indexOutOfBoundsException.getMessage());
                 return Optional.empty();
             }
             return Optional.of(selectedBook);
         }
         return Optional.empty();
     }
}

package entity.main;
import entity.Book;
import entity.Reader;
import entity.concrete.*;
import entity.enums.BookEdition;
import entity.enums.BookStatus;

import java.util.*;

public class LibraryApplication {
    public static void main(String[] args) {
        Librarian librarian1 = new Librarian("Hamza", "123");
        Library library = Library.getInstance();
        Author author1= new Author("au 1","au 1 address","5312275594");
        Author author2= new Author("au 2","au 2 address","5312275594");
        library.addAuthor(author1);
        library.addAuthor(author2);

        Book book1 = new StudyBook(author1,"book1",5, BookStatus.AVAILABLE, BookEdition.PAPERBACK,new Date(),library);
        Book book2 = new Journal(author1,"book2",5, BookStatus.AVAILABLE, BookEdition.PAPERBACK,new Date(),library);
        author1.newBook(book1);
        author1.newBook(book2);
        library.addBook(new StudyBook(book1));
        library.addBook(new Journal(book2));
        library.addBook(new Journal(book2));

        Reader reader1= new Student("reader1","abc","5312275594");
        library.addReader(reader1);




        Scanner scanner = new Scanner(System.in);

        if (true/*librarian1.getName().equals(userName) && librarian1.getPassword().equals(password)*/) {

            String selectedOption;
            do {
                System.out.println("0-Exit");
                System.out.println("1-Author Operations");
                System.out.println("2-Reader Operations");
                System.out.println("3-Library Operations");
                System.out.println("4-Book Operations");
               /* System.out.println("5-Create Bill");
                System.out.println("6-Return Book");*/
                System.out.println("--------------------");

                System.out.println("Enter number for options");
                selectedOption = scanner.nextLine();
                switch (selectedOption) {
                    case "0":
                        System.out.println("selected 0");
                        System.out.println("Closing program");
                        break;
                    case "1":
                        MainAuthorOperations.authorOperations(scanner, librarian1);
                        break;
                    case "2":
                        MainReaderOperations.readerOperations(scanner, librarian1);
                        break;
                    case "3":
                        MainLibraryOperations.libraryOperations(scanner,librarian1);
                        break;
                    case "4":
                        MainBookOperations.bookOperations(scanner,librarian1);
                        break;
                    case "5":
                        System.out.println("selected 5");
                        break;
                    case "6":
                        System.out.println("selected 6");
                        break;
                    default:
                        System.out.println("Invalid input, select again");
                }


            } while (!selectedOption.equals("0"));

        }
    }

}
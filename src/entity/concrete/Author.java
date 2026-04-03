package entity.concrete;

import entity.Book;
import entity.Person;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;
import entity.interfaces.IAuthor;

import java.util.*;

public class Author extends Person implements IAuthor, Bookable {


    @Override
    public void newBook(Book book) {
        this.getBooks().values().stream()
                .filter(b -> (b.getName().equals(book.getName()) && b.getPrice() == book.getPrice() && b.getBookEdition().equals(book.getBookEdition())))
                .findFirst()
                .ifPresentOrElse(
                        existingBook -> System.out.println("Kitap zaten var: " + existingBook.getBook_ID()),
                        () -> {
                            this.addBook(book,book.getBook_ID());
                        }
                );

    }

    public Author(String name, String address, UUID personID, String phoneNo) {
        super(name, address, personID, phoneNo);
    }

    public Author(String name, String address, String phoneNo) {
        super(name, address, phoneNo);
    }

    @Override
    public String whoYouAre() {
        return this.getClass() + ": " + this.getName();
    }


    @Override
    public String toString() {
        return  super.toString() +
                        " Author { "
                        +
                        " books = \n " + this.getBooks() +
                        " } \n";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}

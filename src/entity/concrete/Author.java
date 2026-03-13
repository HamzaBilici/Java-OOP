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
    public void newBook(String name, float price, BookStatus bookStatus, BookEdition bookEdition) {

        newBook( name, price, bookStatus, bookEdition, new Date());

    }

    @Override
    public void newBook(String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date) {


        Book book = new Book(this, name, price, bookStatus, bookEdition, date, this);

        this.getBooks().values().stream()
                .filter(b -> (b.getName().equals(name) && b.getPrice() == price && b.getBookEdition().equals(bookEdition)))
                .findFirst()
                .ifPresentOrElse(
                        existingBook -> System.out.println("Kitap zaten var: " + existingBook.getBook_ID()),
                        () -> {
                            this.addBook(book);
                        }
                );



    }

    public Author(String name) {
        super(name);
    }

    @Override
    public String whoYouAre() {
        return this.getClass() + ": " + this.getName();
    }


    @Override
    public String toString() {
        return "Author{"
                +
                "books=" + this.getBooks() +
                '}';
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

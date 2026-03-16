package entity.concrete;

import entity.Book;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;

import java.util.Date;

public class Journal extends Book {
    public Journal(Author author, String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date_of_purchase, Bookable owner) {
        super(author, name, price, bookStatus, bookEdition, date_of_purchase, owner);
    }


    @Override
    public String toString() {
        return
                super.toString()
                        + "\n Journal { " +
                        " } \n";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Journal journal = (Journal) o;
        return this.getBook_ID().equals(journal.getBook_ID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

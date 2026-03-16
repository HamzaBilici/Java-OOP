package entity.concrete;

import entity.Book;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;

import java.util.Date;

public class Magazine extends Book {
    public Magazine(Author author, String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date_of_purchase, Bookable owner) {
        super(author, name, price, bookStatus, bookEdition, date_of_purchase, owner);
    }

    @Override
    public String toString() {
        return
                super.toString()
                        + "\n Magazine { " +
                        " } \n";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return this.getBook_ID().equals(magazine.getBook_ID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

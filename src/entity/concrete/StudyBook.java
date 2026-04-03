package entity.concrete;

import entity.Book;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;

import java.util.Date;
import java.util.UUID;

public class StudyBook extends Book {
    public StudyBook(Author author, String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date_of_purchase, Bookable owner) {
        super(author, name, price, bookStatus, bookEdition, date_of_purchase, owner);
    }
    public StudyBook(Author author, String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date_of_purchase, Bookable owner, UUID uuid) {
        super(author, name, price, bookStatus, bookEdition, date_of_purchase, owner,uuid);
    }

    public StudyBook(Book book) {
        super(book);
    }

    @Override
    public String toString() {
        return
                super.toString()
                        + "\n StudyBook { " +
                        " } \n";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        StudyBook studyBook = (StudyBook) o;
        return this.getBook_ID().equals(studyBook.getBook_ID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

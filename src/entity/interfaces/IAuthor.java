package entity.interfaces;

import entity.enums.BookEdition;
import entity.enums.BookStatus;

import java.util.Date;

public interface IAuthor {
    void newBook(String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date);

    void newBook(String name, float price, BookStatus bookStatus, BookEdition bookEdition);

}

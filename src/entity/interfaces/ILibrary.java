package entity.interfaces;

import entity.Book;
import entity.Reader;

import java.util.UUID;

public interface ILibrary {
    Reader getMember(UUID uuid);
    void newBook(Book book);
    void lendBook(Book book);
    void takeBackBook(Book book);
    Book showBook(UUID uuid);
}

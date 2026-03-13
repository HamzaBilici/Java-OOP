package entity.interfaces;

import entity.Book;

import java.util.Map;
import java.util.UUID;

public interface Bookable {

    Map<UUID, Book> getBooks();

    void addBook(Book book);

    void removeBook(Book book);

    Book showBook(UUID uuid);
}

package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.interfaces.Bookable;
import entity.interfaces.ILibrary;
import entity.utils.ValidationUtil;

import java.util.*;

public class Library implements ILibrary, Bookable {

    private static final Map<UUID, Book> BOOKS = new HashMap<>();
    private static final Set<Reader> READERS = new HashSet<>();

    private Library() {
    }

    private static Library instance;

    public static Library getInstance() {
        if (instance == null)
            instance = new Library();
        return instance;
    }

    @Override
    public Reader getMember(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "Reader Id can not be null");
        Iterator<Reader> iterator = READERS.iterator();
        while (iterator.hasNext()) {
            Reader reader = iterator.next();
            if (reader.getPersonID().equals(uuid)) return reader;
        }
        throw new NoSuchElementException("Reader not found");
    }

    @Override
    public void newBook(Book book) {

    }

    @Override
    public void lendBook(Book book) {

    }

    @Override
    public void takeBackBook(Book book) {

    }

    @Override
    public Book showBook(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "ID can not be null while getting book");
        return BOOKS.get(uuid);
    }

    @Override
    public Map<UUID, Book> getBooks() {
        return Collections.unmodifiableMap(BOOKS);
    }

    @Override
    public void addBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while adding");
        BOOKS.put(book.getBook_ID(), book);
    }

    @Override
    public void removeBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while removing");
        BOOKS.remove(book.getBook_ID());
    }
}

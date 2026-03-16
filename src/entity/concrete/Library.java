package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.interfaces.Bookable;
import entity.interfaces.ILibrary;
import entity.utils.ValidationUtil;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Library implements ILibrary, Bookable {

    private static final Map<UUID, Book> BOOKS = new HashMap<>();
    private static final Set<Reader> READERS = new HashSet<>();
    private static final Set<Author> AUTHORS = new HashSet<>();
    private static final Set<MemberRecord> MEMBER_RECORDS = new HashSet<>();


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

    public <T> Map<UUID, Book> getBooks(Class<T> searchedCategory) {
        return BOOKS.entrySet()
                .stream()
                .filter(entry -> searchedCategory.isInstance(entry.getValue()))
                .collect(Collectors.toUnmodifiableMap(
                                Entry::getKey,
                                Entry::getValue
                        )
                );
    }

    public Map<UUID, Book> getBooks(String bookName) {

        return BOOKS.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getName().equals(bookName) || entry.getValue().getName().contains(bookName) || bookName.contains(entry.getValue().getName()))
                .collect(Collectors.toUnmodifiableMap(
                                Entry::getKey,
                                Entry::getValue
                        )
                );
    }

    public Map<UUID, Book> getBooks(UUID bookUUID) {

        return BOOKS.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getBook_ID().equals(bookUUID) || entry.getKey().equals(bookUUID))
                .collect(Collectors.toUnmodifiableMap(
                                Entry::getKey,
                                Entry::getValue
                        )
                );
    }

    public Map<UUID, Book> getBooks(Book book) {

        return BOOKS.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(book))
                .collect(Collectors.toUnmodifiableMap(
                                Entry::getKey,
                                Entry::getValue
                        )
                );
    }


    @Override
    public void addBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while adding");
        BOOKS.put(UUID.randomUUID(), book);
    }

    @Override
    public void removeBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while removing");
        BOOKS.remove(book.getBook_ID());
    }

    public Set<Reader> getReaders() {
        return Collections.unmodifiableSet(READERS);
    }

    public void addReader(Reader reader) {
        ValidationUtil.requireNoNull(reader, "Reader can not be null while adding");
        READERS.add(reader);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(AUTHORS);
    }

    public void addAuthor(Author author) {
        ValidationUtil.requireNoNull(author, "Author can not be null while adding");
        AUTHORS.add(author);
    }

    public void removeAuthor(Author author) {
        ValidationUtil.requireNoNull(author, "Author can not be null while adding");
        AUTHORS.remove(author);
    }

    @Override
    public String toString() {
        return "Library";
    }
}

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
    private static Library instance;

    private Library() {
    }



    public static Library getInstance() {
        if (instance == null)
            instance = new Library();
        return instance;
    }

    @Override
    public Optional<Reader> getMember(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "Reader Id can not be null");
        return READERS.stream().filter(reader -> reader.getPersonID().equals(uuid)).findFirst();
    }


    @Override
    public void lendBook(UUID uuid, Reader reader) {
        reader.addBook(getBook(uuid),uuid);
        changeBookOwner(this,reader,getBook(uuid));
    }

    @Override
    public void takeBackBook(UUID uuid, Reader reader) {
        reader.removeBook(uuid);
        changeBookOwner(reader,this,getBook(uuid));
    }

    @Override
    public void changeBookOwner(Bookable currentOwner,Bookable targetOwner,Book book){
        System.out.println("Book given from "+currentOwner);
        System.out.println("To "+targetOwner);
        book.setOwner(targetOwner);
    }



    @Override
    public Map<UUID, Book> getBooks() {
        return Collections.unmodifiableMap(BOOKS);
    }
    public Set<MemberRecord> getMemberRecords() {
        return Collections.unmodifiableSet(MEMBER_RECORDS);
    }

    public  Book getBook(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "Book can not be null while adding");
        return BOOKS.get(uuid);
    }



    @Override
    public void addBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while adding");
        addBook(book, UUID.randomUUID());
    }

    public void addBook(Book book, UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "Book UUID can not be null while adding");
        book.setOwner(this);
        BOOKS.put(uuid, book);
    }

    @Override
    public void removeBook(UUID bookUUID) {

        ValidationUtil.requireNoNull(bookUUID, "Book UUID can not be null while removing");
        BOOKS.remove(bookUUID);
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

    public void addMemberRecord(MemberRecord memberRecord) {
        ValidationUtil.requireNoNull(memberRecord, "MemberRecord can not be null while adding");
        MEMBER_RECORDS.add(memberRecord);
    }

    @Override
    public String toString() {
        return "Library";
    }
}

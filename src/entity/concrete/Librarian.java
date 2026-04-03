package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.enums.BookStatus;
import entity.enums.MemberRecordStatus;
import entity.interfaces.ILibrarian;
import entity.utils.ValidationUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Librarian implements ILibrarian {
    private String name, password;
    private final Library library = Library.getInstance();

    public Librarian(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }

    public Library getLibrary() {
        return library;
    }

    @Override
    public Book searchBook(UUID uuid) {
        return library.getBook(uuid);
    }

    public <T> Map<UUID, Book> searchBooks(Class<T> searchedCategory) {
        return library.getBooks().entrySet()
                .stream()
                .filter(entry -> searchedCategory.isInstance(entry.getValue()))
                .collect(Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }


    public Map<UUID, Book> searchBooks(String bookName) {

        return library.getBooks().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getName().equals(bookName) || entry.getValue().getName().contains(bookName) || bookName.contains(entry.getValue().getName()))
                .collect(Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }

    public Map<UUID, Book> searchBooks(UUID bookUUID) {

        return library.getBooks().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getBook_ID().equals(bookUUID) || entry.getKey().equals(bookUUID))
                .collect(Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }


    public Map<UUID, Book> searchBooks(Book book) {

        return library.getBooks().entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(book))
                .collect(Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }

    public Optional<List<MemberRecord>> searchMemberRecordofReader(Reader reader){
       return Optional.of(library.getMemberRecords().stream().filter(memberRecord -> memberRecord.getCurrentOwner().equals(reader)).toList());
    }


    @Override
    public void verifyMember(Reader reader) {
        verifyMember(reader,true);
    }

    public void verifyMember(Reader reader, boolean verificationState) {
        library.getMember(reader.getPersonID()).ifPresent(verifyMember ->verifyMember.setVerified(verificationState) );
    }

    @Override
    public Optional<MemberRecord> issueBook(UUID uuid, Reader reader) {
        ValidationUtil.requireNoNull(uuid, "Returned book id can not be null");
        ValidationUtil.requireNoNull(reader, "Reader can not be null");
        if(reader.getBooks().size()>=5){
            /*  System.out.println(getReadersRecord(reader).stream().filter(memberRecord -> memberRecord.getMemberRecordStatus().equals(MemberRecordStatus.WAITING)).toList());
             */
            System.out.println("Reader can not take more than 5 books");
            return Optional.empty();
        }

         System.out.println("-----------------------------");


        library.lendBook(uuid, reader);

        searchBook(uuid).setBookStatus(BookStatus.CHECKEDOUT);

       return Optional.of(createBill(uuid));
    }




    @Override
    public void calculateFine(MemberRecord memberRecord) {
        ValidationUtil.requireNoNull(memberRecord, "MemberRecord can not be null");
        if(memberRecord.getRelatedBook().isPresent()){
            memberRecord.setPaidPrice(memberRecord.getRelatedBook().get().getPrice());
        }

    }

    @Override
    public MemberRecord createBill(UUID bookID) {
        ValidationUtil.requireNoNull(bookID, "releated book ID can not be null");
        MemberRecord memberRecord = new MemberRecord(bookID);
        calculateFine(memberRecord);
        library.addMemberRecord(memberRecord);
        return memberRecord;
    }

    @Override
    public void returnBook(MemberRecord memberRecord, Reader reader) {
        ValidationUtil.requireNoNull(memberRecord, "MemberRecord can not be null");
        ValidationUtil.requireNoNull(reader, "Reader can not be null");

        Book takenBackBook=library.getBook(memberRecord.getLibraryBookID());
        System.out.println("paid back "+ memberRecord.payBill()+ " $");

        library.takeBackBook(memberRecord.getLibraryBookID(), reader);
        takenBackBook.setBookStatus(BookStatus.AVAILABLE);
    }

    public List<MemberRecord> getReadersRecord(Reader reader){
       return library.getMemberRecords().stream().filter(memberRecord -> reader.getBooks().containsKey(memberRecord.getLibraryBookID())).toList();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        ValidationUtil.requireNoNull(name, "Name can not be null");
        ValidationUtil.requireUnBlankString(name, "Name can not be blank");
        this.name = name;
    }

    public void setPassword(String password) {
        ValidationUtil.requireNoNull(name, "Password can not be null");
        ValidationUtil.requireUnBlankString(name, "Password can not be blank");
        this.password = password;
    }

    @Override
    public String toString() {
        return " Librarian { " +
                " name = " + name +
                " } ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        ValidationUtil.requireNoNull(o, "Librarian Can not be null");
        ValidationUtil.requireIsInstanceOf(o, Librarian.class, "Compared objects has to be instance of Librarian");
        Librarian librarian = (Librarian) o;
        return Objects.equals(name, librarian.name) && Objects.equals(password, librarian.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}

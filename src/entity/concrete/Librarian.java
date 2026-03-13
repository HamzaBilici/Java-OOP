package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.interfaces.ILibrarian;
import entity.utils.ValidationUtil;

import java.util.List;
import java.util.UUID;

public class Librarian implements ILibrarian {
    private String name, password;
    Library library = Library.getInstance();

    @Override
    public Book searchBook(UUID uuid) {
        return library.showBook(uuid);
    }

    @Override
    public void verifyMember(Reader reader) {
        library.getMember(reader.getPersonID()).setVerified(true);
    }

    public void verifyMember(Reader reader, boolean verificationState) {
        library.getMember(reader.getPersonID()).setVerified(verificationState);
    }

    @Override
    public void issueBook(Reader reader) {

    }

    @Override
    public void calculateFine(Reader reader) {

    }

    @Override
    public void createBill(Reader reader) {

    }

    @Override
    public void returnBook(Book book) {
        ValidationUtil.requireNoNull(book, "Returned book can not be null");
        library.takeBackBook(book);
    }

    @Override
    public void returnBook(List<Book> books) {
        for (Book book : books) {
            returnBook(book);
        }
    }


}

package entity.interfaces;

import entity.Book;
import entity.Reader;

import java.util.List;
import java.util.UUID;

public interface ILibrarian {
    public Book searchBook(UUID uuid);
    public void verifyMember(Reader reader);
    public void issueBook(Reader reader);
    public void calculateFine(Reader reader);
    public void createBill(Reader reader);
    public void returnBook(Book book);
    public void returnBook(List<Book> books);
}

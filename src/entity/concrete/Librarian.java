package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.interfaces.ILibrarian;
import entity.utils.ValidationUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Librarian implements ILibrarian {
    private String name, password;
    Library library = Library.getInstance();

    public Librarian(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }

    public Library getLibrary() {
        return library;
    }

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


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        ValidationUtil.requireNoNull(name,"Name can not be null");
        ValidationUtil.requireUnBlankString(name,"Name can not be blank");
        this.name = name;
    }

    public void setPassword(String password) {
        ValidationUtil.requireNoNull(name,"Password can not be null");
        ValidationUtil.requireUnBlankString(name,"Password can not be blank");
        this.password = password;
    }

    @Override
    public String toString() {
        return " Librarian { " +
                " name = " + name  +
                " } ";
    }

    @Override
    public boolean equals(Object o) {
        if (o== this) return true;
        ValidationUtil.requireNoNull(o,"Librarian Can not be null");
        ValidationUtil.requireIsInstanceOf(o,Librarian.class,"Compared objects has to be instance of Librarian");
        Librarian librarian = (Librarian) o;
        return Objects.equals(name, librarian.name) && Objects.equals(password, librarian.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}

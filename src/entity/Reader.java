package entity;

import entity.interfaces.IReader;
import entity.utils.ValidationUtil;

public abstract class Reader extends Person implements IReader {



    private boolean isVerified;


    public Reader(String name) {
        super(name);
        this.isVerified = false;
    }

    public Reader(String name, boolean isVerified) {
        super(name);
        this.isVerified = isVerified;
    }

    @Override
    public String whoYouAre() {
        return this.getClass()+": "+ this.getName();
    }



    @Override
    public void purchaseBook(Book book) {

        this.addBook(book);
    }

    @Override
    public void borrowBook(Book book) {
        ValidationUtil.requireNoNull(book, "Book can not be null while borrowing");

    }

    @Override
    public void returnBook(Book book) {
        ValidationUtil.requireNoNull(book, "Book can not be null while returning");
        this.removeBook(book);
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }


    @Override
    public String toString() {
        return "Reader{" +
                super.toString()
                +
                "books=" + getBooks() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Reader reader = (Reader) o;
        return  this.getPersonID().equals(reader.getPersonID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

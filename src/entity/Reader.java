package entity;

import entity.interfaces.IReader;
import entity.utils.ValidationUtil;

import java.util.Date;
import java.util.UUID;

public abstract class Reader extends Person implements IReader {


    private boolean isVerified;
    private Date dateOfMembership;


    public Reader(String name, String address, String phoneNo) {
        this(name, address, phoneNo, false);
    }

    public Reader(String name, String address, String phoneNo, boolean isVerified) {
        super(name, address, phoneNo);
        this.setVerified(isVerified);
        this.setDateOfMembership(new Date());
    }


    public Reader(String name, String address, UUID personID, String phoneNo) {
        this(name, address, personID, phoneNo, false);
    }

    public Reader(String name, String address, UUID personID, String phoneNo, boolean isVerified) {
        super(name, address, personID, phoneNo);
        this.setVerified(isVerified);
        this.setDateOfMembership(new Date());
    }

    @Override
    public String whoYouAre() {
        return this.getClass() + ": " + this.getName();
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
       /* ValidationUtil.requireNoNull(book, "Book can not be null while returning");
        this.removeBook(book);*/
        //TODO
    }

    public boolean isVerified() {
        return isVerified;
    }
    public Date getDateOfMembership(){
        return dateOfMembership;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    public void setDateOfMembership(Date dateOfMembership) {
        ValidationUtil.requireNoNull(dateOfMembership,"Membership Date Can not be Null");
        this.dateOfMembership=dateOfMembership;
    }


    @Override
    public String toString() {
        return super.toString()
                +" Reader { " +
                " isValid = "+isVerified()+
                " books = \n" + getBooks() +
                 " } " ;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Reader reader = (Reader) o;
        return this.getPersonID().equals(reader.getPersonID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

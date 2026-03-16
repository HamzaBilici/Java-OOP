package entity;

import entity.concrete.Author;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;
import entity.interfaces.IBook;
import entity.utils.ValidationUtil;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public abstract class Book implements IBook, Comparable<Book> {
    private UUID book_ID;
    private Author author;
    private String name;
    private float price;
    private BookStatus bookStatus;
    private BookEdition bookEdition;
    private Date dateOfPurchase;
    private Bookable owner;

    public Book(Author author, String name, float price, BookStatus bookStatus, BookEdition bookEdition, Date date_of_purchase, Bookable owner) {
        this.setBook_ID(UUID.randomUUID());
        this.setAuthor(author);
        this.setName(name);
        this.setPrice(price);
        this.setBookStatus(bookStatus);
        this.setBookEdition(bookEdition);
        this.setDateOfPurchase(date_of_purchase);
        this.setOwner(owner);
    }

    public UUID getBook_ID() {
        return book_ID;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public BookEdition getBookEdition() {
        return bookEdition;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }


    private void setBook_ID(UUID book_ID) {
        ValidationUtil.requireNoNull(book_ID, "ID cannot be null");
        this.book_ID = book_ID;
    }

    public void setAuthor(Author author) {
        ValidationUtil.requireNoNull(author, "Author cannot be null");
        this.author = author;
    }

    public void setOwner(Bookable owner) {
        ValidationUtil.requireNoNull(author, "Owner cannot be null");
        this.owner = owner;
    }

    public void setName(String name) {
        ValidationUtil.requireNoNull(name, "Name cannot be null");
        ValidationUtil.requireUnBlankString(name, "Name cannot be blank");
        this.name = name;
    }

    public void setPrice(float price) {
        ValidationUtil.requirePossitive((long) price, "Price cannot be lower than 0");
        this.price = price;
    }

    public void setBookStatus(BookStatus bookStatus) {
        ValidationUtil.requireNoNull(bookStatus, "BookStatus cannot be null");
        this.bookStatus = bookStatus;
    }

    public void setBookEdition(BookEdition bookEdition) {
        ValidationUtil.requireNoNull(bookEdition, "BookEdition cannot be null");
        this.bookEdition = bookEdition;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        ValidationUtil.requireNoNull(dateOfPurchase, "date_of_purchase cannot be null");
        this.dateOfPurchase = dateOfPurchase;
    }


    @Override
    public String getTitle() {
        return this.getName();
    }

    @Override
    public Author getAuthor() {
        return this.author;
    }

    @Override
    public void changeOwner(Bookable newOwner, Bookable oldOwner) {
        oldOwner.removeBook(this);
        newOwner.addBook(this);

    }

    @Override
    public Bookable getOwner() {
        return this.owner;
    }

    @Override
    public String display() {
        return this.toString();
    }

    @Override
    public void updateStatus(BookStatus bookStatus) {

        this.bookStatus = bookStatus;
    }

    @Override
    public String toString() {
        return "Book { "+" UUID = "+book_ID + " name = " + name + " + status = "+ bookStatus+ " } ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        ValidationUtil.requireNoNull(o, "Compared object cannot be null");
        ValidationUtil.requireIsInstanceOf(o, Book.class, "Compared objects has to be instance of Book");

        Book book = (Book) o;
        return book_ID.equals(book.getBook_ID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(book_ID);
    }


    @Override
    public int compareTo(Book o) {
        ValidationUtil.requireNoNull(o, "Compared object cannot be null");
        return this.getBook_ID().compareTo(o.getBook_ID());
    }
}

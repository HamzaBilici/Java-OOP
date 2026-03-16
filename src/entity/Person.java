package entity;

import entity.interfaces.Bookable;
import entity.utils.ValidationUtil;

import java.util.*;

public abstract class Person implements Bookable, Comparable<Person> {
    private String name, address, phoneNo;
    private UUID personID;
    private final Map<UUID, Book> books = new HashMap<>();


    public Person(String name, String address, UUID personID, String phoneNo) {
        this.setName(name);
        this.setPersonID(personID);
        this.setAddress(address);
        this.setPhoneNo(phoneNo);
    }


    public Person(String name, String address, String phoneNo) {
        this.setName(name);
        this.setPersonID(UUID.randomUUID());
        this.setAddress(address);
        this.setPhoneNo(phoneNo);
    }

    public abstract String whoYouAre();

    @Override
    public Book showBook(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "ID can not be null while getting book");
        return books.get(uuid);
    }

    @Override
    public Map<UUID, Book> getBooks() {
        return Collections.unmodifiableMap(books);
    }

    @Override
    public void addBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while adding");
        books.put(UUID.randomUUID(), book);
    }


    @Override
    public void removeBook(Book book) {

        ValidationUtil.requireNoNull(book, "Book can not be null while removing");
        books.entrySet().removeIf(entry -> entry.getValue() == book);

    }

    public void removeBook(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "UUID can not be null while removing");
        books.remove(uuid);

    }

    protected String getName() {
        return name;
    }

    public UUID getPersonID() {
        return personID;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    private void setName(String name) {

        ValidationUtil.requireNoNull(name, "Name cannot be null");
        ValidationUtil.requireUnBlankString(name, "Name cannot be blank");
        this.name = name;
        this.personID = UUID.randomUUID();
    }

    public void setAddress(String address) {
        ValidationUtil.requireNoNull(address, "Address can not be null");
        ValidationUtil.requireUnBlankString(address, "Address can not be blank");
        this.address = address;
    }

    public void setPhoneNo(String phoneNo) {
        ValidationUtil.requireUnBlankString(phoneNo, "Phone no can not be blank");
        ValidationUtil.requireLengthofCharacter(phoneNo, 10, "Phone length does not match");
        this.phoneNo = phoneNo;
    }

    private void setPersonID(UUID personID) {
        ValidationUtil.requireNoNull(personID, "PersonID cannot be null");
        this.personID = personID;
    }

    @Override
    public String toString() {
        return " Person { " +
                " name = " + name +
                " } ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        ValidationUtil.requireNoNull(o, "Compared object cannot be null");
        ValidationUtil.requireIsInstanceOf(o, Person.class, "Compared objects has to be instance of Person");
        Person person = (Person) o;
        return Objects.equals(personID, person.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(personID);
    }

    @Override
    public int compareTo(Person person) {
        ValidationUtil.requireNoNull(person, "Compared person cannot be null");
        return this.getPersonID().compareTo(person.getPersonID());
    }
}

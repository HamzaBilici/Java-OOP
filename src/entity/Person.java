package entity;

import entity.interfaces.Bookable;
import entity.utils.ValidationUtil;

import java.util.*;

public abstract class Person implements Bookable, Comparable<Person> {
    private String name;
    private UUID personID;
    private final Map<UUID, Book> books = new HashMap<>();

    public Person(String name) {
        this.setName(name);
        this.personID = UUID.randomUUID();
    }

    public abstract String whoYouAre();

    @Override
    public Book showBook(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "ID can not be null while getting book");
        return books.get(uuid);
    }

    /*  public Book showBook(Integer index) {

         // return books.get(uuid);
      }
  */
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

    private void setName(String name) {

        ValidationUtil.requireNoNull(name, "Name cannot be null");
        ValidationUtil.requireUnBlankString(name, "Name cannot be blank");
        this.name = name;
        this.personID = UUID.randomUUID();
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        ValidationUtil.requireNoNull(o, "Compared object cannot be null");
        ValidationUtil.requireSameTypeOfObject(o, this, "Compared objects has to have same classes");
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

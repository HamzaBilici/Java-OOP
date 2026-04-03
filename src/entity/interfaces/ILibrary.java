package entity.interfaces;

import entity.Book;
import entity.Reader;

import java.util.Optional;
import java.util.UUID;

public interface ILibrary {
    Optional<Reader> getMember(UUID uuid);
    void lendBook(UUID uuid,Reader reader);
    void takeBackBook(UUID uuid,Reader reader);
    void changeBookOwner(Bookable currentOwner,Bookable targetOwner,Book book);
}

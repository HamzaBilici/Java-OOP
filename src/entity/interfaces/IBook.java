package entity.interfaces;

import entity.concrete.Author;
import entity.enums.BookStatus;

public interface IBook {
    String getTitle();

    Author getAuthor();


    Bookable getOwner();

    String display();

    void updateStatus(BookStatus bookStatus);
}

package entity.interfaces;

import entity.Book;

public interface IReader {
     void purchaseBook(Book book);
     void borrowBook(Book book);
     void returnBook(Book book);
}

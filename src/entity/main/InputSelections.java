package entity.main;

import entity.Book;
import entity.Reader;
import entity.concrete.Author;
import entity.concrete.Journal;
import entity.concrete.Magazine;
import entity.concrete.StudyBook;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;
import entity.utils.ValidationUtil;

import java.util.*;

public abstract class InputSelections {
    private InputSelections(){}

    protected static Optional<String> getInputName(Scanner scanner) {
        System.out.println("Enter Name");
        String inputBookName = scanner.nextLine();
        try {
            ValidationUtil.requireUnBlankString(inputBookName, "New Book name can not be empty");

        } catch (IllegalArgumentException illegalArgumentException) {
            return Optional.empty();
        }
        return Optional.of(inputBookName);
    }

    protected static Optional<Float> getInputPrice(Scanner scanner) {
        float inputPrice;

        System.out.println("Enter Price");
        try {
            inputPrice = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Invalid input for price, select again");
            return Optional.empty();
        }
        return Optional.of(inputPrice);
    }


    protected static Optional<BookStatus> getInputBookStatus(Scanner scanner) {
        System.out.println("Select BookStatus");
        System.out.println("----------------");

        System.out.println("1-) " + "AVAILABLE");
        System.out.println("2-) " + "INPROCESS");
        String selectedBookStatus = scanner.nextLine();
        BookStatus bookStatus;

        switch (selectedBookStatus) {
            case "1":
                bookStatus = BookStatus.AVAILABLE;
                break;
            case "2":
                bookStatus = BookStatus.INPROCESS;
                break;
            default:
                System.out.println("Invalid input for bookstatus");
                return Optional.empty();
        }
        return Optional.of(bookStatus);
    }



    protected static Optional<BookEdition> getInputBookEdition(Scanner scanner) {
        System.out.println("Select BookEdition");
        System.out.println("----------------");
        System.out.println("1-) " + "LIMITED");
        System.out.println("2-) " + "FIRSTEDITION");
        System.out.println("3-) " + "HARDCOVER");
        System.out.println("4-) " + "REVISEDEDITION");
        System.out.println("5-) " + "PAPERBACK");
        String selectedBookEdition = scanner.nextLine();
        BookEdition bookEdition;
        switch (selectedBookEdition) {
            case "1":
                bookEdition = BookEdition.LIMITED;
                break;
            case "2":
                bookEdition = BookEdition.FIRSTEDITION;
                break;
            case "3":
                bookEdition = BookEdition.HARDCOVER;
                break;
            case "4":
                bookEdition = BookEdition.REVISEDEDITION;
                break;
            case "5":
                bookEdition = BookEdition.PAPERBACK;
                break;
            default:
                System.out.println("Invalid input for bookstatus");
                return Optional.empty();
        }
        return Optional.of(bookEdition);
    }

    protected static Optional<Book> getInputCategoryCreateBook(Scanner scanner, Author selectedAuthor, String inputBookName, float inputPrice, BookStatus bookStatus, BookEdition bookEdition, Optional<UUID> uuid, Bookable owner) {

        Book newBook = null;
        System.out.println("Select Book Type");
        System.out.println("----------------");
        System.out.println("1-) " + "Journal");
        System.out.println("2-) " + "Study Book");
        System.out.println("3-) " + "Magazine");
        String selectedBookType = scanner.nextLine();

        switch (selectedBookType) {
            case "1":
                newBook = new Journal(selectedAuthor, inputBookName, inputPrice, bookStatus, bookEdition, new Date(), owner);
                break;
            case "2":
                newBook = new StudyBook(selectedAuthor, inputBookName, inputPrice, bookStatus, bookEdition, new Date(), owner);
                break;
            case "3":
                newBook = new Magazine(selectedAuthor, inputBookName, inputPrice, bookStatus, bookEdition, new Date(), owner);
                break;
            default:
                return Optional.empty();
        }

        if (uuid.isPresent()) newBook.setBook_ID(uuid.get());

        return Optional.of(newBook);
    }



    protected static Optional<Map.Entry<UUID,Book>> getInputBookSelection(Scanner scanner, List<Map.Entry<UUID, Book>> selectedBookList){
        selectedBookList.forEach(entry-> System.out.println(selectedBookList.indexOf(entry)+" -) "+ entry.getKey()+" , "+entry.getValue() ));

        Optional<Integer> selectedBookEntryindex = getInputIndex(scanner,"book");
        if (selectedBookEntryindex.isEmpty()){
            System.out.println("Invalid index");
            return Optional.empty();
        }

        Map.Entry<UUID,Book> selectedBookEntry = null;
        try {
            selectedBookEntry = selectedBookList.get(selectedBookEntryindex.get());
            ValidationUtil.requireNoNull(selectedBookEntry, "Selected Book can not be null");

        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid input, select again" + indexOutOfBoundsException.getMessage());
            return Optional.empty();
        }
        return Optional.of(selectedBookEntry);
    }


/*
    protected static Optional<Map.Entry<UUID,Book>> getInputBookSelection(Scanner scanner, List<Map.Entry<UUID, Book>> selectedBookList){
        selectedBookList.forEach(entry-> System.out.println(selectedBookList.indexOf(entry)+" -) "+ entry.getKey()+" , "+entry.getValue() ));
        System.out.println("Select book by index");
        Optional<Integer> selectedBookEntryindex = getInputIndex(scanner,"book");
        if (selectedBookEntryindex.isEmpty()){
            System.out.println("Invalid index");
            return Optional.empty();
        }

        Map.Entry<UUID,Book> selectedBookEntry = null;
        try {
            selectedBookEntry = selectedBookList.get(selectedBookEntryindex.get());
            ValidationUtil.requireNoNull(selectedBookEntry, "Selected Book can not be null");

        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid input, select again" + indexOutOfBoundsException.getMessage());
            return Optional.empty();
        }
        return Optional.of(selectedBookEntry);
    }*/


    protected static Optional<Integer> getInputIndex(Scanner scanner,String text){
        System.out.println("Select "+text+" by index");
        int selectedBookEntryindex = -1;


        try {
            selectedBookEntryindex = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Invalid input");
            return Optional.empty();
        }

        try {
            ValidationUtil.requirePossitive((long)selectedBookEntryindex+1,"index can not be lower than 0");
        }catch (IllegalArgumentException exception){
            System.out.println("Invalid input");
            return Optional.empty();
        }


        return Optional.of(selectedBookEntryindex);
    }

    protected static  Optional<Reader> getInputReaderSelection(Scanner scanner, List<Reader> readersList){
        System.out.println("Select reader by index");
        readersList.forEach(reader-> System.out.println(readersList.indexOf(reader)+" -) "+ reader));

        int selectedReaderindex = -1;
        Reader selectedReader = null;

        try {
            selectedReaderindex = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Invalid input");
            return Optional.empty();
        }

        try {
            selectedReader = readersList.get(selectedReaderindex);
            ValidationUtil.requireNoNull(selectedReader, "Selected Reader can not be null");

        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid input, select again" + indexOutOfBoundsException.getMessage());
            return Optional.empty();
        }

        return Optional.of(selectedReader);

    }

    protected static Optional<Boolean> getInputReaderVerification(Scanner scanner){
        System.out.println("Enter Reader New Verification");
        System.out.println("1-) Enable");
        System.out.println("2-) Disable");
        String selectedReaderVerification = scanner.nextLine();
        switch (selectedReaderVerification) {
            case "1":
                return Optional.of(true);
            case "2":
                return Optional.of(false);
            default:
                return Optional.empty();
        }
    }

}

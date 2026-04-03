package entity.main;

import entity.Book;
import entity.Reader;
import entity.concrete.*;
import entity.enums.BookEdition;
import entity.enums.BookStatus;
import entity.interfaces.Bookable;
import entity.utils.ValidationUtil;

import java.util.*;

abstract class MainBookOperations {
    private MainBookOperations() {
    }


    protected static void bookOperations(Scanner scanner, Librarian librarian) {
        String selectedOptionBook;
        do {

            System.out.println("--------------------");
            System.out.println("0-Return Main Menu");
            System.out.println("1-Rent Book");
            System.out.println("2-Get Back Book");
            //System.out.println("3-Change Verification");

            selectedOptionBook = scanner.nextLine();
            switch (selectedOptionBook) {
                case "0":
                    System.out.println("Returning to main menu");
                    break;
                case "1":
                    System.out.println(rentBook(scanner,librarian));
                    break;
                case "2":
                    takeBackBook(scanner,librarian);
                    break;
                case "3":
                    //   changeVerificationofReader(scanner, librarian);
                    break;
                default:
                    System.out.println("Invalid input, select again");
            }
        } while (!selectedOptionBook.equals("0"));

    }
    protected static Optional<MemberRecord> takeBackBook(Scanner scanner, Librarian librarian){
        List<Reader> readersList=librarian.getLibrary().getReaders().stream().filter(reader -> !reader.getBooks().isEmpty()).toList();
        if(readersList.isEmpty()){
            System.out.println("There is no readers with lended book");
            return Optional.empty();
        }

        Optional<Reader> selectedReader=InputSelections.getInputReaderSelection(scanner,readersList);
        if(selectedReader.isEmpty()){
            System.out.println("Invalid Reader");
            return Optional.empty();
        }

        Optional<List<MemberRecord>> selectedReaderRecords = librarian.searchMemberRecordofReader(selectedReader.get());
        if (selectedReaderRecords.isEmpty()){
            System.out.println("reader has no records");
            return Optional.empty();
        }


        System.out.println("--------------");
        for (MemberRecord memberRecord : selectedReaderRecords.get()) {
            System.out.println(selectedReaderRecords.get().indexOf(memberRecord) + "-) " + memberRecord);
        }
        Optional<Integer> selectedMemberRecordIndex=InputSelections.getInputIndex(scanner,"MemberRecord");
        if(selectedMemberRecordIndex.isEmpty()){
            System.out.println("invalid index");
            return Optional.empty();
        }
        System.out.println(selectedReaderRecords.get().get(selectedMemberRecordIndex.get()));

        librarian.returnBook(selectedReaderRecords.get().get(selectedMemberRecordIndex.get()),selectedReader.get());


        return Optional.empty();
    }

    protected static Optional<MemberRecord> rentBook(Scanner scanner, Librarian librarian) {
        Optional<List<Map.Entry<UUID, Book>>> selectedBookList = MainLibraryOperations.getBooksByBook(scanner, librarian);

        selectedBookList = selectedBookList.map(list -> list.stream()
                .filter(entry -> entry.getValue().getBookStatus().equals(BookStatus.AVAILABLE))
                .toList()
        );
        if(selectedBookList.isEmpty()){
            System.out.println("There is no copy of book in the library");
            return Optional.empty();
        }

        System.out.println("There is "+selectedBookList.get().size()+" copies of selected book in the library");

        Optional<Map.Entry<UUID,Book>> selectedBookEntry=InputSelections.getInputBookSelection(scanner,selectedBookList.get());
        if (selectedBookEntry.isEmpty()){
            System.out.println("Invalid book selection");
            return Optional.empty();
        }


        List<Reader> readersList=librarian.getLibrary().getReaders().stream().toList();
        if(readersList.isEmpty()){
            System.out.println("There is no readers");
            return Optional.empty();
        }

        Optional<Reader> selectedReader=InputSelections.getInputReaderSelection(scanner,readersList);
        if(selectedReader.isEmpty()){
            System.out.println("Invalid Reader");
            return Optional.empty();
        }

       return librarian.issueBook(selectedBookEntry.get().getKey(),selectedReader.get());

    }

    protected static ArrayList<Map.Entry<UUID, Book>> listBooksofLibrary(Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.getLibrary().getBooks().entrySet());
        allEntries.forEach(entry ->  System.out.println(allEntries.indexOf(entry) + "-)  " + entry.getKey() + "  " + entry.getValue()));
        if (allEntries.isEmpty()) System.out.println("There is no Book");
        return allEntries;
    }

    /*protected static ArrayList<Map.Entry<UUID, Book>> listAvailableBooksofLibrary(Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.getLibrary().getBooks().entrySet());
        for (Map.Entry<UUID, Book> entry : allEntries) {
            System.out.println(allEntries.indexOf(entry) + "-)  " + entry.getKey() + "  " + entry.getValue());
        }
        if (allEntries.isEmpty()) System.out.println("There is no Book");
        return allEntries;
    }*/


    protected static <T> ArrayList<Map.Entry<UUID, Book>> listBooksofLibrary(Librarian librarian, Class<T> searchedCategory) {
        System.out.println("--------------");
        ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.searchBooks(searchedCategory).entrySet());

        allEntries.forEach(entry -> System.out.println(allEntries.indexOf(entry) + "-)  " + entry.getKey() + "  " + entry.getValue()));
        if (allEntries.isEmpty()) System.out.println("There is no Book");
        return allEntries;
    }

    /*
        protected static ArrayList<Map.Entry<UUID, Book>> listBooksofLibrary(Librarian librarian, String bookName) {
            System.out.println("--------------");
            ArrayList<Map.Entry<UUID, Book>> allEntries = new ArrayList<>(librarian.getLibrary().getBooks().entrySet());
            for (Map.Entry<UUID, Book> entry : allEntries) {
                System.out.println(allEntries.indexOf(entry) + "-)  " + entry.getKey() + "  " + entry.getValue());
            }
            if (allEntries.isEmpty()) System.out.println("There is no Book");
            return allEntries;
        }
    */






    protected static Optional<Book> updateBook(Scanner scanner, Author selectedAuthor, Book book) {
        System.out.println("--------------");

        Optional<String> inputBookName = InputSelections.getInputName(scanner);
        if (inputBookName.isEmpty()) {
            System.out.println("invalid name");
            return Optional.empty();
        }

        Optional<Float> inputPrice = InputSelections.getInputPrice(scanner);
        if (inputPrice.isEmpty()) {
            System.out.println("invalid price");
            return Optional.empty();
        }

        Optional<BookEdition> bookEdition = InputSelections.getInputBookEdition(scanner);
        if (bookEdition.isEmpty()) {
            System.out.println("invalid book edition");
            return Optional.empty();
        }

        return InputSelections.getInputCategoryCreateBook(scanner, selectedAuthor, inputBookName.get(), inputPrice.get(), book.getBookStatus(), bookEdition.get(), Optional.ofNullable(book.getBook_ID()), book.getOwner());

    }


    protected static Optional<Book> createBook(Scanner scanner, Author selectedAuthor, Optional<UUID> uuid, Bookable owner) {
        System.out.println("--------------");

        Optional<String> inputBookName = InputSelections.getInputName(scanner);
        if (inputBookName.isEmpty()) {
            System.out.println("invalid name");
            return Optional.empty();
        }

        Optional<Float> inputPrice = InputSelections.getInputPrice(scanner);
        if (inputPrice.isEmpty()) {
            System.out.println("invalid price");
            return Optional.empty();
        }

        Optional<BookStatus> bookStatus = InputSelections.getInputBookStatus(scanner);
        if (bookStatus.isEmpty()) {
            System.out.println("invalid book status");
            return Optional.empty();
        }

        Optional<BookEdition> bookEdition = InputSelections.getInputBookEdition(scanner);
        if (bookEdition.isEmpty()) {
            System.out.println("invalid book edition");
            return Optional.empty();
        }

        return InputSelections.getInputCategoryCreateBook(scanner, selectedAuthor, inputBookName.get(), inputPrice.get(), bookStatus.get(), bookEdition.get(), uuid, owner);

    }

    protected static Optional<Book> selectOrDisplayBookFromAuthor(Librarian librarian, Scanner scanner, Author selectedAuthor, boolean option) {
        if (option) System.out.println("Select Book By their number");
        ArrayList<Book> selectedAuthorsBooks = MainAuthorOperations.listAuthorsBooks(librarian, selectedAuthor);
        if (selectedAuthorsBooks.isEmpty()){
            return Optional.empty();
        }
        if (!option) {
            return Optional.empty();
        }

            Optional<Integer> selectedBookindex = InputSelections.getInputIndex(scanner,"book");
        if (selectedBookindex.isEmpty()){
            System.out.println("Invalid input");
            return Optional.empty();
        }
            Book selectedBook = null;


            try {
                selectedBook = selectedAuthorsBooks.get(selectedBookindex.get());
                ValidationUtil.requireNoNull(selectedBook, "Selected Book can not be null");

            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.out.println("Invalid input, select again" + indexOutOfBoundsException.getMessage());
                return Optional.empty();
            }
            return Optional.of(selectedBook);
        }


}

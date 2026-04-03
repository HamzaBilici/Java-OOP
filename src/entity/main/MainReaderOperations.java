package entity.main;

import entity.Reader;
import entity.concrete.Faculty;
import entity.concrete.Librarian;
import entity.concrete.Student;
import entity.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

abstract class MainReaderOperations {
    private MainReaderOperations() {
    }

    protected static void readerOperations(Scanner scanner, Librarian librarian) {
        String selectedOptionReader;
        do {

            System.out.println("--------------------");
            System.out.println("0-Return Main Menu");
            System.out.println("1-List Readers");
            System.out.println("2-Add Reader");
            System.out.println("3-Change Verification");

            selectedOptionReader = scanner.nextLine();
            switch (selectedOptionReader) {
                case "0":
                    System.out.println("Returning to main menu");
                    break;
                case "1":
                    listAllReaders(librarian);
                    break;
                case "2":
                    addReadertoLibrary(scanner, librarian);
                    break;
                case "3":
                    changeVerificationofReader(scanner, librarian);
                    break;
                default:
                    System.out.println("Invalid input, select again");
            }
        } while (!selectedOptionReader.equals("0"));

    }

    protected static void addReadertoLibrary(Scanner scanner, Librarian librarian) {
        System.out.println("--------------");
        System.out.println("Enter Name");
        String inputName = scanner.nextLine();
        System.out.println("Enter Address");
        String inputAddress = scanner.nextLine();
        System.out.println("Enter Phone Number");
        String inputPhoneNo = scanner.nextLine();

        Reader newReader = null;
        try {
            System.out.println("Enter Reader Type");
            System.out.println("1-) Faculty");
            System.out.println("2-) Student");
            String inputReaderType = scanner.nextLine();

            switch (inputReaderType) {
                case "1":

                    newReader = new Faculty(inputName, inputAddress, inputPhoneNo);
                    break;
                case "2":
                    newReader = new Student(inputName, inputAddress, inputPhoneNo);
                    break;
                default:
                    System.out.println("Invalid input for reader type");
            }


        } catch (IllegalArgumentException exception) {
            System.out.println("There is a problem about reader creation:" + exception);
        }
        try {
            librarian.getLibrary().addReader(newReader);
            System.out.println("Reader added");
        } catch (IllegalArgumentException exception) {
            System.out.println("Reader couldn't add to library:" + exception);

        }
        System.out.println("Returning to menu");
    }

    protected static ArrayList<Reader> listAllReaders(Librarian librarian) {
        System.out.println("--------------");
        ArrayList<Reader> allReaders = new ArrayList<>(librarian.getLibrary().getReaders());
        for (Reader reader : allReaders) {
            System.out.println(allReaders.indexOf(reader) + "-) " + reader);
        }
        if (allReaders.isEmpty()) System.out.println("There is no Reader");
        return allReaders;
    }


    protected static Optional<Reader> changeVerificationofReader(Scanner scanner, Librarian librarian) {
        ArrayList<Reader> allReaders = listAllReaders(librarian);

        if (allReaders.isEmpty()) {
            return Optional.empty();
        }
        Optional<Integer> selectedReaderIndex = InputSelections.getInputIndex(scanner, "Reader");
        if (selectedReaderIndex.isEmpty()) {
            System.out.println("Invalid input");
            return Optional.empty();
        }
        Reader selectedReader;
        try {
            selectedReader = allReaders.get(selectedReaderIndex.get());
            ValidationUtil.requireNoNull(selectedReader, "Selected Reader can not be null");
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Invalid Reader Selection : " + indexOutOfBoundsException);
            return Optional.empty();
        }


        Optional<Boolean> selectedVerificationValue = InputSelections.getInputReaderVerification(scanner);
        if (selectedVerificationValue.isEmpty()) {
            System.out.println("Invalid Validation Selection");
            return Optional.empty();
        }
        selectedReader.setVerified(selectedVerificationValue.get());

        return Optional.of(selectedReader);
    }


}

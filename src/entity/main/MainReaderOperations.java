package entity.main;

import entity.Reader;
import entity.concrete.Faculty;
import entity.concrete.Librarian;
import entity.concrete.Student;
import entity.utils.ValidationUtil;

import java.util.ArrayList;
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


    protected static void changeVerificationofReader(Scanner scanner, Librarian librarian) {
        ArrayList<Reader> allReaders = listAllReaders(librarian);
        int selectedReaderIndex = -1;
        Reader selectedReader;
        if (!allReaders.isEmpty()) {
            System.out.println("Select Reader By their number");
            try {
                selectedReaderIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Invalid input");
                // continue;
            }
            try {
                selectedReader = allReaders.get(selectedReaderIndex);
                ValidationUtil.requireNoNull(selectedReader, "Selected Reader can not be null");

                System.out.println("Enter Reader New Verification");
                System.out.println("1-) Enable");
                System.out.println("2-) Disable");
                String selectedReaderVerification = scanner.nextLine();
                switch (selectedReaderVerification) {
                    case "1":
                        selectedReader.setVerified(true);
                        break;
                    case "2":
                        selectedReader.setVerified(false);
                        break;
                    default:
                        System.out.println("Invalid Verification Option");
                }


            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.out.println("Invalid Reader Selection : " + indexOutOfBoundsException);
            } catch (NullPointerException nullPointerException) {
                System.out.println("Null Reader : " + nullPointerException);
            }


            System.out.println("Returning menu");

        }

    }
}

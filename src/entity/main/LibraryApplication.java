package entity.main;

import entity.concrete.Author;
import entity.Person;
import entity.enums.BookEdition;
import entity.enums.BookStatus;

import java.util.*;

public class LibraryApplication {
    public static void main(String[] args) {
        //System.out.println(longestPalindrome("aacaaracecard"));
        Author author = new Author("ahmet");
        try {
            author.newBook("deneme", 1, BookStatus.AVAILABLE, BookEdition.LIMITED);
            List<UUID> keys = new ArrayList<>(author.getBooks().keySet());
            System.out.println(((Person) author.showBook(keys.get(0)).getOwner()).whoYouAre());
            //System.out.println(author.showBook(author.getBooks()));

            Iterator<UUID> iterator = author.getBooks().keySet().iterator();
            while (iterator.hasNext()) {
                System.out.println(author.showBook(iterator.next()));
            }

        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static String enUzun(String text) {

        List<Character> liste = new LinkedList<>();
        String returnValue = "";

        int longestStart = 0;
        int longestEnd = 0;
        int longestLength = -1;
        List<String> palindroms = new LinkedList<>();
        for (Character character : text.toCharArray()) {
            liste.add(character);
        }

        int counterOuter = 0;
        while (counterOuter < liste.size()) {

            int counterInner = counterOuter + 1;
            while (counterInner < liste.size()) {

                if (liste.get(counterInner).equals(liste.get(counterOuter))) {

                    boolean isPalindrom = true;

                    for (int i = counterOuter; i < (counterInner - counterOuter) / 2; i++) {

                        if (!liste.get(i).equals(liste.get(counterInner - i))) {
                            isPalindrom = false;
                            break;
                        }

                    }
                    if ((counterInner - counterOuter) > longestLength && isPalindrom) {
                        longestStart = counterOuter;
                        longestEnd = counterInner;
                        longestLength = longestEnd - longestStart;


                        for (int i = longestStart; i < longestEnd; i++) {
                            returnValue += liste.get(i);
                            System.out.println(returnValue);
                        }
                    }

                }


                counterInner++;
            }
            counterOuter++;
        }

        if (longestLength < 0) return "";


        for (int i = longestStart; i < longestEnd; i++) {
            returnValue += liste.get(i);
        }


        return returnValue;
    }


    public static boolean checkForPalindrome(String text) {

        String cleanText = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(Locale.ENGLISH);
        List<Character> list = new ArrayList<>();
        char[] splitList = cleanText.toCharArray();
        for (char textCharacter : splitList) {
            list.add(textCharacter);
        }
        int characterIndexFromStart = 0;
        int characterIndexFromEnd = list.size() - 1;
        while (characterIndexFromStart < characterIndexFromEnd) {
            if (!list.get(characterIndexFromStart).equals(list.get(characterIndexFromEnd))) {
                return false;
            }
            characterIndexFromStart++;
            characterIndexFromEnd--;
        }

        return true;
    }


    public static String longestPalindrome(String text) {

        int maxLen = 1;
        int start = 0;

        for (int i = 0; i < text.length(); i++) {

            //   System.out.println(text.charAt(i)+String.valueOf(i));
            System.out.println("Starts with : " + text.charAt(i) + "  " + String.valueOf(i));
            for (int j = i + 1; j < text.length(); j++) {

                boolean isEqual = text.charAt(i) == text.charAt(j);
                //    System.out.print((text.charAt(j) + String.valueOf(j)+"---" +isEqual+ "||   "));
                if (isEqual && checkForPalindrome(text.substring(i, j + 1))) {
                    System.out.print("|-" + text.substring(i, j + 1));
                    System.out.print("----");
                    System.out.print(checkForPalindrome(text.substring(i, j + 1)) + "-|");

                    if (j - i > maxLen) {
                        maxLen = j - i;
                        start = i;
                    }

                }
            }
            System.out.println(" ");
        }

        System.out.println(maxLen);

        return text.substring(start, start + maxLen + 1);
    }
}
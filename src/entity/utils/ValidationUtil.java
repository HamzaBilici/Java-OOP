package entity.utils;

public class ValidationUtil {
    private ValidationUtil() {
    }



    public static void requireNoNull(Object obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);

    }

    public static void requirePossitive(Long value, String message) {
        if (value <= 0)
            throw new IllegalArgumentException(message);

    }
    public static void requireUnBlankString(String value, String message) {

        if (value.isBlank())
            throw new IllegalArgumentException(message);

    }

    public static void requireSameTypeOfObject(Object checkedObj, Object objWithExpectedClass, String message) {

        if (!checkedObj.getClass().equals( objWithExpectedClass.getClass())) {
            throw new IllegalArgumentException(message);
        }

    }


}

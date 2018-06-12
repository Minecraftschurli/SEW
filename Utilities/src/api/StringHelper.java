package api;

public class StringHelper {
    public static String getTextBetween(String text, char from, char to) {
        int indexFrom = text.indexOf(from), indexTo = text.lastIndexOf(to);
        System.out.println(from + " " + indexFrom + " " + to + " " + indexTo + " " + text);
        return text.substring(indexFrom, indexTo);
    }
}

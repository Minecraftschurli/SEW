package api;

import java.io.File;
import java.lang.Boolean;
import java.util.LinkedList;
import java.util.List;

public class Misc {

    public static final char[] CHARS = {'!', '"', '§', '$', '%', '&', '/', '(', ')', '=', '?', '<', '>', ';', ',', ',', '.', '_', '-', '#', '\'', '*', '+', '{', '}', '[', ']', '\\', '~', '@', 'µ', '€', '|', '´', '`', '^', '°', '²', '³', 'ß', ' ', '\t', '\n', '\r'};

    public static boolean[] bitsFromByte(Byte b){
        boolean[] bools = new boolean[8];
        String s = Integer.toBinaryString(b.intValue());
        if (s.length()>=8)
            return bools;
        for (int i = 0; i < s.length(); i++) {
            bools[i] = api.Boolean.parseBoolean("" + s.charAt(i));
        }
        return bools;
    }

    public static boolean[] bitsFromBytes(byte[] bytes){
        boolean[][] booleans = new boolean[bytes.length][8];
        for (int i = 0; i < bytes.length; i++) {
            booleans[i] = bitsFromByte(bytes[i]);
        }
        List<Boolean> b = new LinkedList<>();
        for (boolean[] bools : booleans) {
            for (boolean bool : bools) {
                b.add(bool);
            }
        }
        boolean[] bits = new boolean[bytes.length*8];
        for (int i = 0; i < b.size(); i++) {
            bits[i] = b.get(i);
        }
        return bits;
    }

    public static void writeFile(File file, String text){
        FileHelper.saveToFile(text, file);
    }
}

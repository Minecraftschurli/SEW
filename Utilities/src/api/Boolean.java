package api;


public class Boolean {

    public static boolean parseBoolean(String text) throws NumberFormatException{
        switch (text) {
            case "true":
            case "1":
                return true;
            case "false":
            case "0":
                return false;
            default:
                throw new NumberFormatException(text);
        }
	}

	public static java.lang.Boolean[] convert(boolean[] booleans){
        java.lang.Boolean[] booleans1 = new java.lang.Boolean[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            booleans1[i] = booleans[i];
        }
        return booleans1;
    }

    public static boolean[] convert(java.lang.Boolean[] booleans){
        boolean[] booleans1 = new boolean[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            booleans1[i] = booleans[i];
        }
        return booleans1;
    }
}

package api.encryption;

public class BasicDecrypter extends Decrypter {
    public BasicDecrypter(int encryptionValue) {
        super(encryptionValue);
    }

    protected char decryptChar(char c) {
        if (c >= '0' && c <= '9'){
            for (int i = 0; i < this.encryptionValue; i++){
                c--;
                if (c < '0' || c > '9') {
                    c = '9';
                }
            }
            return c;
        }
        if (c >= 'a' && c <= 'z'){
            for (int i = 0; i < this.encryptionValue; i++){
                c--;
                if ((c < 'a' || c > 'z')) {
                    c = 'z';
                }
            }
            return c;
        }
        if (c >= 'A' && c <= 'Z'){
            for (int i = 0; i < this.encryptionValue; i++){
                c--;
                if ((c < 'A' || c > 'Z')) {
                    c = 'Z';
                }
            }
            return c;
        }
        return (char)(c - this.encryptionValue)!='핳'?(char)(c - this.encryptionValue):c;
    }
}

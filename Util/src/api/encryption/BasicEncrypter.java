package api.encryption;

import java.util.Random;

public class BasicEncrypter extends Encrypter {

    public static final Encrypter STANDARD_CESAR_ENCRYPTER = new BasicEncrypter(1L);

    public BasicEncrypter(){
        super();
    }

    public BasicEncrypter(long seed){
        super(seed);
    }

    public BasicEncrypter(int encryptionValue){
        super(encryptionValue);
    }

    @Override
    public String encrypt(String text) {
        return super.encrypt(text);
    }

    @Override
    protected char encryptChar(char c) {
        if (c >= '0' && c <= '9'){
            for (int i = 0; i < this.encryptionValue; i++){
                c++;
                if (c < '0' || c > '9') {
                    c = '0';
                }
            }
            return c;
        }
        if (c >= 'a' && c <= 'z'){
            for (int i = 0; i < this.encryptionValue; i++){
                c++;
                if ((c < 'a' || c > 'z')) {
                    c = 'a';
                }
            }
            return c;
        }
        if (c >= 'A' && c <= 'Z'){
            for (int i = 0; i < this.encryptionValue; i++){
                c++;
                if ((c < 'A' || c > 'Z')) {
                    c = 'A';
                }
            }
            return c;
        }
        return (char)(c + this.encryptionValue);
    }
}

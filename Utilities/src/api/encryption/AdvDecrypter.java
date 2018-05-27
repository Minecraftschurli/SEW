package api.encryption;

public class AdvDecrypter extends Decrypter {
    public AdvDecrypter(int encryptionValue) {
        super(encryptionValue);
    }

    @Override
    protected char decryptChar(char c) {
        return (char) (c/2);
    }
}

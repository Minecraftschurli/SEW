package api.encryption;

import api.RandomArray;

public class AdvEncrypter extends Encrypter {

    public AdvEncrypter(){ super(); }

    public AdvEncrypter(long seed){
        super(seed);
    }

    @Override
    public String encrypt(String text) {
        return super.encrypt(text);
    }

    @Override
    protected char encryptChar(char c) {
        return (char)(c + c);
    }
}

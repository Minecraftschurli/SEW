package api.encryption;

import api.Misc;

import java.io.*;

public abstract class Decrypter{

    protected int encryptionValue;

    private Decrypter(){}

    public Decrypter(int encryptionValue){
        this.encryptionValue = encryptionValue;
    }

    public String decrypt(String text){
        StringBuilder out = new StringBuilder();
        for (char c : text.toCharArray()) {
            char tmp = decryptChar(c);
            out.append(tmp);
        }
        return out.toString().replace("绿","").trim();
    }

    protected abstract char decryptChar(char c);

    public void decryptFile(File file) throws Exception{
        String content = Misc.readFile(file);
        content = decrypt(content);
        Misc.writeFile(file,content);
    }
}

package api.encryption;


import api.Misc;

import java.io.File;
import java.util.Random;

public abstract class Encrypter {

    public static final Encrypter STANDARD_CESAR_ENCRYPTER = new BasicEncrypter(1L);

    protected int encryptionValue;
    private Random rand;

    public Encrypter(){
        this.rand = new Random();
        int tmp = rand.nextInt();
        this.encryptionValue = tmp < 0 ? -(tmp) : tmp;
    }

    public Encrypter(long seed){
        this.rand = new Random(seed);
        int tmp = rand.nextInt();
        this.encryptionValue = tmp < 0 ? -(tmp) : tmp;
    }

    public Encrypter(int encryptionValue){
        this.encryptionValue = encryptionValue;
    }

    public String encrypt(String text){
        StringBuilder out = new StringBuilder();
        for (char c : text.toCharArray()) {
            switch (c){
                case 'Ä':
                    out.append(encryptChar('A') + encryptChar('E'));
                    break;
                case 'ä':
                    out.append(encryptChar('a') + encryptChar('e'));
                    break;
                case 'Ö':
                    out.append(encryptChar('O') + encryptChar('E'));
                    break;
                case 'ö':
                    out.append(encryptChar('o') + encryptChar('e'));
                    break;
                case 'Ü':
                    out.append(encryptChar('U') + encryptChar('E'));
                    break;
                case 'ü':
                    out.append(encryptChar('u') + encryptChar('e'));
                    break;
                default:
                    /*if (new String(Misc.CHARS).contains(""+c)){
                        out.append(c);
                    } else {*/
                        out.append(encryptChar(c));
                    //}
            }
        }
        return out.toString().trim();
    }

    protected abstract char encryptChar(char c);

    public void encryptFile(File file) throws Exception{
        String content = Misc.readFile(file);
        content = encrypt(content);
        Misc.writeFile(file, content);
    }

}

package api.encryption;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EncryptedWriter extends FileWriter {
    private final long encryptionSeed;

    public EncryptedWriter(File file, long encryptionSeed) throws IOException {
        super(file);
        this.encryptionSeed = encryptionSeed;
    }

    @Override
    public void write(String str) throws IOException {
        super.write(new AdvEncrypter(encryptionSeed).encrypt(str));
    }
}

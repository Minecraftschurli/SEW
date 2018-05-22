package api.encryption;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EncryptedWriter extends FileWriter {
    public EncryptedWriter(File file) throws IOException {
        super(file);
    }

    @Override
    public void write(String str) throws IOException {
        super.write(new AdvEncrypter(1L).encrypt(str));
    }
}

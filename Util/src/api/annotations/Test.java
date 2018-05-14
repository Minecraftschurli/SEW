package api.annotations;

import api.encryption.DeAndEncrypt;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


public class Test{

    public static void main(String[] args) {
        /*for (Map.Entry entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();
        }
        System.out.println();*/
        //System.out.println(Encrypter.STANDARD_CESAR_ENCRYPTER.encrypt("text123"));
        //System.out.println(new Decrypter(1155869325).decrypt("grkg678"));
        /*String path = System.getenv("OneDrive") + "\\Desktop\\key.txt";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        //File file = chooseFile();

        DeAndEncrypt.deAndEncrypt(new File(System.getenv("OneDrive") + "\\Desktop\\key.txt"));

        /*File fileIn = new File(System.getenv("OneDrive") + "\\Desktop\\in.txt");
        File fileEncrypted = new File(System.getenv("OneDrive") + "\\Desktop\\encrypted.txt");
        File fileOut = new File(System.getenv("OneDrive") + "\\Desktop\\out.txt");
        String encrypted = Encrypter.STANDARD_CESAR_ENCRYPTER.encrypt(Misc.readFile(fileIn));
        Misc.writeFile(fileEncrypted, encrypted);
        String decrypted = new Decrypter(1155869325).decrypt(Misc.readFile(fileEncrypted));
        Misc.writeFile(fileOut,decrypted);
        System.out.print(Misc.readFile(fileOut));
        /*try {
            runAllAnnotatedWith(TestMethod.class,"GUI");
        } catch (Exception ignored) {}*/
    }

    public static File chooseFile(){
        AtomicReference<File> out = new AtomicReference<>();
        JFileChooser fileChooser = new JFileChooser();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fileChooser.setCurrentDirectory(new File(System.getenv("OneDrive") + "\\Desktop\\"));
        frame.add(fileChooser,BorderLayout.CENTER);
        frame.setSize(700,500);
        frame.setVisible(true);
        fileChooser.addActionListener(e -> {
            if (e.getActionCommand().equals("CancelSelection")) {
                System.exit(0);
            } else if (e.getActionCommand().equals("ApproveSelection")) {
                out.set(fileChooser.getSelectedFile());
                frame.setVisible(false);
            }
        });
        while (out.get() == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
        }
        return out.get();
    }

    private static void runAllAnnotatedWith(Class<? extends Annotation> annotation,String name) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);

        for (Method m : methods) {
            if (annotation.isInstance(TestMethod.class) && name.equals(((TestMethod)m.getAnnotation(annotation)).name())) {
                m.invoke(null);
            }
        }
    }
}


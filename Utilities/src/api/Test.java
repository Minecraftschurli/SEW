package api;

public class Test{

    public static void main(String[] args) {

    }

    /*public static File chooseFile(){
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

    /*private static void runAllAnnotatedWith(Class<? extends Annotation> annotation,String name) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);

        for (Method m : methods) {
            if (annotation.isInstance(TestMethod.class) && name.equals(((TestMethod)m.getAnnotation(annotation)).name())) {
                m.invoke(null);
            }
        }
    }*/
}


package example;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class Client {

    public static void main(String[] args) {

        try {

            URL url = new URL("http://localhost:9000/HelloWorld?wsdl");
            QName qname = new QName(null, "");

            Service service = Service.create(url, qname);

            HelloWorld server = service.getPort(HelloWorld.class);
            String name = "prasad";
            System.out.println(server.sayHelloWorldFrom(name));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

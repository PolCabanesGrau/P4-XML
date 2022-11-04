import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class App {
    private static final String FILENAME = "sitemap.xml";

    public static void main(String[] args) {
        // Iniciamos la clase que nos permite iniciar la "fábrica" de documentos XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            // Opcional, pero se recomienda para evitar ataques XXE (XML External Entities)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // Iniciamos DocumentBuilder para analizar un archivo XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Abrimos el fichero y lo analizamos con db.parse
            Document doc = db.parse("https://www.xataka.com/sitemap_index.xml");

            // La normalización es opcional, pero recomendado para XML mal formateados
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Elemento raiz :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // Obtenemos todos los staffs en una lista de nodos que podemos recorrer
            NodeList list = doc.getElementsByTagName("sitemap");

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Obtener el atributo de identificación del staff
                    String id = element.getAttribute("id");

                    // Obtener el texto de los diferentes elementos.
                    String link = element.getElementsByTagName("loc").item(0).getTextContent();

                    if (element.getElementsByTagName("lastmod").item(0)==null){
                        System.out.println("Link : " + link);
                    }else{
                        String data = element.getElementsByTagName("lastmod").item(0).getTextContent();
                        System.out.println("Link : " + link);
                        System.out.println("Data : " + data);
                    }

                    System.out.println("Current Element :" + node.getNodeName());
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

package repository;

import domain.Cake;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CakeRepositoryXmlFile extends FileRepository<Integer, Cake> {

    public CakeRepositoryXmlFile(String fileName) throws FileNotValidException {
        super(fileName);
        readFile();
        writeFile();
    }

    @Override
    protected void readFile() throws FileNotValidException {
        try {
            // Set up the DOM parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file and normalize
            Document document = builder.parse(new File(this.filename));
            document.getDocumentElement().normalize();

            // Get all <cake> elements
            NodeList nodeList = document.getElementsByTagName("cake");
            Map<Integer, Cake> cakeMap = new HashMap<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element cakeElement = (Element) node;


                    Integer id = Integer.parseInt(cakeElement.getElementsByTagName("id").item(0).getTextContent());
                    int weight = Integer.parseInt(cakeElement.getElementsByTagName("weight").item(0).getTextContent());
                    int price = Integer.parseInt(cakeElement.getElementsByTagName("price").item(0).getTextContent());
                    String type = cakeElement.getElementsByTagName("type").item(0).getTextContent();

                    Cake cake = new Cake(id, weight, price, type);

                    cakeMap.put(id, cake);
                }
            }

            // Assign the map to the repository's map field
            this.map = cakeMap;

        } catch (Exception e) {
            throw new FileNotValidException("Failed to read XML file: " + this.filename + " - " + e.getMessage());
        }
    }

    @Override
    protected void writeFile() {
        try {
            // Create a new XML document
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Root element <cakes>
            Element root = document.createElement("cakes");
            document.appendChild(root);

            // Loop through the map and create <cake> elements
            for (Map.Entry<Integer, Cake> entry : this.map.entrySet()) {
                Cake cake = entry.getValue();

                // <cake> element
                Element cakeElement = document.createElement("cake");
                root.appendChild(cakeElement);

                // <id> element
                Element idElement = document.createElement("id");
                idElement.appendChild(document.createTextNode(cake.getId().toString()));
                cakeElement.appendChild(idElement);

                // <weight> element
                Element weightElement = document.createElement("weight");
                weightElement.appendChild(document.createTextNode(Integer.toString(cake.getWeight())));
                cakeElement.appendChild(weightElement);

                // <price> element
                Element priceElement = document.createElement("price");
                priceElement.appendChild(document.createTextNode(Integer.toString(cake.getPrice())));
                cakeElement.appendChild(priceElement);

                // <type> element
                Element typeElement = document.createElement("type");
                typeElement.appendChild(document.createTextNode(cake.getType()));
                cakeElement.appendChild(typeElement);
            }

            // Write the content into an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(this.filename));

            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            throw new RuntimeException("Failed to write XML file: " + this.filename, e);
        }
    }
}

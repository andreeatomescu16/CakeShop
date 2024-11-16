package repository;

import domain.Cake;
import domain.Order;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryXmlFile extends FileRepository<Integer, Order> {

    public OrderRepositoryXmlFile(String fileName) throws FileNotValidException {
        super(fileName);
        readFile();
        writeFile();
    }

    @Override
    protected void readFile() throws FileNotValidException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(this.filename));
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("order");
            Map<Integer, Order> orderMap = new HashMap<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element orderElement = (Element) node;

                    Integer id = Integer.parseInt(orderElement.getElementsByTagName("id").item(0).getTextContent());
                    boolean status = Boolean.parseBoolean(orderElement.getElementsByTagName("status").item(0).getTextContent());
                    String name = orderElement.getElementsByTagName("name").item(0).getTextContent();

                    Element cakeElement = (Element) orderElement.getElementsByTagName("cake").item(0);
                    Integer cakeId = Integer.parseInt(cakeElement.getElementsByTagName("id").item(0).getTextContent());
                    int weight = Integer.parseInt(cakeElement.getElementsByTagName("weight").item(0).getTextContent());
                    int price = Integer.parseInt(cakeElement.getElementsByTagName("price").item(0).getTextContent());
                    String type = cakeElement.getElementsByTagName("type").item(0).getTextContent();

                    Cake cake = new Cake(cakeId, weight, price, type);
                    Order order = new Order(id, status, cake, name);

                    orderMap.put(id, order);
                }
            }

            this.map = orderMap;

        } catch (Exception e) {
            throw new FileNotValidException("Failed to read XML file: " + this.filename + " - " + e.getMessage());
        }
    }

    @Override
    protected void writeFile() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("orders");
            document.appendChild(root);

            for (Map.Entry<Integer, Order> entry : this.map.entrySet()) {
                Order order = entry.getValue();

                Element orderElement = document.createElement("order");
                root.appendChild(orderElement);

                Element idElement = document.createElement("id");
                idElement.appendChild(document.createTextNode(order.getId().toString()));
                orderElement.appendChild(idElement);

                Element statusElement = document.createElement("status");
                statusElement.appendChild(document.createTextNode(Boolean.toString(order.getStatus())));
                orderElement.appendChild(statusElement);

                Element nameElement = document.createElement("name");
                nameElement.appendChild(document.createTextNode(order.getName()));
                orderElement.appendChild(nameElement);

                Element cakeElement = document.createElement("cake");
                orderElement.appendChild(cakeElement);

                Element cakeIdElement = document.createElement("id");
                cakeIdElement.appendChild(document.createTextNode(order.getCake().getId().toString()));
                cakeElement.appendChild(cakeIdElement);

                Element weightElement = document.createElement("weight");
                weightElement.appendChild(document.createTextNode(Integer.toString(order.getCake().getWeight())));
                cakeElement.appendChild(weightElement);

                Element priceElement = document.createElement("price");
                priceElement.appendChild(document.createTextNode(Integer.toString(order.getCake().getPrice())));
                cakeElement.appendChild(priceElement);

                Element typeElement = document.createElement("type");
                typeElement.appendChild(document.createTextNode(order.getCake().getType()));
                cakeElement.appendChild(typeElement);
            }

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

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

public class CurrencyConverter {

    public double ParserXML(String readCurrency, double readValue) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File("eurofxref-daily.xml");
        DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbF.newDocumentBuilder();
        Document document = dBuilder.parse(inputFile);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("Cube");


        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                String currency = elem.getAttribute("currency");

                if (currency.equals(readCurrency)){
                    String currencyValue = elem.getAttribute("rate");
                    double doubleCurrency = Double.parseDouble(currencyValue);
                    double convert = doubleCurrency*readValue;
                    System.out.println(convert+" "+currency);
                    return convert;
                }
            }
        } return -1;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        double wynik = 0;
        while (wynik <= 0) {
            System.out.print("Wprowadź walutę: "+"\n");
            Scanner currency = new Scanner(System.in);
            String readCurrency = currency.nextLine();
            readCurrency = readCurrency.toUpperCase();

            System.out.print("Wprowadź ilość pieniędzy: "+"\n");
            Scanner value = new Scanner(System.in);
            double readValue = value.nextDouble();

            CurrencyConverter cc = new CurrencyConverter();
            wynik = cc.ParserXML(readCurrency, readValue);
            if (wynik < 0) {
                System.out.println("Wprowadzono błędny symbol waluty. Spróbuj jeszcze raz."+"\n");
            }
        }

    }
}

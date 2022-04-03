import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class Read {
    private static final String HUF_CURRENCY_CODE = "348";
    private static final String NOK_CURRENCY_CODE = "578";
    static float convert;

    public static void main(String[] args) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try (InputStream is = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream()) {
            SAXParser saxParser = factory.newSAXParser();
            MapStaffObjectHandlerSax handler = new MapStaffObjectHandlerSax();
            saxParser.parse(is, handler);
            List<Currency> result = handler.getResult();
            Currency first = find(result, HUF_CURRENCY_CODE);
            Currency second = find(result, NOK_CURRENCY_CODE);
            convert = first.value / first.nominal;
            convert = convert * (second.value / second.nominal);
            System.out.println(first.name + " -> " + second.name + ": " + convert);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Currency find(List<Currency> a, String numCode) {
        Currency n;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getNumCode().equals(numCode)) {
                n = a.get(i);
                return n;
            }
        }
        throw new UnsupportedOperationException();
    }
}

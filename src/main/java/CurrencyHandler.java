import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class CurrencyHandler extends DefaultHandler {
    private final StringBuilder currentValue = new StringBuilder();
    List<Currency> result;
    Currency current;

    public List<Currency> getResult() {
        return result;
    }

    @Override
    public void startDocument() {
        result = new ArrayList<>();
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {
        currentValue.setLength(0);
        if (qName.equalsIgnoreCase("Valute")) {
            current = new Currency();
            String id = attributes.getValue("ID");
            current.setId(id);
        }
    }

    public void endElement(String uri,
                           String localName,
                           String qName) {

        if (qName.equalsIgnoreCase("numCode")) {
            current.setNumCode(currentValue.toString());
        }
        if (qName.equalsIgnoreCase("charCode")) {
            current.setCharCode(currentValue.toString());
        }
        if (qName.equalsIgnoreCase("Nominal")) {
            current.setNominal(Float.parseFloat(currentValue.toString()));
        }
        if (qName.equalsIgnoreCase("Name")) {
            current.setName(currentValue.toString());
        }
        if (qName.equalsIgnoreCase("Value")) {
            String s = currentValue.toString();
            s = s.replace(",", ".");
            current.setValue(Float.parseFloat(s));
        }
        if (qName.equalsIgnoreCase("Valute")) {
            result.add(current);
        }
    }

    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);

    }
}

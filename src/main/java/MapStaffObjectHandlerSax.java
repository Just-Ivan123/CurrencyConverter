import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MapStaffObjectHandlerSax extends DefaultHandler {
    private final StringBuilder currentValue = new StringBuilder();
    List<Currency> result;
    Currency currentStaff;

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


            currentStaff = new Currency();


            String id = attributes.getValue("ID");
            currentStaff.setId(id);
        }


    }

    public void endElement(String uri,
                           String localName,
                           String qName) {

        if (qName.equalsIgnoreCase("numCode")) {
            currentStaff.setNumCode(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("charCode")) {
            currentStaff.setCharCode(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("Nominal")) {
            currentStaff.setNominal(Float.parseFloat(currentValue.toString()));
        }

        if (qName.equalsIgnoreCase("Name")) {
            currentStaff.setName(currentValue.toString());
        }
        if (qName.equalsIgnoreCase("Value")) {
            String s = currentValue.toString();
            s = s.replace(",", ".");
            currentStaff.setValue(Float.parseFloat(s));
        }

        if (qName.equalsIgnoreCase("Valute")) {
            result.add(currentStaff);
        }

    }

    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);

    }
}

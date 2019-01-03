package org.chase.juco.unit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UnitParser {

    private static final String SCHEMA_URL = "https://raw.githubusercontent.com/Chase22/JUCO/dev/src/main/resources/unitshema.xsd";
    private static final Namespace ns = Namespace.getNamespace(SCHEMA_URL);

    /**
     * Parsed a List of {@link UnitGroup} from an InputStream
     * @param stream The Stream of data to be parsed
     * @return A list of the {@link UnitGroup} parsed from the stream
     */
    public List<UnitGroup> parseFromStream(final InputStream stream) {
        List<UnitGroup> unitGroups = new ArrayList<>();
        Document document;

        try {
            final URL schemaUrl = new URL(SCHEMA_URL);
//            final Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl);
            final SAXBuilder saxBuilder = new SAXBuilder();

            // TODO uncomment when shema works correctly
//            saxBuilder.setXMLReaderFactory(new XMLReaderSchemaFactory(schema));

            document = saxBuilder.build(stream);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            return null;
        }

        Element root = document.getRootElement();

        List<Element> groups = root.getChildren("group", ns);

        for (Element e : groups) {
            UnitGroup group = new UnitGroup();
            group.setName(e.getAttributeValue("name"));

            group.setUnits(
                    e.getChildren("unit", ns)
                    .stream()
                    .map(this::parseUnit).peek(unit -> unit.setUnitgroup(group))
                    .collect(Collectors.toList())
            );
            unitGroups.add(group);
        }

        return unitGroups;
    }

    public List<UnitGroup> parseFromStream(File inputFile) throws FileNotFoundException {
        return parseFromStream(new FileInputStream(inputFile));
    }

    private Unit parseUnit(Element unitElement) {
        Unit unit = new Unit();

        unit.setName(unitElement.getChildText("name", ns));
        unit.setConversionRate(parseDouble(unitElement.getChildText("conversionRate", ns)));
        unit.setShorthand(unitElement.getChildText("shorthand", ns));

        if (unit.getShorthand() == null) {
            unit.setShorthand(unit.getName());
        }

        unit.setBaseUnit(parseBoolean(unitElement.getChildText("baseunit", ns)));

        return unit;
    }
}

package org.chase.juco.unit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;

public class UnitParser {

    public static final String SCHEMA_URL = "https://raw.githubusercontent.com/Chase22/JUCO/dev/src/main/resources/unitshema.xsd";
    public static final Namespace ns = Namespace.getNamespace(SCHEMA_URL);

    public List<UnitGroup> parseFromFile(final InputStream stream) {
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

    private Unit parseUnit(Element unitElement) {
        Unit unit = new Unit();

        unit.setName(unitElement.getChildText("name", ns));
        unit.setConversion(parseDouble(unitElement.getChildText("conversionRate", ns)));
        unit.setShorthand(unitElement.getChildText("shorthand", ns));

        if (unit.getShorthand() == null) {
            unit.setShorthand(unit.getName());
        }

        unit.setBaseUnit(parseBoolean(unitElement.getChildText("baseunit", ns)));

        return unit;
    }
}

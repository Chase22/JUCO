package org.chase.juco.base;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;

public class UnitParser {

    public List<UnitGroup> parseFromFile(final InputStream stream) {
        List<UnitGroup> unitGroups = new ArrayList<>();
        Document document;

        try {
            document = new SAXBuilder().build(stream);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            return null;
        }

        Element root = document.getRootElement();

        List<Element> groups = root.getChildren("group");

        for (Element e : groups) {
            UnitGroup group = new UnitGroup();
            group.setName(e.getAttributeValue("name"));

            group.setUnits(
                    e.getChildren("unit")
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

        unit.setName(unitElement.getChildText("name"));
        unit.setConversion(parseDouble(unitElement.getChildText("conversionRate")));
        unit.setShorthand(unitElement.getChildText("shorthand"));

        if (unit.getShorthand() == null) {
            unit.setShorthand(unit.getName());
        }

        unit.setBaseUnit(parseBoolean(unitElement.getChildText("baseunit")));

        return unit;
    }
}

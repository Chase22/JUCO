package org.chase.juco.unit;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UnitParser {


    /**
     * Parsed a List of {@link UnitGroup} from an InputStream
     * @param stream The Stream of data to be parsed
     * @return A list of the {@link UnitGroup} parsed from the stream
     */
    public List<UnitGroup> parseFromStream(final InputStream stream) {

        Yaml yaml = new Yaml();
        LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, Object>>> map = yaml.load(stream);

        return map.entrySet().stream().map(entry ->
            UnitGroup.create(
                entry.getKey(),
                entry.getValue()
                        .entrySet()
                        .stream()
                        .map(this::parseUnit)
                        .collect(toList()))).collect(toList());
    }

    public List<UnitGroup> parseFromStream(File inputFile) throws FileNotFoundException {
        return parseFromStream(new FileInputStream(inputFile));
    }

    private Unit parseUnit(Entry<String, LinkedHashMap<String, Object>> entry) {
        Unit unit = new Unit();

        unit.setName(entry.getKey());

        entry.getValue().forEach((key, value) -> {
            if (key.equalsIgnoreCase("shorthand")) {
                unit.setShorthand((String) value);
            }
            if (key.equalsIgnoreCase("conversionRate")) {
                unit.setConversionRate(Double.parseDouble(value.toString()));
            }
            if (key.equalsIgnoreCase("baseUnit")) {
                unit.setBaseUnit((Boolean) value);
            }
        });
        return unit;
    }
}

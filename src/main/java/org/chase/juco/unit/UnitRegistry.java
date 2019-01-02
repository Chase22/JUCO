package org.chase.juco.unit;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A global Registry holding references to all Unit objects defined in the units.xml file. <br>
 * Must call {@link UnitRegistry#initialise()} before calling {@link UnitRegistry#getInstance()}
 */
public class UnitRegistry {
    private static UnitRegistry Instance = null;

    private List<UnitGroup> groups;

    public static UnitRegistry getInstance() {
        if (Instance == null) {
            throw new NotInitializedException("Registry has not been initialized");
        }
        return Instance;
    }

    public static void initialise() {
        Instance = new UnitRegistry();
        Instance.groups = new UnitParser().parseFromFile(UnitRegistry.class.getResourceAsStream("/units.xml"));
    }

    private UnitRegistry(){}

    public List<UnitGroup> getGroups() {
        return groups;
    }

    public List<Unit> getUnits() {
        return groups.stream().map(UnitGroup::getUnits).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Optional<UnitGroup> getGroup(String name) {
        return groups.stream().filter(unitGroup -> unitGroup.getName().equals(name)).findFirst();
    }

}

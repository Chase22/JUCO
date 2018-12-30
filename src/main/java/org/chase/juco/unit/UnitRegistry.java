package org.chase.juco.unit;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitRegistry {
    private static UnitRegistry Instance = new UnitRegistry();

    private List<UnitGroup> groups;

    public static UnitRegistry getInstance() {
        return Instance;
    }

    private UnitRegistry() {
        groups = new UnitParser().parseFromFile(UnitRegistry.class.getResourceAsStream("/units.xml"));
    }

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

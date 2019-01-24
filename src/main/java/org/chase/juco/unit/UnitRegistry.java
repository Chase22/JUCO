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

    /**
     * Gets the current {@link UnitRegistry} Instance
     * @return the current {@link UnitRegistry} Instance
     */
    public static UnitRegistry getInstance() {
        if (Instance == null) {
            throw new NotInitializedException("Registry has not been initialized");
        }
        return Instance;
    }

    /**
     * Initialise the UnitRegistry, parsing all Units from the units.xml file and making the Instance available through {@link UnitRegistry#getInstance()}
     */
    public static void initialise() {
        Instance = new UnitRegistry();
        Instance.groups = new UnitParser().parseFromStream(UnitRegistry.class.getResourceAsStream("/units.yml"));
    }

    private UnitRegistry(){}

    /**
     * @return A list of all {@link UnitGroup Unitsgroups} known to the Registry.
     */
    public List<UnitGroup> getGroups() {
        return groups;
    }

    /**
     * @return A flat List of all Units in all Groups in this registry. Can contain multiple BaseUnits and multiple Units with the same Name or Shorthand.
     */
    public List<Unit> getUnits() {
        return groups.stream().map(UnitGroup::getUnits).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Returns one {@link UnitGroup} by its {@link UnitGroup#name}
     * @param name the name of the Group
     * @return The {@link UnitGroup} with the given Name
     */
    public Optional<UnitGroup> getGroup(String name) {
        return groups.stream().filter(unitGroup -> unitGroup.getName().equals(name)).findFirst();
    }

}

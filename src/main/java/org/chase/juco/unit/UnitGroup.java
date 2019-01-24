package org.chase.juco.unit;

import org.chase.juco.exceptions.NoBaseUnitException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A group of units with one BaseUnit. Conversions are only allowed between units of the same group
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class UnitGroup {

    /**
     * Creates a unitgroup with the given name and units. The Unit#unitGroup gets set to the returned UnitGroup
     * @return a new UnitGroup
     */
    public static UnitGroup create(final String name, final List<Unit> units) {
        UnitGroup group = new UnitGroup();
        group.setName(name);
        group.setUnits(units.stream().peek(unit -> unit.setUnitgroup(group)).collect(Collectors.toList()));
        return group;
    }

    /**
     * The name uniquely identifying one group
     */
    private String name;

    /**
     * The units associated with this group
     */
    private List<Unit> units = new ArrayList<>();

    /**
     * Get a unit by it's name or shorthand. It is first checked if a unit with the given name exists.
     * If non exists, a unit with the given shorthand is returned. If none exists, an empty {@link Optional} is returned.
     * @param name The name or shorthand to be searched for
     * @return The Unit with the associated name or shorthand
     */
    public Optional<Unit> getUnit(String name) {
        Optional<Unit> unitOptional = units.stream().filter(unit -> unit.getName().equalsIgnoreCase(name)).findFirst();

        if (!unitOptional.isPresent()) {
            unitOptional = getUnitByShort(name);
        }

        return unitOptional;
    }

    /**
     * Get a unit by the shorthand of the unit
     * @param shorthand the shorthand to be searched for
     * @return The Unit with the associated shorthand
     */
    public Optional<Unit> getUnitByShort(String shorthand) {
        return units.stream()
                .filter(unit -> unit.getShorthand() != null)
                .filter(unit -> unit.getShorthand().equalsIgnoreCase(shorthand))
                .findFirst();
    }

    /**
     * Gets the base unit of this group. Throws a {@link NoBaseUnitException} if no base unit is found
     * @return The base unit of this group
     */
    public Unit getBase() {
        return units.stream().filter(Unit::isBaseUnit).findFirst().orElseThrow(NoBaseUnitException::new);
    }

    public String getName() {
        return this.name;
    }

    public List<Unit> getUnits() {
        return this.units;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUnits(final List<Unit> units) {
        this.units = units;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UnitGroup unitGroup = (UnitGroup) o;
        return Objects.equals(name, unitGroup.name) &&
                Objects.equals(units, unitGroup.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UnitGroup.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("units=" + units)
                .toString();
    }
}

package org.chase.juco.unit;

import lombok.Data;
import org.chase.juco.exceptions.NoBaseUnitException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A group of units with one BaseUnit. Conversions are only allowed between units of the same group
 */
@Data
public class UnitGroup extends ArrayList<Unit> {

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
        return units.stream().filter(unit -> unit.getShorthand().equalsIgnoreCase(shorthand)).findFirst();
    }

    /**
     * Gets the base unit of this group. Throws a {@link NoBaseUnitException} if no base unit is found
     * @return The base unit of this group
     */
    public Unit getBase() {
        return units.stream().filter(Unit::isBaseUnit).findFirst().orElseThrow(NoBaseUnitException::new);
    }
}

package org.chase.juco.unit;

import lombok.Data;
import org.chase.juco.exceptions.NoBaseUnitException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class UnitGroup {

    private String name;

    private List<Unit> units = new ArrayList<>();

    public Optional<Unit> getUnit(String name) {
        Optional<Unit> unitOptional = units.stream().filter(unit -> unit.getName().equalsIgnoreCase(name)).findFirst();

        if (!unitOptional.isPresent()) {
            unitOptional = getUnitByShort(name);
        }

        return unitOptional;
    }

    public Optional<Unit> getUnitByShort(String name) {
        return units.stream().filter(unit -> unit.getShorthand().equalsIgnoreCase(name)).findFirst();
    }

    public Unit getUnit(int index) {
        return units.get(index);
    }

    public Unit getBase() {
        return units.stream().filter(Unit::isBaseUnit).findFirst().orElseThrow(NoBaseUnitException::new);
    }
}

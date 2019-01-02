package org.chase.juco.unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chase.juco.amount.Amount;
import org.chase.juco.amount.Prefix;

/**
 * Represents a Unit of Measurement, consisting of a {@link Unit#name} and a {@link Unit#conversionRate}
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unit {
    private UnitGroup unitgroup;

    /**
     * The SI-Prefix of this unit. <br>
     * <i>Note: Units with prefix should be added to the {@link UnitRegistry}. They should only be optained using {@link Unit#withPrefix(Prefix)}</i>
     * @see Prefix
     * @see <a href=https://en.wikipedia.org/wiki/Metric_prefix>https://en.wikipedia.org/wiki/Metric_prefix</a>
     */
    private Prefix prefix;

    /**
     * Determines if this unit is the BaseUnit of its {@link Unit#unitgroup}
     *
     * During conversionRate, all Amounts get converted into the base unit before being converted into the target unit.
     */
    private boolean baseUnit;

    /**
     * The name of the unit.
     */
    private String name;

    /**
     * An optional shorthand for the Unit. Is used in {@link Amount#toString()} and as a second identifier.
     */
    private String shorthand;

    /**
     * The conversion rate to the base unit of it's {@link Unit#unitgroup}.
     * Denotes which amount of this unit is equal to 1 baseunit.
     * <br>
     * For example:
     * <pre>
     * name: Celcius
     * baseUnit: false
     * conversionRate: -272.15 // 1 Kelvin = -272.15° Celcius
     * </pre>
     */
    private double conversionRate;

    /**
     * Returns a new Unit instance with the given Prefix. Can be used in {@link Amount#convertTo(Unit)}
     * @param prefix The {@link Prefix} to be applied
     * @return a new Unit with the applied {@link Prefix}
     */
    public Unit withPrefix(Prefix prefix) {
        return Unit.builder()
                .baseUnit(baseUnit)
                .name(name)
                .shorthand(shorthand)
                .conversionRate(conversionRate)
                .prefix(prefix)
                .build();
    }

    /**
     * Returns the shorthand of the unit if not null or empty. Else returns the unit name.
     * Useful for displaying
     * @return The shorthand or the name of the Unit.
     */
    public String getDisplayShort() {
        return shorthand != null && !shorthand.isEmpty() ? shorthand : name;
    }
}

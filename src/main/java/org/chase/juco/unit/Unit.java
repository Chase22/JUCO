package org.chase.juco.unit;

import org.chase.juco.amount.Amount;
import org.chase.juco.amount.Prefix;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents a Unit of Measurement, consisting of a {@link Unit#name} and a {@link Unit#conversionRate}
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Unit {

    /**
     * The {@link UnitGroup} this Unit belongs to
     */
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

    public Unit() {}

    public Unit(final UnitGroup unitgroup, final Prefix prefix, final boolean baseUnit, final String name, final String shorthand, final double conversionRate) {
        this.unitgroup = unitgroup;
        this.prefix = prefix;
        this.baseUnit = baseUnit;
        this.name = name;
        this.shorthand = shorthand;
        this.conversionRate = conversionRate;
    }

    public static UnitBuilder builder() {
        return new UnitBuilder();
    }

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

    public UnitGroup getUnitgroup() {
        return this.unitgroup;
    }

    public Prefix getPrefix() {
        return this.prefix;
    }

    public boolean isBaseUnit() {
        return this.baseUnit;
    }

    public String getName() {
        return this.name;
    }

    public String getShorthand() {
        return this.shorthand;
    }

    public double getConversionRate() {
        return this.conversionRate;
    }

    public void setUnitgroup(final UnitGroup unitgroup) {
        this.unitgroup = unitgroup;
    }

    public void setPrefix(final Prefix prefix) {
        this.prefix = prefix;
    }

    public void setBaseUnit(final boolean baseUnit) {
        this.baseUnit = baseUnit;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setShorthand(final String shorthand) {
        this.shorthand = shorthand;
    }

    public void setConversionRate(final double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Unit.class.getSimpleName() + "[", "]")
                .add("unitgroup=" + unitgroup.getName())
                .add("prefix=" + prefix)
                .add("baseUnit=" + baseUnit)
                .add("name='" + name + "'")
                .add("shorthand='" + shorthand + "'")
                .add("conversionRate=" + conversionRate)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Unit unit = (Unit) o;
        return baseUnit == unit.baseUnit &&
                Double.compare(unit.conversionRate, conversionRate) == 0 &&
                Objects.equals(unitgroup, unit.unitgroup) &&
                prefix == unit.prefix &&
                Objects.equals(name, unit.name) &&
                Objects.equals(shorthand, unit.shorthand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitgroup, prefix, baseUnit, name, shorthand, conversionRate);
    }

    public static class UnitBuilder {
        private UnitGroup unitgroup;
        private Prefix prefix;
        private boolean baseUnit;
        private String name;
        private String shorthand;
        private double conversionRate;

        UnitBuilder() {
        }

        /**
         * @see Unit#unitgroup
         */
        public Unit.UnitBuilder unitgroup(final UnitGroup unitgroup) {
            this.unitgroup = unitgroup;
            return this;
        }

        /**
         * @see Unit#prefix
         */
        public Unit.UnitBuilder prefix(final Prefix prefix) {
            this.prefix = prefix;
            return this;
        }

        /**
         * @see Unit#baseUnit
         */
        public Unit.UnitBuilder baseUnit(final boolean baseUnit) {
            this.baseUnit = baseUnit;
            return this;
        }

        public Unit.UnitBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Unit.UnitBuilder shorthand(final String shorthand) {
            this.shorthand = shorthand;
            return this;
        }

        public Unit.UnitBuilder conversionRate(final double conversionRate) {
            this.conversionRate = conversionRate;
            return this;
        }

        public Unit build() {
            return new Unit(unitgroup, prefix, baseUnit, name, shorthand, conversionRate);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", UnitBuilder.class.getSimpleName() + "[", "]")
                    .add("unitgroup=" + unitgroup.getName())
                    .add("prefix=" + prefix)
                    .add("baseUnit=" + baseUnit)
                    .add("name='" + name + "'")
                    .add("shorthand='" + shorthand + "'")
                    .add("conversionRate=" + conversionRate)
                    .toString();
        }
    }
}

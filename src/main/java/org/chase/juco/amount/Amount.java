package org.chase.juco.amount;

import org.chase.juco.exceptions.ConversionGroupException;
import org.chase.juco.unit.Unit;

import java.util.Objects;

/**
 * Represents an Amount of a specific Unit.
 * @see Unit
 * @since 1.0.0
*/
public class Amount {
    private double amount;
    private Unit unit;

    @java.beans.ConstructorProperties({"amount", "unit"})
    Amount(final double amount, final Unit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public static AmountBuilder builder() {
        return new AmountBuilder();
    }

    /**
     * Converts the amount into another Unit.
     * @param unit The {@link Unit} to convert to
     * @return A new {@link Amount} with the converted amount
     */
    public Amount convertTo(Unit unit) {
        if (!unit.getUnitgroup().equals(this.unit.getUnitgroup())) {
            throw new ConversionGroupException();
        }

        double amountPrefix = unit.getPrefix() == null ? this.amount : this.amount / unit.getPrefix().getFactor();

        double amountBase = amountPrefix / this.unit.getConversionRate();

        return Amount.builder()
                .amount(amountBase * unit.getConversionRate())
                .unit(unit)
                .build();
    }

    @Override
    public String toString() {
        return String.format("%s{%f %s}", Amount.class.getSimpleName(), amount, unit.getDisplayShort());
    }


    public double getAmount() {
        return this.amount;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public void setUnit(final Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Amount amount1 = (Amount) o;
        return Double.compare(amount1.amount, amount) == 0 &&
                Objects.equals(unit, amount1.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    public static class AmountBuilder {
        private double amount;
        private Unit unit;

        AmountBuilder() {
        }

        public Amount.AmountBuilder amount(final double amount) {
            this.amount = amount;
            return this;
        }

        public Amount.AmountBuilder unit(final Unit unit) {
            this.unit = unit;
            return this;
        }

        public Amount build() {
            return new Amount(amount, unit);
        }

        public String toString() {
            return "Amount.AmountBuilder(amount=" + this.amount + ", unit=" + this.unit + ")";
        }
    }
}



package org.chase.juco.amount;

import lombok.Builder;
import lombok.Data;
import org.chase.juco.exceptions.ConversionGroupException;
import org.chase.juco.unit.Unit;

/**
 * Represents an Amount of a specific Unit.
 * @see Unit
 * @since 1.0.0
*/
@Data
@Builder
public class Amount {
    private double amount;
    private Unit unit;

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
}



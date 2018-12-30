package org.chase.juco.amount;

import lombok.Builder;
import lombok.Data;
import org.chase.juco.exceptions.ConversionGroupException;
import org.chase.juco.unit.Unit;

@Data
@Builder
public class Amount {
    private double amount;
    private Unit unit;

    public Amount convertTo(Unit unit) {
        if (!unit.getUnitgroup().equals(this.unit.getUnitgroup())) {
            throw new ConversionGroupException();
        }

        double amountPrefix = unit.getPrefix() == null ? this.amount : this.amount / unit.getPrefix().getFactor();

        double amountBase = amountPrefix / this.unit.getConversion();

        return Amount.builder()
                .amount(amountBase * unit.getConversion())
                .unit(unit)
                .build();
    }
}

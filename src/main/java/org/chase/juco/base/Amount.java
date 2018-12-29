package org.chase.juco.base;

import lombok.Builder;
import lombok.Data;
import org.chase.juco.exceptions.ConversionGroupException;

@Data
@Builder
public class Amount {
    private double amount;
    private Unit unit;

    public Amount convertTo(Unit unit) {
        if (!unit.getUnitgroup().equals(this.unit.getUnitgroup())) {
            throw new ConversionGroupException();
        }

        double amountBase = this.amount / this.unit.getConversion();

        return Amount.builder()
                .amount(amountBase * unit.getConversion())
                .unit(unit)
                .build();
    }
}

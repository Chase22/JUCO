package org.chase.juco.unit;

import lombok.*;
import org.chase.juco.amount.Prefix;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "unitgroup")
public class Unit {
    private UnitGroup unitgroup;

    private Prefix prefix;

    private boolean baseUnit;
    private String name;
    private String shorthand;

    private double conversion;

    public Unit withPrefix(Prefix prefix) {
        return Unit.builder()
                .baseUnit(baseUnit)
                .name(name)
                .shorthand(shorthand)
                .conversion(conversion)
                .prefix(prefix)
                .build();
    }
}

package org.chase.juco.base;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "unitgroup")
public class Unit {
    private UnitGroup unitgroup;

    private boolean baseUnit;
    private String name;
    private String shorthand;

    private double conversion;
}

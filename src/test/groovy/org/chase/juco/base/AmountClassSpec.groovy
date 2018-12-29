package org.chase.juco.base

import spock.lang.Specification

class AmountClassSpec extends Specification {

    public static final BigDecimal ERROR_MARGIN = 0.0001
    final UnitGroup someUnitGoup = Mock(UnitGroup)

    final Unit someBaseUnit = new Unit(name: "someBase", conversion: 1, baseUnit: true, unitgroup: someUnitGoup)
    final Unit someUnit = new Unit(name: "someUnit", conversion: 0.5, unitgroup: someUnitGoup)
    final Unit someOtherUnit = new Unit(name: "someOtherUnit", conversion: 2, unitgroup: someUnitGoup)

    def "ConvertTo() should return the correct amount for a conversion between a unit and the baseunit"() {
        given:
        Amount someAmount = new Amount(2, someUnit)

        expect:
        someAmount.convertTo(someBaseUnit).amount == 4
    }

    def "ConvertTo() should return the correct amount for a conversion between a unit and another unit"() {
        given:
        Amount someAmount = new Amount(2, someUnit)

        expect:
        someAmount.convertTo(someOtherUnit).amount == 8
    }

}

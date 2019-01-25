package org.chase.juco.amount

import org.chase.juco.exceptions.ConversionGroupException
import org.chase.juco.unit.Unit
import org.chase.juco.unit.UnitGroup
import spock.lang.Specification

class AmountClassSpec extends Specification {

    final UnitGroup someUnitGoup = Mock(UnitGroup)
    final UnitGroup someOtherUnitGoup = Mock(UnitGroup)


    final Unit someBaseUnit = new Unit(name: "someBase", conversionRate: 1, baseUnit: true, unitgroup: someUnitGoup)
    final Unit someUnit = new Unit(name: "someUnit", conversionRate: 0.5, unitgroup: someUnitGoup)
    final Unit someOtherUnit = new Unit(name: "someOtherUnit", conversionRate: 2, unitgroup: someUnitGoup)

    final Unit someOtherGroupUnit = new Unit(name: "OtherGroupUnit", conversionRate: 2, unitgroup: someOtherUnitGoup)

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

    def "ConvertTo() should throw an exception if 2 units are not in the same unitgroup"() {
        given:
        Amount someAmount = new Amount(2, someUnit)

        when:
        someAmount.convertTo(someOtherGroupUnit)

        then:
        thrown(ConversionGroupException)
    }
}

package org.chase.juco

import org.chase.juco.amount.Amount
import org.chase.juco.unit.Unit
import org.chase.juco.unit.UnitGroup
import org.chase.juco.unit.UnitRegistry
import spock.lang.Specification
import spock.lang.Unroll

class JucoServiceSpec extends Specification {
    public static final BigDecimal ERROR_MARGIN_PERCENT = 0.000001 //in %

    static UnitRegistry registry

    void setupSpec() {
        UnitRegistry.initialise()
        registry = UnitRegistry.instance
    }

    @Unroll
    def "Converting #givenAmount #fromUnit to #toUnit should result in #expectedAmount"(
            double givenAmount, String fromUnit, String toUnit, double expectedAmount
    ) {
        given:
        UnitGroup length = registry.getGroup("length").get()

        Unit from = length.getUnit(fromUnit).get()
        Unit to = length.getUnit(toUnit).get()

        Amount feetAmount = new Amount(givenAmount, from)

        when:
        double result = feetAmount.convertTo(to).getAmount()

        then:
        calculateErrorInPercent(result, expectedAmount) < ERROR_MARGIN_PERCENT

        where:
        givenAmount | fromUnit            | toUnit  || expectedAmount
        25          | "foot"              | "meter" || 7.62
        18          | "foot"              | "inch"  || 216
        1           | "astronomical unit" | "meter" || 149597870691d
        1           | "au"                | "meter" || 149597870691d

    }

    double calculateErrorInPercent(double actual, double expected) {
        return (Math.abs(actual-expected)/expected)*100
    }
}
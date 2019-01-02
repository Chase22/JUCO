package org.chase.juco.amount;

import lombok.Getter;

/**
 * Valid SI Unit Prefixes
 * @see <a href=https://en.wikipedia.org/wiki/Metric_prefix>https://en.wikipedia.org/wiki/Metric_prefix</a>
 */
@SuppressWarnings("unused")
@Getter
public enum Prefix {
    YOTTA(10E+24, "Y"),
    ZETTA(10E+21, "Z"),
    EXA(10E+18, "E"),
    PETA(10E+15, "P"),
    TERA(10E+12, "T"),
    GIGA(10E+9, "G"),
    MEGA(10E+6, "M"),
    KILO(10E+3, "k"),
    HECTO(10E+2, "h"),
    DECA(10E+1, "da"),
    DECI(10E-1, "d"),
    CECI(10E-2, "c"),
    MILLI(10E-3, "m"),
    MICRO(10E-6, "µ"),
    NANO(10E-9, "n"),
    PICO(10E-12, "p"),
    FEMTO(10E-15, "f"),
    ATTO(10E-18, "a"),
    ZEPTO(10E-21, "z"),
    YOCTO(10E-24, "y");

    Prefix(final Double factor, final String symbol) {
        this.factor = factor;
        this.symbol = symbol;
    }

    /**
     * The factor applied to the unit prefixed
     */
    private final Double factor;

    /**
     * The short symbol of the unit
     */
    private final String symbol;
}

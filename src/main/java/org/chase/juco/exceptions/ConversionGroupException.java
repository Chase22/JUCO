package org.chase.juco.exceptions;

public class ConversionGroupException extends ConversionException {
    public ConversionGroupException() {
        super("The units are not in the same UnitGroup");
    }
}

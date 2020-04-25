# JUCO
### Java Unit Converion

JUCO is an easy libary to convert units of measurement between each other.

## Concepts
### Units
A unit consists of a Name, a Shorthand and a conversion-rate to the base unit.
The conversion rate shows how much of this unit is used for one unit of the base unit.

For example, the base unit for the length unitGroup is meter. 
Feet would therefore have a conversionRate rate of 3.2808 since 1 meter = 3.2808 Feet

### Unitgroup
A unitGroup is a group of units that can be converted into each other using the set base unit. 
Each unit is converted into any other unit by first converting it into the base unit and then converting it into the 
given target unit <br>
For example, the Unitgroup <i>temperature</i> uses the SI Unit Kelvin. 

### Baseunit
Base units are the central unit of every group. Every other unit has a conversion rate towards the base unit. 
Every conversion can be executed in 2 steps

- Convert from start unit to base unit
- Convert from base unit to target unit

Currently all unit groups use the respective SI units as their base unit

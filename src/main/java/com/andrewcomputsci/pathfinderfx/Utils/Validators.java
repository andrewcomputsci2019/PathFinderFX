package com.andrewcomputsci.pathfinderfx.Utils;

/**
 * Validations utils, provides static helper methods that make validation of data easy
 */
public class Validators {

    /**
     * Checks if value is negative
     *
     * @param number integer value to be checked
     * @return true if value is negative
     */
    public static boolean isNegative(int number) {
        return number < 0;
    }
}

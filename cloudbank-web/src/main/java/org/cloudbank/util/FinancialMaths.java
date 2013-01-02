package org.cloudbank.util;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: jchrist
 * Date: 04.12.12
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class FinancialMaths {
    /**
     * Calculates the <code>n</code>'th root of <code>val</code> with given
     * precision where precision defines the number of decimal fractional
     * digits to have guaranteed in common with real root.
     * Returns the calculated <code>BigDecimal</code> with the given precision.
     * @param val the value for which to calculate the <code>n</code>'th root (<code>val>=0</code>)
     * @param n the root's order (<code>n>=2</code>)
     * @param precision the precision in decimal fractional digits (<code>precision>=0</code>)
     * @return the calculated <code>BigDecimal</code>
     */
    public static BigDecimal root(BigDecimal val, int n, int precision) {
        // return null if given incorrect parameters
        if ( n <= 1  ||  precision < 0  || val == null  ||  val.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        // return root of 0 or 1 immediately to make sure, that val does not equal 0 or 1
        if (val.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        if (val.compareTo(BigDecimal.ONE)  == 0) return BigDecimal.ONE;

        // define and initialize some neccessary variables
        final BigDecimal TWO = new BigDecimal(2);
        BigDecimal lowerBound, upperBound;

        // set up the lower and the upper bound
        if (val.compareTo(BigDecimal.ONE) > 0) {
            lowerBound = BigDecimal.ONE;
            upperBound = val;
        } else {
            lowerBound = val;
            upperBound = BigDecimal.ONE;
        }

        // set up the first candidate for the real root and it's nth power
        BigDecimal sq        = upperBound.add(lowerBound).divide(TWO);
        BigDecimal sqPowN    = sq.pow(n);

        // loop until lower and upper bound do have precision decimal fractional digits in common
        while (lowerBound.setScale(precision, BigDecimal.ROUND_HALF_UP).compareTo(upperBound.setScale(precision, BigDecimal.ROUND_HALF_UP)) != 0) {
            if (sqPowN.compareTo(val) > 0) {
                // lower the upper bound
                upperBound = sq;
                // set sq inside the new range
                sq = sq.add(lowerBound).divide(TWO);
            } else {
                // raise the lower bound
                lowerBound = sq;
                // set sq inside the new range
                sq = sq.add(upperBound).divide(TWO);
            }
            sqPowN = sq.pow(n);
            //System.out.println(sq); // uncomment to see the precisioning process
        }
        // return the root shortened to precision fractional digits and striped zeros
        return sq.setScale(precision, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
}

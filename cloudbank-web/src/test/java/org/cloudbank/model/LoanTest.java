package org.cloudbank.model;


import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;


/**
 * TODO JavaDoc
 */
public class LoanTest  {

    @Test
    public void testCalculateMonthlyPayment() throws Exception {
        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal("10000"));
        loan.setDuration(24);
        loan.setInterestRate(new BigDecimal(0.0899));
        loan.calculateMonthlyPayment();
        assertEquals(new BigDecimal("455.20"), loan.getMonthlyPayment().setScale(2, RoundingMode.HALF_UP));

    }
}

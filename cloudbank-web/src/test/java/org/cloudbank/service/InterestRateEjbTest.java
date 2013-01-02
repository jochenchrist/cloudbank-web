package org.cloudbank.service;

import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class InterestRateEjbTest {

    @EJB
    InterestRateEjb service;

    @Before
    public void setUp() throws Exception{
        service = new InterestRateEjb();
    }

    @Test
    public void testCalculateRate() throws Exception {
        BigDecimal result = service.calculateInterestRate(new BigDecimal("10000"), 12);
        assertEquals("Zins-Rate sollte stimmen", new BigDecimal("0.059"), result);

    }
}

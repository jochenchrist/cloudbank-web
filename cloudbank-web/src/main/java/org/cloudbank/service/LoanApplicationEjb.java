package org.cloudbank.service;

import org.cloudbank.model.LoanApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LoanApplicationEjb {

    private static Logger logger = LoggerFactory.getLogger(LoanApplicationEjb.class);

    @PersistenceContext
    private EntityManager em;

    public void saveLoanApplication(LoanApplication loanApplication) {
        logger.info("Save Loan Application to database");
        em.persist(loanApplication);
    }

}

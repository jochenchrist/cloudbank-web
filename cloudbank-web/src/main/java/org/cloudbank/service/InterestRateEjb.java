package org.cloudbank.service;

import org.cloudbank.model.Loan;
import org.drools.*;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import java.math.BigDecimal;


@Stateless
@Path("/interestRate")
public class InterestRateEjb {

    private static Logger logger = LoggerFactory.getLogger(InterestRateEjb.class);

    private static KnowledgeBase knowledgeBase = getInterestCalculationKnowledgeBase();

    /**
     * Calculates the monthly payment amount.
     *
     *
     * @param amount        The total amount of the loan
     * @param duration      Duration in month (includes the duration without payment)
     * @return              Monthly payment rate
     */
    @GET
    @Produces("text/plain")
    public BigDecimal calculateInterestRate(@QueryParam("amount") BigDecimal amount, @QueryParam("duration") int duration) {
        logger.debug("enter calculateInterestRate({}, {})", amount, duration);

        Loan loan = new Loan();
        loan.setLoanAmount(amount);
        loan.setDuration(duration);

        executeRules(loan);

        BigDecimal result = loan.getInterestRate();

        logger.debug("exit calculateInterestRate({})", result);
        return result;
    }


    public Loan calculateInterestRate(@QueryParam("loan") Loan loan) {
        logger.debug("enter calculateInterestRate({})", loan);

        executeRules(loan);

        logger.debug("exit calculateInterestRate({})", loan);
        return loan;
    }

    private void executeRules(Loan loan) {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);
        session.insert(loan);
        session.fireAllRules();
        session.dispose();
    }

    private static KnowledgeBase getInterestCalculationKnowledgeBase() {
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        DecisionTableConfiguration dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        dtableconfiguration.setInputType( DecisionTableInputType.XLS );
        knowledgeBuilder.add( ResourceFactory.newClassPathResource("interestCalculation.xls"), ResourceType.DTABLE, dtableconfiguration );

//        knowledgeBuilder.add( ResourceFactory.newClassPathResource("interestRate.drl"), ResourceType.DRL);
        if ( knowledgeBuilder.hasErrors() ) {
            logger.error(knowledgeBuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile interestRate.drl");
        }
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());

        return knowledgeBase;
    }

}

package org.cloudbank.web.page;

import org.cloudbank.model.Loan;
import org.cloudbank.model.LoanApplication;
import org.cloudbank.service.LoanApplicationEjb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
public class LoanApplicationController implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(LoanApplicationController.class);


    @ManagedProperty(value="#{loanCalculator.loan}")
    Loan loan;

    LoanApplication loanApplication;

    @EJB
    LoanApplicationEjb service;


    public LoanApplicationController() {
        loanApplication = new LoanApplication();
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }


    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }



    public String save() {
        logger.debug("Enter save()");

        loanApplication.setLoan(loan);

        service.saveLoanApplication(loanApplication);

        logger.debug("Exit save()");

        return "/success.xhtml";
    }



}
package org.cloudbank.web.page;

import org.cloudbank.model.Loan;
import org.cloudbank.service.InterestRateEjb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@ManagedBean
@SessionScoped
public class LoanCalculator implements Serializable {

    // Properties

    @Min(value = 500, message = "Der Wunschbetrag muss mindestens 500 EUR betragen.")
    private BigDecimal amount = new BigDecimal("5000");
    @Min(value = 6, message = "Die Laufzeit muss mindestens 6 Monate betragen.")
    @Max(value = 72, message = "Die Dauer darf maximal 72 Monate betragen.")
    private int dauer = 12;

    @Min(value = 0, message = "Bigge geben Sie ein gültiges Netto-Einkommen ein.")
    private BigDecimal nettoeinkommen = new BigDecimal("2000");

    private BigDecimal monatsbetrag;
    private BigDecimal zinssatz;


    @EJB
    private InterestRateEjb service;

    private static Logger logger = LoggerFactory.getLogger(LoanCalculator.class);


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMonatsbetrag() {
        return monatsbetrag;
    }

    public void setMonatsbetrag(BigDecimal monatsbetrag) {
        this.monatsbetrag = monatsbetrag;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public BigDecimal getNettoeinkommen() {
        return nettoeinkommen;
    }

    public void setNettoeinkommen(BigDecimal nettoeinkommen) {
        this.nettoeinkommen = nettoeinkommen;
    }

    public BigDecimal getZinssatz() {
        return zinssatz;
    }

    public void setZinssatz(BigDecimal zinssatz) {
        this.zinssatz = zinssatz;
    }

    public String berechnen() {
        logger.debug("enter berechnen()");

        Loan loan = getLoan();
        loan = service.calculateInterestRate(loan);
        loan.calculateMonthlyPayment();

        setMonatsbetrag(loan.getMonthlyPayment());
        getMonatsbetrag().setScale(2, RoundingMode.HALF_UP);
        setZinssatz(loan.getInterestRate());
        getMonatsbetrag().setScale(5, RoundingMode.HALF_DOWN);


        logger.debug("exit berechnen()");

        return "/index.xhtml";

    }

    public Loan getLoan() {
        Loan loan = new Loan();
        loan.setLoanAmount(getAmount());
        loan.setDuration(getDauer());
        loan.setCurrency("EUR");
        loan.setMonthlyIncome(getNettoeinkommen());
        return loan;

    }


    public String beantragen() {
        logger.debug("enter beantragen()");

        berechnen();

        logger.debug("exit beantragen()");

        return "/apply.xhtml";

    }








}

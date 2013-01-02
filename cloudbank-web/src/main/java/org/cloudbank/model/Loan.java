package org.cloudbank.model;

import org.cloudbank.util.FinancialMaths;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Loan implements Serializable {

    public static final int SCALE = 40;

    private BigDecimal loanAmount;
    private String currency = "EUR";
    private BigDecimal commission;
    private int duration;
    private BigDecimal monthlyIncome;
    // effective
    private BigDecimal interestRate;
    private BigDecimal monthlyPayment;

    private boolean monthlyPaymentDirty = true;

    /**
     * Berechnet bei nachsuessiger Zahlungsart
     */
    public void calculateMonthlyPayment() {

        if(loanAmount == null || duration <= 0 || interestRate == null ||
                interestRate.compareTo(BigDecimal.ZERO) < 0 )
        return;


        BigDecimal amount = loanAmount;
        amount.setScale(SCALE);
        if(commission != null) amount = amount.add(commission);

        // Um die Monatliche Verzinsung auszurechnen, muss die 12. Wurzel von q gezogen werden.
        BigDecimal q = BigDecimal.ONE.add(interestRate);
        BigDecimal qm = FinancialMaths.root(q, 12, SCALE);

        // 100000 * 1,01 ^ 5 * (1,01 -1 ) / (1,01^ - 1)
        setMonthlyPayment(amount.multiply(qm.pow(duration)).multiply(qm.subtract(BigDecimal.ONE)).divide((qm.pow(duration)).subtract(BigDecimal.ONE), SCALE, RoundingMode.HALF_UP));

    }


    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("Loan [amount=").append(loanAmount).append(", duration=").append(duration).append("]").append(", interestRate=").append(getInterestRate()).append("]").append(", monthlyPayment=").append(getMonthlyPayment()).append("]").toString();
    }

}

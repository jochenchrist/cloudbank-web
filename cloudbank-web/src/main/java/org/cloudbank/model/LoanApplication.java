package org.cloudbank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class LoanApplication implements Serializable {

    private long id;
    private Date datum;

    private Loan loan;

    private BigDecimal nettoeinkommen;
    private boolean selbststaendig;
    private boolean andereKredite;
    private BigDecimal andereKrediteRate;
    private String vorname;
    private String nachname;
    private String strasse;
    private String postleitzahl;
    private String ort;
    private String landIsoCode;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BigDecimal getNettoeinkommen() {
        return nettoeinkommen;
    }

    public void setNettoeinkommen(BigDecimal nettoeinkommen) {
        this.nettoeinkommen = nettoeinkommen;
    }

    public boolean isSelbststaendig() {
        return selbststaendig;
    }

    public void setSelbststaendig(boolean selbststaendig) {
        this.selbststaendig = selbststaendig;
    }

    public boolean isAndereKredite() {
        return andereKredite;
    }

    public void setAndereKredite(boolean andereKredite) {
        this.andereKredite = andereKredite;
    }

    public BigDecimal getAndereKrediteRate() {
        return andereKrediteRate;
    }

    public void setAndereKrediteRate(BigDecimal andereKrediteRate) {
        this.andereKrediteRate = andereKrediteRate;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLandIsoCode() {
        return landIsoCode;
    }

    public void setLandIsoCode(String landIsoCode) {
        this.landIsoCode = landIsoCode;
    }
}

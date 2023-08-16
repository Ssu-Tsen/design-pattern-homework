package org.example;

import lombok.Value;

import java.util.Date;
import java.util.List;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Value
public class Case {
    List<String> symptoms;
    Date caseTime;
    Prescription prescription;

    public Case(List<String> symptoms, Prescription prescription) {
        this.symptoms = symptoms;
        this.prescription = prescription;
        this.caseTime = new Date();
    }
}

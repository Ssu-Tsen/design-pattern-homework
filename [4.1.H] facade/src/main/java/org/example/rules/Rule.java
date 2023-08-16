package org.example.rules;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.*;

import java.util.List;

import static java.lang.String.*;
import static java.util.Collections.*;
import static org.example.Patient.Gender.*;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Setter
@NoArgsConstructor
public abstract class Rule {
    protected Rule next;

    public Rule(Rule next) {
        this.next = next;
    }

    public Prescription prescribe(List<String> symptoms, Patient patient) {
        if (match(symptoms, patient)) {
            return getPrescription();
        }
        if (next == null) {
            throw new IllegalStateException(format("No rule match the given symptoms: %s", join("," , symptoms)));
        }
        return next.prescribe(symptoms, patient);
    }
    protected abstract boolean match(List<String> symptoms, Patient patient);
    protected abstract Prescription getPrescription();

    public static void main(String[] args) {
        Rule rule = new CovidRule(new AttractiveRule(new SleepApneaSyndromeRule(null)));
        Patient patient = new Patient("A123456789", "岑寶寶", FEMALE, 18, 163, 48);
        Prescription prescription = rule.prescribe(singletonList("sneeze"), patient);
        System.out.println(prescription.getName());
    }
}

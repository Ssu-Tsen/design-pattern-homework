package org.example.rules;

import lombok.NoArgsConstructor;
import org.example.Patient;
import org.example.Prescription;
import org.example.rules.Rule;

import java.util.List;

import static java.util.Arrays.*;
import static java.util.Collections.*;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@NoArgsConstructor
public class AttractiveRule extends Rule {
    private final List<String> symptoms = singletonList("sneeze");

    public AttractiveRule(Rule next) {
        super(next);
    }

    @Override
    protected boolean match(List<String> symptoms, Patient patient) {
        return patient.getAge() == 18 && this.symptoms.equals(symptoms);
    }

    @Override
    protected Prescription getPrescription() {
        return new Prescription("青春抑制劑", "有人想你了 (專業學名：Attractive)",
                asList("假鬢角", "臭味"), "把假鬢角黏在臉的兩側，讓自己異性緣差一點，自然就不會有人想妳了。");
    }
}

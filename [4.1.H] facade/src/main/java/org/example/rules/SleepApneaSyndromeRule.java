package org.example.rules;

import lombok.NoArgsConstructor;
import org.example.Patient;
import org.example.Prescription;
import org.example.rules.Rule;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@NoArgsConstructor
public class SleepApneaSyndromeRule extends Rule {
    private final List<String> symptoms = singletonList("snore");

    public SleepApneaSyndromeRule(Rule next) {
        super(next);
    }

    @Override
    protected boolean match(List<String> symptoms, Patient patient) {
        double bmi = patient.getWeight() / (patient.getHeight()/100 * patient.getHeight()/100);
        return bmi > 26 && this.symptoms.equals(symptoms);
    }

    @Override
    protected Prescription getPrescription() {
        return new Prescription("打呼抑制劑", "睡眠呼吸中止症（專業學名：SleepApneaSyndrome）", singletonList("一捲膠帶"),
                "把嘴巴黏起來，讓自己睡不著，自然就不會打呼了。");
    }
}

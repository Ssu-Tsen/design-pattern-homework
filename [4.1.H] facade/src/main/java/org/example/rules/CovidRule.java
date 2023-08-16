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
public class CovidRule extends Rule {
    private final List<String> symptoms = asList("sneeze", "headache", "cough");

    public CovidRule(Rule next) {
        super(next);
    }

    @Override
    public boolean match(List<String> symptoms, Patient patient) {
        return this.symptoms.equals(symptoms);
    }

    @Override
    public Prescription getPrescription() {
        return new Prescription("清冠一號", "新冠肺炎（專業學名：COVID-19）",
                singletonList("新冠肺炎（專業學名：COVID-19）"), "將相關藥材裝入茶包裡，使用500 mL 溫、熱水沖泡悶煮1~3 分鐘後即可飲用。");
    }
}

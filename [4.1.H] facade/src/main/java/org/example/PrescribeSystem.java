package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.on.prescribed.actions.SaveToFile;
import org.example.on.prescribed.actions.SaveToPatientDatabase;
import org.example.prescribeSystem.PatientDatabase;
import org.example.prescribeSystem.Prescriber;
import org.example.rules.AttractiveRule;
import org.example.rules.CovidRule;
import org.example.rules.Rule;
import org.example.rules.SleepApneaSyndromeRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Thread.currentThread;
import static java.util.Arrays.*;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@RequiredArgsConstructor
public class PrescribeSystem {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Prescriber prescriber;
    private final PatientDatabase patientDatabase;
    private final SaveToPatientDatabase saveToPatientDatabase;
    private final Map<String, Rule> potentialDiseaseToRule = Map.of("COVID-19", new CovidRule(), "Attractive",
            new AttractiveRule(), "SleepApneaSyndrome", new SleepApneaSyndromeRule());

    public PrescribeSystem(String patientFile, String supportedDiseasesFile) throws IOException {
        this.patientDatabase = new PatientDatabase();
        this.prescriber = new Prescriber(patientDatabase, supportedDiseases(supportedDiseasesFile));
        this.saveToPatientDatabase = new SaveToPatientDatabase(patientDatabase);
        saveAllPatients(patientFile);
        prescriber.startPrescribing();
    }

    public void prescribe(String patientId, List<String> symptoms, String outputFile, Format format) {
        prescriber.demand(patientId, symptoms, asList(saveToPatientDatabase, new SaveToFile(outputFile, format)));
    }

    private void saveAllPatients(String jsonFile) throws IOException {
        var in = currentThread().getContextClassLoader().getResourceAsStream(jsonFile);
        List<Patient> patients = objectMapper.readValue(in, new TypeReference<>() {});
        patientDatabase.setPatients(patients);
        System.out.println("[Saved All Patients' Data.]");
    }

    private Rule supportedDiseases(String xmlFile) {
        Rule rule = null;
        try(var in = currentThread().getContextClassLoader().getResourceAsStream(xmlFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            String disease;
            while ((disease = reader.readLine()) != null) {
                if (potentialDiseaseToRule.containsKey(disease)) {
                    Rule newRule = potentialDiseaseToRule.get(disease);
                    newRule.setNext(rule);
                    rule = newRule;
                } else {
                    throw new IllegalArgumentException("Got an unknown disease name: " + disease);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[Set All Supported Diseases.]");
        return rule;
    }

    @Getter
    @AllArgsConstructor
    public enum Format {
        JSON(".json"),
        CSV(".csv");

        private final String fileExtension;
    }
}

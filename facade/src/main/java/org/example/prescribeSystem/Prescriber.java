package org.example.prescribeSystem;

import lombok.Getter;
import lombok.Setter;
import org.example.Case;
import org.example.Demand;
import org.example.Patient;
import org.example.Prescription;
import org.example.on.prescribed.actions.OnPrescribedAction;
import org.example.on.prescribed.actions.SaveToPatientDatabase;
import org.example.rules.AttractiveRule;
import org.example.rules.CovidRule;
import org.example.rules.Rule;
import org.example.rules.SleepApneaSyndromeRule;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.String.*;
import static java.lang.Thread.*;
import static java.util.Collections.*;
import static org.example.Patient.Gender.FEMALE;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Getter
@Setter
public class Prescriber {
    private PatientDatabase patientDatabase;
    private BlockingQueue<Demand> waitingList;
    private boolean isRunning;
    private Rule rule;

    public Prescriber(PatientDatabase database, Rule rule) {
        this.patientDatabase = database;
        this.rule = rule;
        waitingList = new LinkedBlockingQueue<>();
        this.isRunning = true;
    }

    public void startPrescribing() {
        Thread processingThread = new Thread(() -> {
            System.out.println("[Starting Prescribing...]");
            while (isRunning) {
                try {
                    Demand demand = waitingList.take();
                    prescribe(demand);
                } catch (InterruptedException ignored) {
                }
            }
        });
        processingThread.start();
    }

    public void demand(String patientId, List<String> symptoms, List<OnPrescribedAction> actions) {
        System.out.printf("[Added a Demand into Waiting List] patientId: %s, symptoms: %s.\n", patientId, join(", ", symptoms));
        waitingList.add(new Demand(patientId, symptoms, actions));
    }

    private void prescribe(Demand demand) throws InterruptedException {
        System.out.printf("=====================================\n[Prescribing...] patientId: %s.\n", demand.getPatientId());
        sleep(3000);
        Patient patient = patientDatabase.findById(demand.getPatientId());
        Prescription prescription = rule.prescribe(demand.getSymptoms(), patient);
        Case aCase = new Case(demand.getSymptoms(), prescription);
        System.out.printf("[Prescribed Done] prescription: %s, potentialDisease: %s.\n", prescription.getName(), prescription.getPotentialDisease());
        demand.getOnPrescribedActions().forEach(action -> action.onPrescribed(aCase, patient.getId()));
    }

    public static void main(String[] args) throws InterruptedException {
        Rule rule = new CovidRule(new AttractiveRule(new SleepApneaSyndromeRule(null)));
        Patient patient = new Patient("A123456789", "岑寶寶", FEMALE, 18, 163, 100);
        PatientDatabase database = new PatientDatabase(singletonList(patient));
        SaveToPatientDatabase saveToPatientDatabase = new SaveToPatientDatabase(database);
        Prescriber prescriber = new Prescriber(database, rule);
        prescriber.demand("A123456789", singletonList("sneeze"), singletonList(saveToPatientDatabase));
        sleep(2000);
        prescriber.demand("A123456789", singletonList("snore"), singletonList(saveToPatientDatabase));

        sleep(7000);
        patient = database.findById("A123456789");
        var cases = patient.getCases();
        cases.forEach(aCase -> System.out.println(aCase.getCaseTime()));
    }
}

package org.example.prescribeSystem;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Case;
import org.example.Patient;

import java.util.List;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDatabase {
    private List<Patient> patients;
    public Patient findById(String patientId) {
        return patients.stream()
                .filter(patient -> patient.getId().equals(patientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Patient not found."));
    }

    public void saveCase(String patientId, Case aCase) {
        Patient patient = findById(patientId);
        patient.addCase(aCase);
    }

    public void add(Patient patient) {
        patients.add(patient);
    }
}

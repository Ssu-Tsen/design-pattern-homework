package org.example.on.prescribed.actions;

import lombok.AllArgsConstructor;
import org.example.Case;
import org.example.prescribeSystem.PatientDatabase;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@AllArgsConstructor
public class SaveToPatientDatabase implements OnPrescribedAction {
    private final PatientDatabase patientDatabase;
    @Override
    public void onPrescribed(Case aCase, String patientId) {
        System.out.println("[Saving to Database]");
        patientDatabase.saveCase(patientId, aCase);
    }
}

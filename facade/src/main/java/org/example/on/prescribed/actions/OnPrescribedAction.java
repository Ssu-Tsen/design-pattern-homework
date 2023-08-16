package org.example.on.prescribed.actions;

import org.example.Case;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
public interface OnPrescribedAction {
    void onPrescribed(Case aCase, String patientId);
}

package org.example;

import lombok.Value;
import org.example.on.prescribed.actions.OnPrescribedAction;

import java.util.List;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Value
public class Demand {
    String patientId;
    List<String> symptoms;
    List<OnPrescribedAction> onPrescribedActions;
}

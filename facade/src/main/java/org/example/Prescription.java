package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Getter
@AllArgsConstructor
public class Prescription {
    @Size(min = 4, max = 30)
    private String name;
    @Size(min = 8, max = 100)
    private String potentialDisease;
    private List<String> medicines;
    private String usage;
}

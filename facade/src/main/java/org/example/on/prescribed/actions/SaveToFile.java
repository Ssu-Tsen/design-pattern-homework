package org.example.on.prescribed.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.Case;
import org.example.Prescription;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.example.PrescribeSystem.Format;
import static org.example.PrescribeSystem.Format.CSV;
import static org.example.PrescribeSystem.Format.JSON;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@AllArgsConstructor
@NoArgsConstructor
public class SaveToFile implements OnPrescribedAction {
    private ObjectMapper mapper = new ObjectMapper();
    private String fileName;
    private Format format;

    public SaveToFile(String fileName, Format format) {
        this.fileName = fileName;
        this.format = format;
    }

    @Override
    public void onPrescribed(Case aCase, String patientId) {
        String fullName = fileName + format.getFileExtension();
        File outputFile = new File(fullName);
        System.out.printf("[Saving to File] fileName: %s\n", fullName);

        try (FileWriter writer = new FileWriter(outputFile);
             CSVWriter csvWriter = new CSVWriter(writer)) {
            if (JSON == format){
                String jsonData = mapper.writeValueAsString(aCase);
                writer.write(jsonData);
            } else if (CSV == format) {
                String[] header = {"symptoms", "caseTime", "prescription"};
                String[] content = {join(",", aCase.getSymptoms()), valueOf(aCase.getCaseTime()),
                        mapper.writeValueAsString(aCase.getPrescription())};
                csvWriter.writeAll(asList(header, content));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var action = new SaveToFile("output", CSV);
        Case aCase = new Case(asList("打哈欠"), new Prescription("青春抑制劑", "有人想你了 (專業學名：Attractive)",
                asList("假鬢角", "臭味"), "把假鬢角黏在臉的兩側，讓自己異性緣差一點，自然就不會有人想妳了。"));
        action.onPrescribed(aCase, "A123456789");
    }
}

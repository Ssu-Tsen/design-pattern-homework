package org.example;

import java.io.IOException;

import static java.util.List.of;
import static org.example.PrescribeSystem.Format;

public class Main {

    public static void main(String[] args) throws IOException {
        PrescribeSystem prescribeSystem = new PrescribeSystem("patients.json", "diseases.txt");
        prescribeSystem.prescribe("A12345678", of("sneeze"), "lauren", Format.JSON);
        prescribeSystem.prescribe("B12345678", of("sneeze", "headache", "cough"), "johnny", Format.JSON);
        prescribeSystem.prescribe("C12345678", of("snore"), "winnie", Format.CSV);
    }
}
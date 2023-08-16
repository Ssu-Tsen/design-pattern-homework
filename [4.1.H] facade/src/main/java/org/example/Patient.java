package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import org.example.utils.ValidationUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

import static org.example.utils.ValidationUtils.validate;
import static org.example.utils.ValidationUtils.validateProperty;

//import static org.example.utils.ValidationUtils.*;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@Data
@NoArgsConstructor
public class Patient {
    @Pattern(regexp = "[A-Z][0-9]{8}")
    private String id;
    private String name;
    private Gender gender;
    @Min(1) @Max(180)
    private int age;
    @Min(1) @Max(500)
    private float height;
    @Min(1) @Max(500)
    private float weight;
    private final List<Case> cases = new ArrayList<>();

    public Patient(String id, String name, Gender gender, int age, float height, float weight) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;

        validate(this);
    }

    public void setId(String id) {
        this.id = id;
        validateProperty(this, "id");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
        validateProperty(this, "age");
    }

    public void setHeight(float height) {
        this.height = height;
        validateProperty(this, "height");
    }

    public void setWeight(float weight) {
        this.weight = weight;
        validateProperty(this, "weight");
    }

    public void addCase(Case aCase) {
        cases.add(aCase);
    }

    @Getter
    @AllArgsConstructor
    public enum Gender {
        MALE("male"),
        FEMALE("female");

        @JsonProperty
        private final String name;
    }
}

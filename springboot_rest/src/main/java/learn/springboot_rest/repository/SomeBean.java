package learn.springboot_rest.repository;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// -------------------------
@JsonIgnoreProperties(value = {"name"})
// Approach 2: Specify fields at class level. String "name" corresponds to field `name`
// Approach 1 is better because of we refactor fields, we have to rename the strings as well
// Approach 3: Dynamic filtering -> defining JsonFilter
@JsonFilter("SomeBeanFilter")
public class SomeBean {

    private String id;

    private String name;

    // I don't want to send this because this field is sensitive
    // Approach 1: I can annotate each field with @JsonIgnore
    @JsonIgnore
    // This will prevent sending this field when retrieved specific entity or list of entities
    private String password;

    private String hobby;

    private String food;

    private int age;
}

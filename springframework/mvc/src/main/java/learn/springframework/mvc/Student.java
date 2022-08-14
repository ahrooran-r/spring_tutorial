package learn.springframework.mvc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Student {

    // final values set by user
    private String firstName, lastName, country, language;
    private String[] os;

    // initial collection of values displayed for the selection by user
    // these can be hardcoded. But instead this makes things easy
    private HashMap<String, String> countryOptions;
    private List<String> languageOptions;
    private List<String> osOptions;

    public Student() {

        countryOptions = new HashMap<>(3);
        // populate countryOptions with key, value pairs
        // instead of hard coding them in JSP
        countryOptions.put("SL", "SriLanka");
        countryOptions.put("IN", "India");
        countryOptions.put("CH", "China");

        languageOptions = new LinkedList<>();
        languageOptions.add("Java");
        languageOptions.add("Python");
        languageOptions.add("JavaScript");
        languageOptions.add("HTML");

        osOptions = new LinkedList<>();
        osOptions.add("Windows");
        osOptions.add("Linus");
        osOptions.add("Mac");

    }

    public String[] getOs() {
        return os;
    }

    public void setOs(String[] os) {
        this.os = os;
    }

    public List<String> getOsOptions() {
        return osOptions;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getLanguageOptions() {
        return languageOptions;
    }

    public String getCountry() {
        return countryOptions.get(country);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashMap<String, String> getCountryOptions() {
        return countryOptions;
    }
}

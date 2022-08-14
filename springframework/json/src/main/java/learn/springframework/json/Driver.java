package learn.springframework.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Driver {

    public static void main(String[] args) {

        // how json jackson works

        try {

            // create an object mapper
            ObjectMapper mapper = new ObjectMapper();

            // read JSON from file and map to POJO
            Student s1 = mapper.readValue(
                    new File("E:\\Programs\\Tutorials\\Spring\\json-tutorial\\data\\sample-full.json"),
                    Student.class
            );

            // print values
            System.out.println(s1.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

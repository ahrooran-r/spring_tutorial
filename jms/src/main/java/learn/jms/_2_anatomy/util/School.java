package learn.jms._2_anatomy.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class School implements Serializable {
    String name;
    int peopleStudy;
    short teachers;
}

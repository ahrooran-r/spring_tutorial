package learn.er_modelling.entity.exercise_2;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String licenseNumber;

    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Customer customer;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cars")
    private List<Accident> accidents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Policy policy;
}

package learn.springframework.mvchibernate.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;

    @Column(name = "last_name")
    @NonNull
    private String lastName;

    @Column(name = "email")
    @NonNull
    private String email;
}

// Lombok: How to specify a one arg constructor?
// @RequiredArgsConstructor and @NonNull are two important keys to solve the problem above.
// Because @RequiredArgsConstructor creates a constructor with fields which are annotated by @NonNull annotation.

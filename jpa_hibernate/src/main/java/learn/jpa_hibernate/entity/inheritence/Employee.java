package learn.jpa_hibernate.entity.inheritence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
@NoArgsConstructor
@Getter
@Setter

// 1
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(name = "employee_type")

// 2
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

// 3
@Inheritance(strategy = InheritanceType.JOINED)

@Entity
public abstract class Employee {

    @Id
    // @GeneratedValue(strategy = GenerationType.TABLE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public Employee(String name) {
        this.name = name;
    }
}
*/

/*
For `InheritanceType.SINGLE_TABLE` only 1 table will be created regardless of number of child classes.
Each unique field in child class is represented in the same table and other fields are marked as `null`
For example:

    Employee fullTimeEmployee = new FullTimeEmployee("Panda", 10000);
    Employee partTimeEmployee = new PartTimeEmployee("Bear", 50);
    employeeRepository.saveEmployee(fullTimeEmployee);
    employeeRepository.saveEmployee(partTimeEmployee);

Now table would be created like this:
Dtype,              id,     name,   hourly_wage,    salary
PartTimeEmployee    1       Jill    50.00           null
FullTimeEmployee    2       Jack    null            10_000.00

This is unnecessarily complex. But it gets the job done.
There is also chances of invalid data -> no data integrity -> but this is high performant
SINGLE_TABLE is the default InheritanceType

If I want I can give a name to the `Dtype` column -> which is the discriminator column which says what data belongs
to what class.
This name assigning is done by @DiscriminatorColumn(name = "employee_type")
    name = `EmployeeType` will result in same name in the end. JPA automatically converts name from Java convention to
    database convention: EmployeeType -> employee_type

 */

/*
For `InheritanceType.TABLE_PER_CLASS`, there will be a class for each child class.
So for here, there will be 2 classes.

Tables would be created like this:

1. full_time_employee:

    id      name    salary
    1       panda   10000

2. part_time_employee

    id      name    hourly_wage
    2       bear    50

This is more efficient and performant.
But common columns are repeated -> waste of space.
    Think of 10 subclasses with super class having 100 columns
    Then for each subclass, there will be a table with 100 columns
And, if I want to see all employees, then it has to do a join.

NOTE: make sure to set generation strategy to: @GeneratedValue(strategy = GenerationType.TABLE)
https://stackoverflow.com/a/923523/10582056 -> But there will be performance problems
*/

/*
For `InheritanceType.JOINED`, there will be a class for each child class and super class.
So for here, there will be 3 classes.
Hence, there is a performance hit

Tables would be created like this:

1.  employee

    id      name
    1       panda
    2       bear

1. full_time_employee:

    id      salary
    1       10000

2. part_time_employee

    id      hourly_wage
    2       50

This fixes the waste of space issue with TABLE_PER_CLASS strategy.
and I can keep using `@GeneratedValue(strategy = GenerationType.IDENTITY)`
so this is more efficient too
But when retrieving all employees, there should be a join
*/

@NoArgsConstructor
@Getter
@Setter

@MappedSuperclass
// @Entity -> DO NOT ANNOTATE
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public Employee(String name) {
        this.name = name;
    }
}

/*

For @MappedSuperclass, there will be no table for Employee class. It is just a mapping.
Employee class will no longer considered to be an entity either:
So, DO NOT ANNOTATE `@MappedSuperclass` WITH `@Entity`

So for here, there will be 2 classes.

Tables would be created like this:

1. full_time_employee:

    id      name    salary
    1       panda   10000

2. part_time_employee:

    id      name    hourly_wage
    1       bear    50


This is similar to `InheritanceType.TABLE_PER_CLASS`. But there are some differences:
1. There is no formal relationship between part_time_employee and full_time_employee
2. Employee is not an entity anymore. It's just there as a showpiece.
3. Notice the IDs of 2 columns are not linked. Hence, they cannot be jointly queried.
    So they have to be queried as if they are separate entities.

This fixes the waste of space issue with TABLE_PER_CLASS strategy.
and I can keep using `@GeneratedValue(strategy = GenerationType.IDENTITY)`
so this is more efficient too
But when retrieving all employees, there should be a join
 */

/*
 *
 * if DATA INTEGRITY or DATA QUALITY matters -> go for JOINED type
 * if PERFORMANCE matters -> go for SINGLE_TABLE
 *
 * TABLE_PER_CLASS and @MappedSuperClass is not much usable
 *
 * */
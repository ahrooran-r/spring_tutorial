package learn.jpa_hibernate;

import learn.jpa_hibernate.repository.basics.CourseJpaRepository;
import learn.jpa_hibernate.repository.basics.PersonJdbcDao;
import learn.jpa_hibernate.repository.basics.PersonJpaRepository;
import learn.jpa_hibernate.repository.inheritence.EmployeeRepository;
import learn.jpa_hibernate.repository.relationship.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JpaHibernateApplication implements CommandLineRunner {

    @Autowired
    PersonJdbcDao personJdbcDao;

    @Autowired
    PersonJpaRepository personJpaRepository;

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaHibernateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("----------------------------------------------------------------------------------------------------------");
        // log.info("All users -> {}", personJdbcDao.findAll());
        // log.info("User 10_001 -> {}", personJdbcDao.findById(10_001));
        // log.info("User 10_001 -> {}", personJdbcDao.findByIdWithPersonRowMapper(10_001));
        // log.info("Delete user 10_001 -> {}", personJdbcDao.deleteById(10_001));
        // log.info("New user -> {}", personJdbcDao.insert(new PersonBean("Vlad", "India", new Date())));
        // log.info("New user -> {}", personJdbcDao.insert(new PersonBean(10_001, "Ranga", "Hyderabad", new Date())));
        // log.info("Update user 10_003 -> {}", personJdbcDao.update(new PersonBean(10003, "Vlad", "India", new Date())));

        // log.info("User 10_001 -> {}", personJpaRepository.findById(10_001));
        // log.info("All users -> {}", personJpaRepository.findAll());

        // log.info("Course 1 -> {}", courseJpaRepository.findById(1L));
        // log.info("New Course -> {}", courseJpaRepository.save(new Course("History")));

        // Course oldCourse = courseJpaRepository.findById(1L);
        // Course newCourse = new Course(oldCourse.getId(), "Arts");
        // log.info("Update Course -> {}", courseJpaRepository.save(newCourse));

        // log.info("Student: {}", studentRepository.findById(20001));
        // log.info("Saved Student with Passport: {}", studentRepository.saveWithPassport());

        // Employee fullTimeEmployee = new FullTimeEmployee("Panda", 10000);
        // Employee partTimeEmployee = new PartTimeEmployee("Bear", 50);
        //
        // employeeRepository.saveEmployee(fullTimeEmployee);
        // employeeRepository.saveEmployee(partTimeEmployee);
        //
        // // log.info("All employees = {}", employeeRepository.findAll());
        // log.info("All part time employees = {}", employeeRepository.findAllPartTimeEmployees());
        // log.info("All full time employees = {}", employeeRepository.findAllFullTimeEmployees());
    }
}

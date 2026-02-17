package rvt.StudentuReg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String personalCode;
    private String registrationTime;

    // Constructor for new student
    public Student(String firstName, String lastName, String email, String personalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalCode = personalCode;
        this.registrationTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Constructor for loading from CSV
    public Student(String firstName, String lastName, String email, String personalCode, String time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalCode = personalCode;
        this.registrationTime = time;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPersonalCode() { return personalCode; }
    public String getRegistrationTime() { return registrationTime; }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s", firstName, lastName, email, personalCode, registrationTime);
    }
}
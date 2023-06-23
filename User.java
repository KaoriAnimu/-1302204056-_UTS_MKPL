package org.telkom.university.code.smell;

import java.time.Year;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class User {
    private final String userId;
    private String programStudy;
    private String faculty;
    private int enrollmentYear;
    private String email;
    private String password;
    private String userName;
    private String gender;
    private String firstName;
    private String lastName;
    private String studentIdentifierNumber;

    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    public void setSchoolIdentifier(String programStudy, String faculty, int enrollmentYear) throws IllegalArgumentException, EnrollmentYearException {
        validateInput(programStudy, "Program study");
        validateInput(faculty, "Faculty");
        if (enrollmentYear <= 0 || enrollmentYear >= Integer.MAX_VALUE) {
            throw new EnrollmentYearException("Enrollment year should be a positive integer.");
        }
        this.programStudy = programStudy;
        this.faculty = faculty;
        this.enrollmentYear = enrollmentYear;
    }

    public void setSchoolAccount(String email, String password, String userName) throws IllegalArgumentException, EmptyInputException {
        validateInput(email, "Email");
        validateInput(password, "Password");
        validateInput(userName, "User name");
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public void setGeneralInformation(String firstName, String lastName, String gender, String studentIdentifierNumber) throws IllegalArgumentException, EmptyInputException {
        validateInput(firstName, "First name");
        validateInput(lastName, "Last name");
        validateInput(gender, "Gender");
        validateInput(studentIdentifierNumber, "Student identifier number");
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studentIdentifierNumber = studentIdentifierNumber;
    }

    public int calculateEnrollmentYear() {
        int currentYear = Year.now().getValue();
        return currentYear - this.enrollmentYear;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return password != null && pattern.matcher(password).matches();
    }

    public void updateProfile(String firstName, String lastName, String gender, String studentIdentifierNumber,
                              String programStudy, String faculty, int enrollmentYear, String email,
                              String password, String userName) throws Exception {
        if (studentIdentifierNumber.length() != 10 || !StringUtils.isNumeric(studentIdentifierNumber)) {
            throw new IllegalArgumentException("Student identifier number is not valid.");
        }
        boolean isValidEmail = isValidEmail(email);
        boolean isStrongPassword = isStrongPassword(password);
        this.setSchoolIdentifier(programStudy, faculty, enrollmentYear);
        this.setSchoolAccount(email, password, userName);
        this.setGeneralInformation(firstName, lastName, gender, studentIdentifierNumber);
        int calculatedYear = this.calculateEnrollmentYear();
        String emailStatus = isValidEmail ? "VALID" : "INVALID";
        String passwordStatus = isStrongPassword ? "STRONG" : "WEAK";
        if (emailStatus.equals("VALID") && passwordStatus.equals("STRONG")) {
            System.out.println("UPDATE COMPLETE!");
        } else if (emailStatus.equals("VALID") && passwordStatus.equals("WEAK")) {
            System.out.println("PLEASE USE A BETTER PASSWORD");
        } else if (emailStatus.equals("INVALID") && passwordStatus.equals("STRONG")) {
            System.out.println("PLEASE CHECK YOUR EMAIL");
        } else if (emailStatus.equals("INVALID") && passwordStatus.equals("WEAK")) {
            System.out.println("THIS IS A JOKE, RIGHT? PLEASE USE A VALID EMAIL AND A STRONG PASSWORD");
        }
    }

    private void validateInput(String input, String fieldName) throws IllegalArgumentException, EmptyInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new EmptyInputException(fieldName + " should not be null, empty, or blank.");
        }
    }
}

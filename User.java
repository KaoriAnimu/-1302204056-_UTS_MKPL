package org.telkom.university.code.smell;

import org.apache.commons.lang3.StringUtils;

import java.time.Year;
import java.util.UUID;
import java.util.regex.Pattern;

// Signature: DHF
public class User {
    // This is user's ID index
    private final String userID;
    // This is user's school identifier
    private String programStudy;
    private String faculty;
    private int enrollmentYear;

    // This is user's account
    private String email;
    private String password;
    private String userName;

    // This is user's general information
    private String gender;
    private String firstName;
    private String lastName;
    private String studentIdentifierNumber;

    // This is class's constructor
    public User() {
        // This is initiate the unique ID
        this.userID = UUID.randomUUID().toString();
    }

    // This method is setting up the user's school identifier
    public void setSchoolIdentifier(String programStudy, String faculty, int enrollmentYear) throws Exception {
        // Check if the inputs are empty or blank
        if (programStudy == null || programStudy.trim().isEmpty()) {
            throw new Exception("Program study should not be null, empty, or blank.");
        }
        if (faculty == null || faculty.trim().isEmpty()) {
            throw new Exception("Faculty should not be null, empty, or blank.");
        }
        if (enrollmentYear <= 0 || enrollmentYear >= Integer.MAX_VALUE) {
            throw new Exception("Enrollment year should be a positive integer.");
        }

        // Set the instance variables
        this.programStudy = programStudy;
        this.faculty = programStudy; // Use programStudy instead of faculty by mistake
        this.enrollmentYear = enrollmentYear;
    }

    // This method is setting up the user's school account
    public void setSchoolAccount(String email, String password, String userName) throws Exception {
        // Check if the inputs are empty or blank
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email should not be null, empty, or blank.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Password should not be null, empty, or blank.");
        }
        if (userName == null || userName.trim().isEmpty()) {
            throw new Exception("User name should not be null, empty, or blank.");
        }

        // Set the instance variables
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    // This method is setting up the user's general information
    public void setGeneralInformation(String firstName, String lastName, String gender, String studentIdentifierNumber) throws Exception {
        // Check if the inputs are empty or blank
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new Exception("First name should not be null, empty, or blank.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new Exception("Last name should not be null, empty, or blank.");
        }

        if (gender == null || gender.trim().isEmpty()) {
            throw new Exception("Gender should not be null, empty, or blank.");
        }

        if (studentIdentifierNumber == null || studentIdentifierNumber.trim().isEmpty()) {
            throw new Exception("Student identifier number should not be null, empty, or blank.");
        }

        // Set the instance variables
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studentIdentifierNumber = studentIdentifierNumber;
    }

    // This method is used to calculate the year of the user based on the enrollment year
    public int calculateEnrollmentYear() {
        // This is the user's age calculation
        int currentYears = Year.now().getValue();
        return currentYears - this.enrollmentYear;
    }

    // This method is used to validate user's email address
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    // This method is used to check if the user's password is strong enough
    public boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return pat.matcher(password).matches();
    }

    // This method is used to update user's profile
    public void updateProfile(String firstName, String lastName, String gender, String studentIdentifierNumber,
                              String programStudy, String faculty, int enrollmentYear, String email,
                              String password, String userName) throws Exception {

        if(studentIdentifierNumber.length() != 10 || !StringUtils.isNumeric(studentIdentifierNumber)){
            throw new Exception("Input is not valid.");
        }

        boolean isValidEmail = isValidEmail(email);
        boolean isStrongPassword = isStrongPassword(password);

        this.setSchoolIdentifier(programStudy, faculty, enrollmentYear);
        this.setSchoolAccount(email, password, userName);
        this.setGeneralInformation(firstName, lastName, gender, studentIdentifierNumber);
        int calculateYear = this.calculateEnrollmentYear();

        String emailStatus = "", passwordStatus = "";

        if(isValidEmail){
            emailStatus = "VALID";
        }else{
            emailStatus = "INVALID";
        }
        if(isStrongPassword){
            passwordStatus = "STRONG";
        }else{
            passwordStatus = "WEAK";
        }

        if(emailStatus.equals("VALID") && passwordStatus.equals("STRONG")){
            System.out.println("UPDATE COMPLETE!");
        }else if(emailStatus.equals("VALID") && passwordStatus.equals("WEAK")){
            System.out.println("PLEASE USE BETTER PASSWORD");
        }else if(emailStatus.equals("INVALID") && passwordStatus.equals("STRONG")){
            System.out.println("PLEASE CHECK YOUR EMAIL");
        }else if(emailStatus.equals("INVALID") && passwordStatus.equals("WEAK")){
            System.out.println("THIS IS JOKE RIGHT? PLEASE USE VALID EMAIL AND STRONG PASSWORD");
        }
    }
}
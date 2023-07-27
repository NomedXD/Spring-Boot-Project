package by.teachmeskills.project.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class User extends BaseEntity{
    @Email
    @NotNull
    private String mail;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$")
    @NotNull
    private String password;
    @Pattern(regexp = "[a-zA-Z ,.'-]+")
    @NotNull
    private String name;
    @Pattern(regexp = "[a-zA-Z ,.'-]+")
    @NotNull
    private String surname;
    @NotNull
    private LocalDate date;
    private Float currentBalance;
    private String mobile;
    private String street;
    private String accommodationNumber;
    private String flatNumber;

    public User() {

    }

    public User(int id, String mail, String password, String name, String surname, LocalDate date, float currentBalance, String mobile, String street, String accommodationNumber, String flatNumber) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.currentBalance = currentBalance;
        this.mobile = mobile;
        this.street = street;
        this.accommodationNumber = accommodationNumber;
        this.flatNumber = flatNumber;
    }

    public User(String mail, String password, String name, String surname, LocalDate date, float currentBalance){
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public String getMobile() {
        return mobile;
    }

    public String getStreet() {
        return street;
    }

    public String getAccommodationNumber() {
        return accommodationNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAccommodationNumber(String accommodationNumber) {
        this.accommodationNumber = accommodationNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }
}

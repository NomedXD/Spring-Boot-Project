package by.teachmeskills.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @NotNull(message = "Field is null validation error")
    @Email(message = "Field does not satisfy regexp")
    @Column(name = "mail", unique = true)
    private String mail;
    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$", message = "Field does not satisfy regexp")
    @Column(name = "password")
    private String password;
    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Column(name = "surname")
    private String surname;
    @NotNull(message = "Field is null validation error")
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "balance")
    private Float currentBalance;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "street")
    private String street;
    @Column(name = "accommodation_number")
    private String accommodationNumber;
    @Column(name = "flat_number")
    private String flatNumber;

    public User() {

    }

    public User(Integer id, String mail, String password, String name, String surname, LocalDate date, float currentBalance, String mobile, String street, String accommodationNumber, String flatNumber) {
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

    public Integer getId() {
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

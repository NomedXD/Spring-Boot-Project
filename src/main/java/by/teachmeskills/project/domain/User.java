package by.teachmeskills.project.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orders;

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
        /*
            В этом моменте не сильно разобрался. Если стоит на поле orders cascade = CascadeType.ALL, то при persist
            user в базу данных метод не вернет пользователя вместе с заказами(их у него нет, то есть размер списка = 0,
            в поле orders все еще будет null, поэтому здесь сразу и инициализирую это поле
         */
        this.orders = new ArrayList<>();
    }
}

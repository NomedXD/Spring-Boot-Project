package by.teachmeskills.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Size(min = 0, max = 45, message = "Out of validation bounds")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Field is null validation error")
    @Size(min = 0, max = 45, message = "Out of validation bounds")
    @Column(name = "imagepath")
    private String imagepath;
    @NotNull(message = "Field is null validation error")
    @Size(min = 0, max = 45, message = "Out of validation bounds")
    @Column(name = "sometext")
    private String sometext;

    public Category(){

    }

    public Category(int id, String name, String imagepath, String sometext) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.sometext = sometext;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getSometext() {
        return sometext;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setSometext(String sometext) {
        this.sometext = sometext;
    }
}

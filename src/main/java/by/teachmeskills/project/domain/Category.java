package by.teachmeskills.project.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Category extends BaseEntity{
    @NotNull
    @Pattern(regexp = "[a-zA-Z ,.'-]+")
    @Size(min = 0, max = 45)
    private String name;
    @NotNull
    @Size(min = 0, max = 45)
    private String imagepath;
    @NotNull
    @Size(min = 0, max = 45)
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

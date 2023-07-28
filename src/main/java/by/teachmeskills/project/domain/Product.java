package by.teachmeskills.project.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class Product extends BaseEntity{
    @NotNull
    @Pattern(regexp = "[a-zA-Z ,.'-]+")
    @Size(min = 0, max = 45)
    private String name;
    @NotNull
    @Size(min = 0, max = 45)
    private String imagepath;
    @NotNull
    private String description;
    @NotNull
    @PositiveOrZero
    private int categoryid;
    @NotNull
    @Positive
    private float price;

    public Product(){

    }

    public Product(int id, String name, String imagepath, String description, int categoryid, float price) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.description = description;
        this.categoryid = categoryid;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

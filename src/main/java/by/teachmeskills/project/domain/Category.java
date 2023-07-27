package by.teachmeskills.project.domain;

public class Category extends BaseEntity{
    private String name;
    private String imagepath;
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

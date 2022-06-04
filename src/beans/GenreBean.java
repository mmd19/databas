package beans;

import java.util.ArrayList;

public class GenreBean {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String pattern = "Namn: %s";
        String returnValue = String.format(pattern, this.name);

        return returnValue;
    }

    public String toJson() {
        String pattern = "{ \"Name\": \"%s\" }";
        String returnValue = String.format(pattern, this.name);

        return returnValue;
    }
}
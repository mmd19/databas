package beans;

public class ActorBean {
    private int id;
    private String name;
    private String country;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        String pattern = "Namn: %s, Land: %s";
        String returnValue = String.format(pattern, this.name, this.country);

        return returnValue;
    }

    public String toJson() {
        String pattern = "{ \"Namn\": \"%s\", \"Land\": \"%s\" }";
        String returnValue = String.format(pattern, this.name, this.country);

        return returnValue;
    }
}
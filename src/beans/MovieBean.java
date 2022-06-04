package beans;

public class MovieBean {
    private String name;
    private String releaseDate;
    private String length;
    private String genre;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String toString() {
        String pattern = "Namn: %s, releasedate: %s, length: %s, genre: %s";
        String returnValue = String.format(pattern, this.name);

        return returnValue;
    }

    public String toJson() {
        String pattern = "{ \"Name\": \"%s\", \"releasedate\": \"%s\", \"length\": \"%s\", \"genre\": \"%s\" }";
        String returnValue = String.format(pattern, this.name, this.releaseDate, this.length, this.genre);

        return returnValue;
    }
}
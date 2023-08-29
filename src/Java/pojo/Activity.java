package Java.pojo;

public class Activity {
    private String creator;
    private String name;
    private String location;
    private String content;
    private String time;

    public Activity() {

    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Activity(String creator, String name, String location, String content, String time) {
        this.creator = creator;
        this.name = name;
        this.location = location;
        this.content = content;
        this.time = time;
    }
}

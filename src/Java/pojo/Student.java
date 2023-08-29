package Java.pojo;

public class Student {
    private String name="";
    private String num="";
    private String job="";
    private String gender="";

    public Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Student(String name, String num, String job, String gender) {
        this.name = name;
        this.num = num;
        this.job = job;
        this.gender = gender;
    }
}

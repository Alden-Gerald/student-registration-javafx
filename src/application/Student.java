package application;

public class Student {
    private String studentId;
    private String name;
    private String major;
    private String email;
    private String phone;

    public Student(
            String studentId,
            String name,
            String major,
            String email,
            String phone) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.email = email;
        this.phone = phone;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
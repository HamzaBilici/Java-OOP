package entity.concrete;

import entity.Reader;
import entity.utils.ValidationUtil;

public class Student extends Reader {

    private int schoolNumber;

    public Student(String name, int schoolNumber) {
        super(name);
        this.setSchoolNumber(schoolNumber);
    }

    public Student(String name, boolean isVerified, int schoolNumber) {
        super(name, isVerified);
        this.setSchoolNumber(schoolNumber);
    }

    public int getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(int schoolNumber) {
        ValidationUtil.requirePossitive((long)schoolNumber,"School number can not be lower than 0");
        this.schoolNumber = schoolNumber;
    }


    @Override
    public String toString() {
        return "Student{" +
                super.toString()
                +
                "schoolNumber=" + schoolNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return  this.getPersonID().equals(student.getPersonID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

package entity.concrete;

import entity.Reader;
import entity.utils.ValidationUtil;

import java.util.UUID;

public class Student extends Reader {

    public Student(String name, String address, String phoneNo) {
        super(name, address, phoneNo);
    }

    public Student(String name, String address, String phoneNo, boolean isVerified) {
        super(name, address, phoneNo, isVerified);
    }

    public Student(String name, String address, UUID personID, String phoneNo) {
        super(name, address, personID, phoneNo);
    }

    public Student(String name, String address, UUID personID, String phoneNo, boolean isVerified) {
        super(name, address, personID, phoneNo, isVerified);
    }

    @Override
    public String toString() {
        return super.toString()
                + " Student { " +
                " } ";
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

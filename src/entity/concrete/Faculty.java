package entity.concrete;

import entity.Reader;
import entity.utils.ValidationUtil;

import java.util.Objects;

public class Faculty extends Reader {
    private int facultyNo;


    public Faculty(String name, int facultyNo) {
        super(name);
        this.setFacultyNo(facultyNo);
    }

    public Faculty(String name, boolean isVerified, int facultyNo) {
        super(name, isVerified);
        this.setFacultyNo(facultyNo);
    }

    public int getFacultyNo() {
        return facultyNo;
    }

    public void setFacultyNo(int facultyNo) {
        ValidationUtil.requirePossitive((long)facultyNo,"Faculty number can not be lower than 0");
        this.facultyNo = facultyNo;
    }

    @Override
    public String toString() {
        return "Faculty{"  +
                super.toString()
                +
                "facultyNo=" + facultyNo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Faculty faculty = (Faculty) o;
       return this.getPersonID().equals(faculty.getPersonID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

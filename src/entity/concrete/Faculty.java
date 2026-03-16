package entity.concrete;

import entity.Reader;
import entity.utils.ValidationUtil;

import java.util.Objects;
import java.util.UUID;

public class Faculty extends Reader {

    public Faculty(String name, String address, String phoneNo) {
        super(name, address, phoneNo);
    }

    public Faculty(String name, String address, String phoneNo, boolean isVerified) {
        super(name, address, phoneNo, isVerified);
    }

    public Faculty(String name, String address, UUID personID, String phoneNo) {
        super(name, address, personID, phoneNo);
    }

    public Faculty(String name, String address, UUID personID, String phoneNo, boolean isVerified) {
        super(name, address, personID, phoneNo, isVerified);
    }


    @Override
    public String toString() {
        return super.toString() + " Faculty { "
                +
                " } ";
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

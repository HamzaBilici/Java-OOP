package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.utils.ValidationUtil;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MemberRecord {
    private UUID memberID;
    //private String type;
    private Date dateOfMembership;
    private int maxBookLimit;
    private Set<UUID> issuedBooksID;
    //private Reader member;
    private String name,address;
    private int phoneNo;






    private Library library = Library.getInstance();


    public Reader getMember() {
        return library.getMember(this.memberID);
    }

    public void addBookIssued(Book book) {

        ValidationUtil.requireNoNull(book, "book can not be null");
        this.addBookIssued(book.getBook_ID());
    }

    public void addBookIssued(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "uuid can not be null");
        issuedBooksID.add(uuid);
    }


    public void removeBookIssued(Book book) {

        ValidationUtil.requireNoNull(book, "book can not be null");
        this.removeBookIssued(book.getBook_ID());
    }

    public void removeBookIssued(UUID uuid) {
        ValidationUtil.requireNoNull(uuid, "uuid can not be null");
        issuedBooksID.remove(uuid);
    }


    public UUID getMemberID() {
        return memberID;
    }

    public Date getDateOfMembership() {
        return dateOfMembership;
    }

    public int getMaxBookLimit() {
        return maxBookLimit;
    }

    public Set<UUID> getIssuedBooksID() {
        return issuedBooksID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNo() {
        return phoneNo;
    }


    public void setMemberID(UUID memberID) {
        ValidationUtil.requireNoNull(memberID,"MemberID can not be null");
        this.memberID = memberID;
    }

    public void setDateOfMembership(Date dateOfMembership) {
        ValidationUtil.requireNoNull(memberID,"Membership Date can not be null");
        this.dateOfMembership = dateOfMembership;
    }

    public void setMaxBookLimit(int maxBookLimit) {

        ValidationUtil.requireNoNull(memberID,"Max Book Limit can not be null");
        this.maxBookLimit = maxBookLimit;
    }

    public void setIssuedBooksID(Set<UUID> issuedBooksID) {

        ValidationUtil.requireNoNull(issuedBooksID,"Issued Books ID can not be null");
        this.issuedBooksID = issuedBooksID;
    }

    public void setName(String name) {

        ValidationUtil.requireNoNull(issuedBooksID,"name can not be null");
        this.name = name;
    }

    public void setAddress(String address) {

        ValidationUtil.requireNoNull(issuedBooksID,"address can not be null");
        this.address = address;
    }

    public void setPhoneNo(int phoneNo) {

        ValidationUtil.requireNoNull(issuedBooksID,"phoneNo can not be null");
        this.phoneNo = phoneNo;
    }


    @Override
    public String toString() {
        return "MemberRecord{" +
                "phoneNo=" + phoneNo +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", dateOfMembership=" + dateOfMembership +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(o==this) return true;
        ValidationUtil.requireNoNull(o,"Compared object can not be null");
        ValidationUtil.requireSameTypeOfObject(o,this,"Compared objects has to be from same classes");
        MemberRecord that = (MemberRecord) o;
        return Objects.equals(memberID, that.memberID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberID);
    }
}

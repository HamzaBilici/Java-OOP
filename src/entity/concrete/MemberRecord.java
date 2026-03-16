package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.enums.MemberRecordStatus;
import entity.interfaces.IMemberRecord;
import entity.utils.ValidationUtil;

import java.util.Objects;
import java.util.UUID;

public class MemberRecord implements IMemberRecord {

    private UUID memberRecordID;
    private Reader relatedReader;
    private Book relatedBook;
    private MemberRecordStatus memberRecordStatus;
    private float paidPrice;

    //private Reader member;


    public MemberRecord(Reader relatedReader, Book relatedBook) {
        this(UUID.randomUUID(), relatedReader, relatedBook);
    }

    public MemberRecord(Reader relatedReader, Book relatedBook, MemberRecordStatus memberRecordStatus) {
        this(UUID.randomUUID(), relatedReader, relatedBook, memberRecordStatus);
    }

    public MemberRecord(UUID memberRecordID, Reader relatedReader, Book relatedBook) {
        this(memberRecordID, relatedReader, relatedBook, MemberRecordStatus.WAITING);
    }

    public MemberRecord(UUID memberRecordID, Reader relatedReader, Book relatedBook, MemberRecordStatus memberRecordStatus) {
        this.setMemberRecordID(memberRecordID);
        this.setRelatedReader(relatedReader);
        this.setRelatedBook(relatedBook);
        this.setMemberRecordStatus(memberRecordStatus);
        this.setPaidPrice(0);
    }

    @Override
    public float payBill() {
        this.setMemberRecordStatus(MemberRecordStatus.DONE);
        return getRelatedBook().getPrice();
    }


    public UUID getMemberRecordID() {
        return memberRecordID;
    }

    public Reader getRelatedReader() {
        return relatedReader;
    }

    public Book getRelatedBook() {
        return relatedBook;
    }

    public float getPaidPrice() {
        return paidPrice;
    }

    public MemberRecordStatus getMemberRecordStatus() {
        return memberRecordStatus;
    }

    public void setMemberRecordID(UUID memberRecordID) {
        ValidationUtil.requireNoNull(memberRecordID, "MemberRecordID can not be null");
        this.memberRecordID = memberRecordID;
    }

    public void setRelatedReader(Reader relatedReader) {

        ValidationUtil.requireNoNull(memberRecordID, "relatedReader can not be null");
        this.relatedReader = relatedReader;
    }

    public void setRelatedBook(Book relatedBook) {

        ValidationUtil.requireNoNull(memberRecordID, "relatedBook can not be null");
        this.relatedBook = relatedBook;
    }



    public void setMemberRecordStatus(MemberRecordStatus memberRecordStatus) {
        ValidationUtil.requireNoNull(memberRecordID, "MemberRecordStatus can not be null");
        this.memberRecordStatus = memberRecordStatus;

    }
    public void setPaidPrice(float paidPrice) {
        ValidationUtil.requirePossitive((long)paidPrice,"paidPrice can not be lower than 0");
        this.paidPrice = paidPrice;
    }

    @Override
    public String toString() {
        return " MemberRecord { " +
                " memberRecordID = " + memberRecordID +
                ", relatedReader = " + relatedReader +
                ", relatedBook = " + relatedBook +
                " } ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        ValidationUtil.requireIsInstanceOf(o, MemberRecord.class, "Compared object has to be instance of MemberRecord");
        ValidationUtil.requireNoNull(o, "Compared object can not be null");
        MemberRecord that = (MemberRecord) o;
        return Objects.equals(memberRecordID, that.memberRecordID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberRecordID);
    }
}

package entity.concrete;

import entity.Book;
import entity.Reader;
import entity.enums.MemberRecordStatus;
import entity.interfaces.Bookable;
import entity.interfaces.IMemberRecord;
import entity.utils.ValidationUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class MemberRecord implements IMemberRecord {

    private UUID memberRecordID;
    /* private Reader relatedReader;
     private Book relatedBook;*/
    private UUID libraryBookID;
    private MemberRecordStatus memberRecordStatus;
    private float paidPrice;

    //private Reader member;


    public MemberRecord(/*Reader relatedReader, Book relatedBook,*/UUID libraryBookID) {
        this(UUID.randomUUID(), libraryBookID);
    }

    public MemberRecord(/*Reader relatedReader, Book relatedBook,*/UUID libraryBookID, MemberRecordStatus memberRecordStatus) {
        this(UUID.randomUUID(), libraryBookID, /*relatedReader, relatedBook,*/ memberRecordStatus);
    }

    public MemberRecord(UUID memberRecordID, UUID libraryBookID/*, Reader relatedReader, Book relatedBook*/) {
        this(memberRecordID, libraryBookID/*, relatedReader, relatedBook*/, MemberRecordStatus.WAITING);
    }

    public MemberRecord(UUID memberRecordID, UUID libraryBookID, /*Reader relatedReader, Book relatedBook,*/ MemberRecordStatus memberRecordStatus) {
        this.setMemberRecordID(memberRecordID);
       /* this.setRelatedReader(relatedReader);
        this.setRelatedBook(relatedBook);*/
        this.setMemberRecordStatus(memberRecordStatus);
        this.setPaidPrice(1);
        setLibraryBookID(libraryBookID);
    }

    @Override
    public float payBill() {
        this.setMemberRecordStatus(MemberRecordStatus.DONE);
        return getPaidPrice();
    }


    public UUID getMemberRecordID() {
        return memberRecordID;
    }

  /*  public Optional<Reader> getRelatedReader() {
        Optional<MemberRecord> libraryRecord= Library.getInstance().getMemberRecords().stream().filter(memberRecord -> memberRecord.getMemberRecordID().equals(this.memberRecordID)).findFirst();
        if(libraryRecord.isPresent()){

        }
    }
/*
    public Book getRelatedBook() {
        return relatedBook;
    }*/

    public float getPaidPrice() {
        return paidPrice;
    }

    public MemberRecordStatus getMemberRecordStatus() {
        return memberRecordStatus;
    }

    public UUID getLibraryBookID() {
        return libraryBookID;
    }

    public Optional<Book> getRelatedBook() {
        return Optional.of(Library.getInstance().getBooks().get(libraryBookID));
    }

    public Bookable getCurrentOwner() {
        return getRelatedBook().get().getOwner();
    }

    public void setLibraryBookID(UUID libraryBookID) {
        ValidationUtil.requireNoNull(memberRecordID, "libraryBookID can not be null");
        this.libraryBookID = libraryBookID;
    }

    public void setMemberRecordID(UUID memberRecordID) {
        ValidationUtil.requireNoNull(memberRecordID, "MemberRecordID can not be null");
        this.memberRecordID = memberRecordID;
    }

 /*   public void setRelatedReader(Reader relatedReader) {

        ValidationUtil.requireNoNull(memberRecordID, "relatedReader can not be null");
        this.relatedReader = relatedReader;
    }

    public void setRelatedBook(Book relatedBook) {

        ValidationUtil.requireNoNull(memberRecordID, "relatedBook can not be null");
        this.relatedBook = relatedBook;
    }*/


    public void setMemberRecordStatus(MemberRecordStatus memberRecordStatus) {
        ValidationUtil.requireNoNull(memberRecordID, "MemberRecordStatus can not be null");
        this.memberRecordStatus = memberRecordStatus;

    }

    public void setPaidPrice(float paidPrice) {
        ValidationUtil.requirePossitive((long) paidPrice, "paidPrice can not be lower than 0");
        this.paidPrice = paidPrice;
    }

    @Override
    public String toString() {
        if (getRelatedBook().isEmpty()) return "error text";
        return " MemberRecord { " +
                " memberRecordID = " + memberRecordID +
                ", relatedBook = " + getRelatedBook().get().getName() +
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

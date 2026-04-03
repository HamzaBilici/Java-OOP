package entity.interfaces;

import entity.Book;
import entity.Reader;
import entity.concrete.MemberRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILibrarian {
    public Book searchBook(UUID uuid);
    public void verifyMember(Reader reader);
    public Optional<MemberRecord> issueBook(UUID uuid, Reader reader);
    public void calculateFine(MemberRecord memberRecord);
    public MemberRecord createBill(UUID bookID);
    public void returnBook(MemberRecord memberRecord, Reader reader);
}

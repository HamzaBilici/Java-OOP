package entity.enums;

public enum MemberRecordStatus {
    WAITING("Book rented and waiting for it return"),
    DONE("Book taken back and paid back");

    private final String explanation;

    MemberRecordStatus(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }
}

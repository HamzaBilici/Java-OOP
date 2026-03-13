package entity.enums;

public enum BookStatus {
   // Available , CheckedOut, InProcess ,  Missing
    AVAILABLE("Available (on shelf)"),
    CHECKEDOUT("Checked Out/Loaned (due on a specific date)"),
    INPROCESS("In Process (being cataloged/repaired)"),
    MISSING("Missing/On Search");

    private String status;

    private BookStatus(String status){
        this.status=status;
    }
}

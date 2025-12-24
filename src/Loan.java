import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private final int itemId;
    private final LocalDate borrowedOn;
    private final LocalDate dueOn;

    public Loan(int itemId, LocalDate borrowedOn, int loanDays) {
        this.itemId = itemId;
        this.borrowedOn = borrowedOn;
        this.dueOn = borrowedOn.plusDays(Math.max(1, loanDays));
    }

    public int getItemId() { return itemId; }
    public LocalDate getBorrowedOn() { return borrowedOn; }
    public LocalDate getDueOn() { return dueOn; }
    public boolean isOverdue(LocalDate today) { return today.isAfter(dueOn); }
    public long daysOverdue(LocalDate today) { return isOverdue(today) ? ChronoUnit.DAYS.between(dueOn, today) : 0; }

    @Override
    public String toString() { return "Loan[item=" + itemId + ", borrowed=" + borrowedOn + ", due=" + dueOn + "]"; }
}

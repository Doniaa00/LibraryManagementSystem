import java.time.LocalDate;
import java.util.*;

public class Member {
    private final int id;
    private final String name;
    private final Map<Integer, Loan> activeLoans = new HashMap<>();
    private final int maxSimultaneousLoans;

    public Member(int id, String name) { this(id, name, 5); }
    public Member(int id, String name, int maxSimultaneousLoans) {
        this.id = id;
        this.name = (name == null ? "Member#" + id : name.trim());
        this.maxSimultaneousLoans = Math.max(1, maxSimultaneousLoans);
    }
    public Member(int id) { this(id, "Member#" + id); }

    public int getId() { return id; }
    public String getName() { return name; }
    public Collection<Loan> getActiveLoans() { return activeLoans.values(); }
    public int getActiveLoanCount() { return activeLoans.size(); }
    public int getMaxSimultaneousLoans() { return maxSimultaneousLoans; }

    public void addLoan(Loan loan) {
        if (activeLoans.size() >= maxSimultaneousLoans) throw new IllegalStateException("Max loans reached");
        activeLoans.put(loan.getItemId(), loan);
    }

    public boolean returnLoan(int itemId) { return activeLoans.remove(itemId) != null; }

    public List<Loan> getOverdueLoans(LocalDate today) {
        List<Loan> overdue = new ArrayList<>();
        for (Loan l : activeLoans.values()) if (l.isOverdue(today)) overdue.add(l);
        return overdue;
    }

    @Override
    public String toString() { return "Member[" + id + "] " + name + " loans=" + activeLoans.keySet(); }
}

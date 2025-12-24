import java.time.LocalDate;
import java.util.*;

public class Library {
    private final List<Item> items = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private int nextItemId = 1;
    private int nextMemberId = 1;

    public int addBook(String title, String author) { 
        int id = nextItemId++; items.add(new Book(id, title, author)); return id; 
    }
    public int addBook(String title) { return addBook(title, "Unknown"); }
    public int addReferenceBook(String title, String author) { 
        int id = nextItemId++; items.add(new ReferenceBook(id, title, author)); return id; 
    }
    public int addMagazine(String title, int issue) { 
        int id = nextItemId++; items.add(new Magazine(id, title, issue)); return id; 
    }
    public int addAudiobook(String title, String narrator, int mins) { 
        int id = nextItemId++; items.add(new Audiobook(id, title, narrator, mins)); return id; 
    }
    public int addMember(String name) { 
        int id = nextMemberId++; members.add(new Member(id, name)); return id; 
    }
    public int addMember() { return addMember("Member#" + nextMemberId); }

    public Item getItem(int id) { for (Item i : items) if (i.getId()==id) return i; return null; }
    public Member getMember(int id) { for (Member m : members) if (m.getId()==id) return m; return null; }
    public List<Item> getAllItems() { return new ArrayList<>(items); }
    public List<Member> getAllMembers() { return new ArrayList<>(members); }

    public List<Item> getAvailableItems() { 
        List<Item> list = new ArrayList<>(); 
        for (Item i : items) if (i.isAvailable()) list.add(i); 
        return list; 
    }

    public List<Item> searchItemsByTitle(String q) {
        q = (q==null?"":q.toLowerCase().trim()); 
        List<Item> found = new ArrayList<>(); 
        for (Item i : items) if (i.getTitle().toLowerCase().contains(q)) found.add(i); 
        return found; 
    }

    public void borrowItem(int memberId, int itemId) throws MemberNotFoundException, ItemNotFoundException, BookNotAvailableException, MaxLoansExceededException {
        Member m = getMember(memberId); if (m==null) throw new MemberNotFoundException("Member not found");
        Item i = getItem(itemId); if (i==null) throw new ItemNotFoundException("Item not found");
        if (!i.canBeBorrowed() || !i.isAvailable()) throw new BookNotAvailableException("Item not available");
        if (m.getActiveLoanCount()>=m.getMaxSimultaneousLoans()) throw new MaxLoansExceededException("Max loans reached");
        m.addLoan(new Loan(itemId, LocalDate.now(), i.getDefaultLoanDays()));
        i.setAvailable(false);
    }

    public boolean returnItem(int memberId, int itemId) throws MemberNotFoundException, ItemNotFoundException {
        Member m = getMember(memberId); if (m==null) throw new MemberNotFoundException("Member not found");
        Item i = getItem(itemId); if (i==null) throw new ItemNotFoundException("Item not found");
        boolean ok = m.returnLoan(itemId); if (ok && i.canBeBorrowed()) i.setAvailable(true); return ok;
    }

    public List<Loan> getLoansForMember(int memberId) throws MemberNotFoundException {
        Member m = getMember(memberId); if (m==null) throw new MemberNotFoundException("Member not found"); 
        return new ArrayList<>(m.getActiveLoans());
    }

    public List<String> getOverdueReport() {
        List<String> report = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Member m : members) for (Loan l : m.getOverdueLoans(today)) {
            Item it = getItem(l.getItemId());
            report.add("Member " + m.getId() + " (" + m.getName() + ") - Item " + it.getId() + " '" + it.getTitle() + "' overdue by " + l.daysOverdue(today) + " day(s). Due: " + l.getDueOn());
        }
        return report;
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryGUI extends JFrame {

    private final Library library = new Library();

    private final DefaultTableModel itemsModel = new DefaultTableModel();
    private final DefaultTableModel membersModel = new DefaultTableModel();

    private final JTable itemsTable = new JTable(itemsModel);
    private final JTable membersTable = new JTable(membersModel);

    public LibraryGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        seedData();    
        initGUI();     
        loadItemsTable(library.getAllItems());
        loadMembersTable(library.getAllMembers());
    }

    private void seedData() {
        library.addBook("The Hobbit", "J.R.R. Tolkien");
        library.addBook("Clean Code", "Robert C. Martin");
        library.addReferenceBook("Java API Reference", "Smith");
        library.addMagazine("Tech Weekly", 42);
        library.addAudiobook("Learning Java", "Jane Reader", 360);

        library.addMember("Alice");
        library.addMember("Bob");
        library.addMember(); 
    }

    private void initGUI() {
        JTabbedPane tabs = new JTabbedPane();

        JPanel itemsPanel = new JPanel(new BorderLayout());

        itemsModel.setColumnIdentifiers(new String[]{"ID", "Title", "Type", "Available"});
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane itemsScroll = new JScrollPane(itemsTable);
        itemsPanel.add(itemsScroll, BorderLayout.CENTER);

        JPanel itemButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton showAllBtn = new JButton("Show All Items");
        JButton showAvailableBtn = new JButton("Show Available");
        JButton borrowBtn = new JButton("Borrow Item");
        JButton returnBtn = new JButton("Return Item");
        JButton searchBtn = new JButton("Search by Title");
        JButton addItemBtn = new JButton("Add Item");

        itemButtons.add(showAllBtn);
        itemButtons.add(showAvailableBtn);
        itemButtons.add(borrowBtn);
        itemButtons.add(returnBtn);
        itemButtons.add(searchBtn);
        itemButtons.add(addItemBtn);

        itemsPanel.add(itemButtons, BorderLayout.NORTH);
        tabs.add("Items", itemsPanel);

      
        JPanel membersPanel = new JPanel(new BorderLayout());

        membersModel.setColumnIdentifiers(new String[]{"ID", "Name", "Active Loans"});
        JScrollPane membersScroll = new JScrollPane(membersTable);
        membersPanel.add(membersScroll, BorderLayout.CENTER);

        JPanel memberButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton showMembersBtn = new JButton("Show Members");
        JButton showLoansBtn = new JButton("Show Loans");
        JButton addMemberBtn = new JButton("Add Member");

        memberButtons.add(showMembersBtn);
        memberButtons.add(showLoansBtn);
        memberButtons.add(addMemberBtn);

        membersPanel.add(memberButtons, BorderLayout.NORTH);
        tabs.add("Members", membersPanel);

        add(tabs);


        showAllBtn.addActionListener(e -> loadItemsTable(library.getAllItems()));
        showAvailableBtn.addActionListener(e -> loadItemsTable(library.getAvailableItems()));
        borrowBtn.addActionListener(e -> borrowItem());
        returnBtn.addActionListener(e -> returnItem());
        searchBtn.addActionListener(e -> searchItems());
        addItemBtn.addActionListener(e -> addItem());

        showMembersBtn.addActionListener(e -> loadMembersTable(library.getAllMembers()));
        showLoansBtn.addActionListener(e -> showMemberLoans());
        addMemberBtn.addActionListener(e -> addMember());
    }

    private void loadItemsTable(List<Item> items) {
        itemsModel.setRowCount(0);
        for (Item it : items) {
            itemsModel.addRow(new Object[]{it.getId(), it.getTitle(), it.getItemType(), it.isAvailable() ? "Yes" : "No"});
        }
    }

    private void loadMembersTable(List<Member> members) {
        membersModel.setRowCount(0);
        for (Member m : members) {
            membersModel.addRow(new Object[]{m.getId(), m.getName(), m.getActiveLoanCount()});
        }
    }

    private void borrowItem() {
        try {
            int memberId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Member ID:"));
            int itemId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Item ID:"));
            library.borrowItem(memberId, itemId);
            JOptionPane.showMessageDialog(this, "Borrowed successfully!");
            loadItemsTable(library.getAllItems());
            loadMembersTable(library.getAllMembers());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void returnItem() {
        try {
            int memberId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Member ID:"));
            int itemId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Item ID:"));
            boolean ok = library.returnItem(memberId, itemId);
            JOptionPane.showMessageDialog(this, ok ? "Returned successfully!" : "Return failed.");
            loadItemsTable(library.getAllItems());
            loadMembersTable(library.getAllMembers());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void searchItems() {
        String query = JOptionPane.showInputDialog(this, "Enter title to search:");
        if (query != null) {
            List<Item> found = library.searchItemsByTitle(query);
            loadItemsTable(found);
        }
    }

    private void addItem() {
        String[] options = {"Book", "Reference Book", "Magazine", "Audiobook"};
        String type = (String) JOptionPane.showInputDialog(this, "Select item type:", "Add Item",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (type == null) return;

        try {
            String title = JOptionPane.showInputDialog(this, "Enter title:");
            switch (type) {
                case "Book":
                    String author = JOptionPane.showInputDialog(this, "Enter author:");
                    library.addBook(title, author);
                    break;
                case "Reference Book":
                    String rauthor = JOptionPane.showInputDialog(this, "Enter author:");
                    library.addReferenceBook(title, rauthor);
                    break;
                case "Magazine":
                    int issue = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter issue number:"));
                    library.addMagazine(title, issue);
                    break;
                case "Audiobook":
                    String narrator = JOptionPane.showInputDialog(this, "Enter narrator:");
                    int length = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter length in minutes:"));
                    library.addAudiobook(title, narrator, length);
                    break;
            }
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            loadItemsTable(library.getAllItems());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void addMember() {
        String name = JOptionPane.showInputDialog(this, "Enter member name:");
        if (name == null || name.trim().isEmpty()) {
            name = null;
        }
        library.addMember(name);
        JOptionPane.showMessageDialog(this, "Member added successfully!");
        loadMembersTable(library.getAllMembers());
    }

    private void showMemberLoans() {
        try {
            int memberId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Member ID:"));
            List<Loan> loans = library.getLoansForMember(memberId);
            StringBuilder sb = new StringBuilder();
            for (Loan l : loans) {
                Item it = library.getItem(l.getItemId());
                sb.append("Item: ").append(it.getTitle())
                        .append(" (").append(it.getItemType()).append(")")
                        .append(", Borrowed: ").append(l.getBorrowedOn())
                        .append(", Due: ").append(l.getDueOn())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.length() == 0 ? "No loans." : sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI().setVisible(true));
    }
}

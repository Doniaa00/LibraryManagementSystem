import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Library library = new Library();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        runMenu();
    }

    private static void seedData() {
        library.addBook("The Hobbit", "J.R.R. Tolkien");
        library.addBook("Clean Code", "Robert C. Martin");
        library.addReferenceBook("Java API Reference", "Smith");
        library.addMagazine("Tech Weekly", 42);
        library.addAudiobook("Learning Java", "Jane Reader", 360);

        library.addMember("Alice");
        library.addMember("Bob");
        library.addMember();
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1) Show all items");
            System.out.println("2) Show available items");
            System.out.println("3) Borrow item");
            System.out.println("4) Return item");
            System.out.println("5) Show all members");
            System.out.println("6) Show loans for member");
            System.out.println("7) Show overdue report");
            System.out.println("8) Search items by title");
            System.out.println("9) Add sample item / member");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": showAllItems(); break;
                    case "2": showAvailable(); break;
                    case "3": borrowFlow(); break;
                    case "4": returnFlow(); break;
                    case "5": showAllMembers(); break;
                    case "6": showLoansForMember(); break;
                    case "7": showOverdue(); break;
                    case "8": searchByTitle(); break;
                    case "9": addSample(); break;
                    case "0": System.out.println("Goodbye!"); return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void showAllItems() {
        List<Item> all = library.getAllItems();
        System.out.println("All items:");
        for (Item it : all) System.out.println(it);
    }

    private static void showAvailable() {
        List<Item> available = library.getAvailableItems();
        System.out.println("Available items:");
        for (Item it : available) System.out.println(it);
    }

    private static void borrowFlow() {
        try {
            System.out.print("Member ID: ");
            int mid = Integer.parseInt(scanner.nextLine());
            System.out.print("Item ID: ");
            int iid = Integer.parseInt(scanner.nextLine());
            library.borrowItem(mid, iid);
            System.out.println("Borrowed successfully.");
        } catch (BookNotAvailableException e) {
            System.out.println("Not available: " + e.getMessage());
        } catch (MaxLoansExceededException e) {
            System.out.println("Cannot borrow: " + e.getMessage());
        } catch (MemberNotFoundException | ItemNotFoundException e) {
            System.out.println("Not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }

    private static void returnFlow() {
        try {
            System.out.print("Member ID: ");
            int mid = Integer.parseInt(scanner.nextLine());
            System.out.print("Item ID: ");
            int iid = Integer.parseInt(scanner.nextLine());
            boolean ok = library.returnItem(mid, iid);
            System.out.println(ok ? "Returned successfully." : "Return failed.");
        } catch (MemberNotFoundException | ItemNotFoundException e) {
            System.out.println("Not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }

    private static void showAllMembers() {
        List<Member> members = library.getAllMembers();
        System.out.println("Members:");
        for (Member m : members) System.out.println(m);
    }

    private static void showLoansForMember() {
        try {
            System.out.print("Member ID: ");
            int mid = Integer.parseInt(scanner.nextLine());
            List<Loan> loans = library.getLoansForMember(mid);
            System.out.println("Loans for member " + mid + ":");
            for (Loan l : loans) {
                Item it = library.getItem(l.getItemId());
                System.out.println(" - " + it + "  borrowed on: " 
                        + l.getBorrowedOn() + " due: " + l.getDueOn());
            }
        } catch (MemberNotFoundException e) {
            System.out.println("Not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }

    private static void showOverdue() {
        List<String> report = library.getOverdueReport();
        if (report.isEmpty()) {
            System.out.println("No overdue items.");
        } else {
            System.out.println("Overdue report:");
            for (String line : report) System.out.println(" - " + line);
        }
    }

    private static void searchByTitle() {
        System.out.print("Search title: ");
        String q = scanner.nextLine();
        List<Item> found = library.searchItemsByTitle(q);
        if (found.isEmpty()) System.out.println("No items found.");
        else {
            System.out.println("Found:");
            for (Item it : found) System.out.println(it);
        }
    }

    private static void addSample() {
        System.out.println("Add: 1) Book 2) Reference 3) Magazine 4) Audiobook 5) Member");
        String c = scanner.nextLine().trim();
        try {
            switch (c) {
                case "1":
                    System.out.print("Title: "); String bt = scanner.nextLine();
                    System.out.print("Author: "); String ba = scanner.nextLine();
                    System.out.println("Added book id=" + library.addBook(bt, ba));
                    break;
                case "2":
                    System.out.print("Title: "); String rt = scanner.nextLine();
                    System.out.print("Author: "); String ra = scanner.nextLine();
                    System.out.println("Added reference id=" + library.addReferenceBook(rt, ra));
                    break;
                case "3":
                    System.out.print("Title: "); String mt = scanner.nextLine();
                    System.out.print("Issue num: "); int mi = Integer.parseInt(scanner.nextLine());
                    System.out.println("Added magazine id=" + library.addMagazine(mt, mi));
                    break;
                case "4":
                    System.out.print("Title: "); String at = scanner.nextLine();
                    System.out.print("Narrator: "); String an = scanner.nextLine();
                    System.out.print("Length minutes: "); int al = Integer.parseInt(scanner.nextLine());
                    System.out.println("Added audiobook id=" + library.addAudiobook(at, an, al));
                    break;
                case "5":
                    System.out.print("Name: "); String name = scanner.nextLine();
                    System.out.println("Added member id=" + library.addMember(name));
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid numeric input.");
        }
    }
}

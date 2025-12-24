public class Magazine extends Item {
    private final int issueNumber;

    public Magazine(int id, String title, int issueNumber) {
        super(id, title);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() { return issueNumber; }
    public String getItemType() { return "Magazine"; }
    public int getDefaultLoanDays() { return 7; }

    @Override
    public String toString() { return super.toString() + " (Issue " + issueNumber + ")"; }
}

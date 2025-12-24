public abstract class Item {
    protected final int id;
    protected final String title;
    protected boolean available = true;

    public Item(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public boolean canBeBorrowed() { return true; }
    public int getDefaultLoanDays() { return 14; }
    public abstract String getItemType();

    @Override
    public String toString() {
        return "[" + id + "] " + title + " (" + getItemType() + ")"
                + (available ? " (Available)" : " (Borrowed)");
    }
}

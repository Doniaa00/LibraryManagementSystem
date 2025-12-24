public class ReferenceBook extends Book {
    public ReferenceBook(int id, String title, String author) {
        super(id, title, author);
        this.available = false;
    }

    @Override
    public String getItemType() { return "Reference Book (In-Library)"; }
    @Override
    public boolean canBeBorrowed() { return false; }
    @Override
    public void setAvailable(boolean available) { this.available = false; }
}

public class Book extends Item {
    private final String author;

    public Book(int id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public Book(int id, String title) {
    	this(id, title, "Unknown"); 
    	}
    public String getAuthor() {
    	return author; 
    	}
    public String getItemType() { 
    	return "Book";
    	}
    public int getDefaultLoanDays() { 
    	return 21; 
    	}

    @Override
    public String toString() { return super.toString() + " by " + author; }
}

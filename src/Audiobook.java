public class Audiobook extends Item {
    private final String narrator;
    private final int lengthMinutes;

    public Audiobook(int id, String title, String narrator, int lengthMinutes) {
        super(id, title);
        this.narrator = narrator;
        this.lengthMinutes = lengthMinutes;
    }

    public String getNarrator() { return narrator; }
    public int getLengthMinutes() { return lengthMinutes; }
    public String getItemType() { return "Audiobook"; }
    public int getDefaultLoanDays() { return 28; }

    @Override
    public String toString() {
        return super.toString() + " (Narrator: " + narrator + ", " + lengthMinutes + " min)";
    }
}

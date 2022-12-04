package domain.model;

public enum Level {
    EASY(25, "Easy"),
    MEDIUM(20, "Medium"),
    HARD(17, "Hard");

    public final int numberOfProvidedDigits;
    public final String stringValue;
    Level(int numberOfProvidedDigits, String stringValue) {
        this.numberOfProvidedDigits = numberOfProvidedDigits;
        this.stringValue = stringValue;
    }
}

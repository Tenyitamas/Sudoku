package domain.model;

import util.Constants;

public enum Level {

    EASY(Constants.EASY_NO_DIGITS, "Easy"),
    MEDIUM(Constants.MEDIUM_NO_DIGITS, "Medium"),
    HARD(Constants.HARD_NO_DIGITS, "Hard");




    public final int numberOfProvidedDigits;
    public final String stringValue;
    Level(int numberOfProvidedDigits, String stringValue) {
        this.numberOfProvidedDigits = numberOfProvidedDigits;
        this.stringValue = stringValue;
    }
}

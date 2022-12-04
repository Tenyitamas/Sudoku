package util;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static util.Constants.*;

public class SudokuUtils {

    public static boolean collectionHasRepeats(List<Integer> collection) {
        for (int value = MIN_DIGIT_VALUE; value <= MAX_DIGIT_VALUE; value++) {
            if (Collections.frequency(collection, value) > 1) return true;
        }
        return false;
    }

    public static boolean isValidIndex(int index) {
        return index >= MIN_DIGIT_INDEX && index <= MAX_DIGIT_INDEX;
    }

    public static boolean isValidValue(int value) {
        return (value == 0  || value >= MIN_DIGIT_VALUE) && value <= MAX_DIGIT_VALUE;
    }

}

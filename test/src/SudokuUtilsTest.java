package src;

import org.junit.Assert;
import org.junit.Test;
import util.SudokuUtils;

import java.util.ArrayList;
import java.util.List;

public class SudokuUtilsTest {

    @Test
    public void isValidIndexTest() {
        Assert.assertFalse(SudokuUtils.isValidIndex(-1));
        Assert.assertFalse(SudokuUtils.isValidIndex(9));
        Assert.assertTrue(SudokuUtils.isValidIndex(0));
        Assert.assertTrue(SudokuUtils.isValidIndex(2));
        Assert.assertTrue(SudokuUtils.isValidIndex(8));
    }

    @Test
    public void isValidValueTest() {
        Assert.assertTrue(SudokuUtils.isValidValue(0));
        Assert.assertFalse(SudokuUtils.isValidValue(10));
        Assert.assertTrue(SudokuUtils.isValidValue(1));
        Assert.assertTrue(SudokuUtils.isValidValue(7));
        Assert.assertTrue(SudokuUtils.isValidValue(9));
    }


    @Test
    public void collectionHasRepeatsTest() {
        Assert.assertTrue(SudokuUtils.collectionHasRepeats(new ArrayList<>(){{
            for (int i = 1; i <= 9; i++) {
                add(i);
            }
            add(2);
        }}));

        Assert.assertFalse(SudokuUtils.collectionHasRepeats(new ArrayList<Integer>(){{
            for (int i = 0; i <= 9; i++) {
                add(i);
            }
        }}));

        Assert.assertFalse(SudokuUtils.collectionHasRepeats(new ArrayList<>(){{
            add(0);
            add(0);
            add(1);
            add(2);
        }}));
    }
}

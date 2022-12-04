package domain.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class Field implements Serializable {
    private int value = 0;
    private boolean editable = false;
    private Set<Integer> noteValues = Collections.emptySet();

    public Field(){

    }

    public Field(int value) {
        this.value = value;
    }

    public Field(int value, boolean editable) {
        this.value = value;
        this.editable = editable;
    }

    public Field(int value, boolean editable, Set<Integer> noteValues) {
        this.value = value;
        this.editable = editable;
        this.noteValues = noteValues;
    }

    public void addNoteValues(Set<Integer> notes) {
        noteValues.addAll(notes);
    }

    public int getValue() {
        return value;
    }

    public boolean isEditable() {
        return editable;
    }

    public Set<Integer> getNoteValues() {
        return noteValues;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setNoteValues(Set<Integer> noteValues) {
        this.noteValues = noteValues;
    }
}

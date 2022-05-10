package Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Helper.Helper.Word;

public class SortedList {
    public final ArrayList<Word> list = new ArrayList<>();

    public Word first() {
        return list.get(0);
    }

    public void clear() {
        list.clear();
    }

    public Word get(int i){
        return list.get(i);
    }

    public void add(Word o) {
        list.add(o);
        Collections.sort(list);
    }

    public void remove(Word o) {
        list.remove(o);
        Collections.sort(list);
    }

    public int size() {
        return list.size();
    }

    public boolean contains(Word o) {
        return list.contains(o);
    }


}

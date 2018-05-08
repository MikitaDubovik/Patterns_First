package sample.Iterator;

import sample.Models.Bus;

public interface BusIterator {
    boolean hasNext();
    Bus getNext();
    void reset();
}

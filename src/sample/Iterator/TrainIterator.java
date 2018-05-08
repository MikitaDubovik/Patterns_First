package sample.Iterator;

import sample.Models.Train;

public interface TrainIterator {
    boolean hasNext();
    Train getNext();
    void reset();
}

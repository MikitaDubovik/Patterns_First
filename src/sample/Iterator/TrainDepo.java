package sample.Iterator;

import sample.Models.Train;
import sample.Singlton.Transport;

import java.util.List;

public class TrainDepo implements TrainIterator {
    private int currentPosition = 0;

    private List<Train> trains = Transport.getInstance().trainList;

    @Override
    public boolean hasNext() {
        return currentPosition < trains.size();
    }

    @Override
    public Train getNext() {
        if (!hasNext()) {
            return null;
        }
        Train train = trains.get(currentPosition);
        currentPosition++;
        return train;
    }

    @Override
    public void reset() {
        currentPosition = 0;
    }
}

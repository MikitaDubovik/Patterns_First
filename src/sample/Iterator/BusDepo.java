package sample.Iterator;

import sample.Models.Bus;
import sample.Singlton.Transport;

import java.util.List;

public class BusDepo implements BusIterator {

    private int currentPosition = 0;

    private List<Bus> buses = Transport.getInstance().busList;

    @Override
    public boolean hasNext() {
        return currentPosition < buses.size();
    }

    @Override
    public Bus getNext() {
        if (!hasNext()) {
            return null;
        }
        Bus bus = buses.get(currentPosition);
        currentPosition++;
        return bus;
    }

    @Override
    public void reset() {
        currentPosition = 0;
    }
}

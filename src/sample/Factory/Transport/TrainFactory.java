package sample.Factory.Transport;

import sample.Singlton.Transport;

import java.io.File;

public class TrainFactory extends TransportFactory {

    @Override
    public void ParseXml(File file) {
        Transport.getInstance().ParseXml(file, "Train");
    }

    @Override
    public void ParseCsv(File file) {
        Transport.getInstance().ParseCsv(file, "Train");
    }
}

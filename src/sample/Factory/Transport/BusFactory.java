package sample.Factory.Transport;

import sample.Singlton.Transport;

import java.io.File;

public class BusFactory extends TransportFactory {

    @Override
    public void ParseXml(File file) {
        Transport.getInstance().ParseXml(file, "Bus");
    }

    @Override
    public void ParseCsv(File file) {
        Transport.getInstance().ParseCsv(file, "Bus");
    }
}

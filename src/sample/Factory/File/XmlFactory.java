package sample.Factory.File;

import sample.Factory.Transport.BusFactory;
import sample.Factory.Transport.TrainFactory;

import java.io.File;

public class XmlFactory extends FileFactory {

    @Override
    public void ParseFile(File file, String transport) {

        if (transport.equals("Bus"))
        {
            transportFactory = new BusFactory();
            transportFactory.ParseXml(file);
        }
        if(transport.equals("Train"))
        {
            transportFactory = new TrainFactory();
            transportFactory.ParseXml(file);
        }
    }
}

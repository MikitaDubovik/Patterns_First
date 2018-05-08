package sample.Factory.Transport;

import java.io.File;

public abstract class TransportFactory {

    public abstract void ParseXml(File file);

    public abstract void ParseCsv(File file);

}

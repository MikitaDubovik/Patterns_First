package sample.Factory.File;

import sample.Factory.Transport.TransportFactory;

import java.io.File;

public abstract class FileFactory {

    public TransportFactory transportFactory;

    public abstract void ParseFile(File file, String transport);

}

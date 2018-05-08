package sample.Singlton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.Models.Bus;
import sample.Models.Train;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Transport {

    private static Transport instance;

    public ArrayList<Train> trainList = new ArrayList<>();

    public ArrayList<Bus> busList = new ArrayList<>();

    private Transport() {
    }

    public static synchronized Transport getInstance() {
        if (instance == null) {
            synchronized (Transport.class) {
                if (instance == null) {
                    instance = new Transport();
                }
            }
        }
        return instance;
    }

    public void ParseXml(File file, String type) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            if (type.equals("Bus")) {
                NodeList nList = doc.getElementsByTagName("info");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Bus bus = new Bus();
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        bus.setNumber(eElement.getElementsByTagName("number-route").item(0).getTextContent());
                        bus.setDate(eElement.getElementsByTagName("date").item(0).getTextContent());
                        bus.setTime(eElement.getElementsByTagName("time").item(0).getTextContent());
                        bus.setDestination(eElement.getElementsByTagName("destination").item(0).getTextContent());
                        bus.setBeginStation(eElement.getElementsByTagName("begin-station").item(0).getTextContent());
                        bus.setPlatform(eElement.getElementsByTagName("platform").item(0).getTextContent());
                        bus.setEndStation(eElement.getElementsByTagName("end-station").item(0).getTextContent());
                        bus.setCost(eElement.getElementsByTagName("cost").item(0).getTextContent());
                        bus.setMark(eElement.getElementsByTagName("mark").item(0).getTextContent());
                        bus.setTakeTime(eElement.getElementsByTagName("take-time").item(0).getTextContent());
                        busList.add(bus);
                    }
                }
            }

            if (type.equals("Train")) {
                NodeList nList = doc.getElementsByTagName("info");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Train train = new Train();
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        train.setNumber(eElement.getElementsByTagName("number-route").item(0).getTextContent());
                        train.setBeginDate(eElement.getElementsByTagName("begin-date").item(0).getTextContent());
                        train.setBeginTime(eElement.getElementsByTagName("begin-time").item(0).getTextContent());
                        train.setDestination(eElement.getElementsByTagName("destination").item(0).getTextContent());
                        train.setBeginStation(eElement.getElementsByTagName("begin-station").item(0).getTextContent());
                        train.setPlatform(eElement.getElementsByTagName("platform").item(0).getTextContent());
                        train.setEndStation(eElement.getElementsByTagName("end-station").item(0).getTextContent());
                        train.setCost(eElement.getElementsByTagName("cost").item(0).getTextContent());
                        train.setType(eElement.getElementsByTagName("type").item(0).getTextContent());
                        train.setEndDate(eElement.getElementsByTagName("end-date").item(0).getTextContent());
                        train.setEndTime(eElement.getElementsByTagName("end-time").item(0).getTextContent());
                        trainList.add(train);
                    }
                }
                trainList.isEmpty();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ParseCsv(File file, String type) {
        try {
            if(type.equals("Bus"))
            {
                List<String> lines = Files.readAllLines(Paths.get(file.toPath().toString()));
                Boolean flag = false;
                for(String line : lines) {
                    Bus bus = new Bus();
                    String[] result = line.split(";");
                    if (flag) {
                        bus.setNumber(result[0]);
                        bus.setDate(result[1]);
                        bus.setTime(result[2]);
                        bus.setDestination(result[3]);
                        bus.setBeginStation(result[4]);
                        bus.setPlatform(result[5]);
                        bus.setEndStation(result[6]);
                        bus.setCost(result[7]);
                        bus.setMark(result[8]);
                        bus.setTakeTime(result[9]);
                        busList.add(bus);
                    }
                    flag = true;
                }
            }

            if(type.equals("Train"))
            {

                List<String> lines = Files.readAllLines(Paths.get(file.toPath().toString()));
                Boolean flag = false;
                for(String line : lines) {
                    Train train = new Train();
                    String[] result = line.split(";");
                    if(flag)
                    {
                        train.setNumber(result[0]);
                        train.setBeginDate(result[1]);
                        train.setBeginTime(result[2]);
                        train.setDestination(result[3]);
                        train.setBeginStation(result[4]);
                        train.setPlatform(result[5]);
                        train.setEndStation(result[6]);
                        train.setType(result[7]);
                        train.setCost(result[8]);
                        train.setEndDate(result[9]);
                        train.setEndTime(result[10]);
                        trainList.add(train);
                    }
                    flag = true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

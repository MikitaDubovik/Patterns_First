package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Iterator.BusDepo;
import sample.Models.Bus;
import sample.Singlton.Transport;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DisplayBus implements Initializable {

    private URL thisUrl;

    @FXML
    private TableView<Bus> tableBus;

    @FXML
    private TableColumn<Bus, String> number, date, time, destination, beginStation, platform, endStation, cost, mark, takeTime;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private ChoiceBox<String> chooseDist;

    @FXML
    private ChoiceBox<String> chooseTime;

    @FXML
    private CheckBox weekday;

    @FXML
    private CheckBox weekend;

    @FXML
    private TextField numberTrip;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    public void onRefresh(){
        FilteredList<Bus> temp = FilterByChoiceBox();
        temp = FilterByCheckBox(temp);
        temp = FilterByDate(temp);

        //REINITIALIZE TABLE
        tableBus.getItems().clear();
        initialize(thisUrl,null);

        SetSize(temp);

        if(datePickerTo.getValue() != null)
            datePickerTo.setValue(null);
        if(datePickerFrom.getValue() != null)
            datePickerFrom.setValue(null);
        if(weekend.isSelected() == true)
            weekend.setSelected(false);
        if(weekday.isSelected() == true)
            weekday.setSelected(false);
    }

    private void SetSize(FilteredList<Bus> temp){
        if (temp != null) {
            ArrayList<Bus> toTable = new ArrayList<>(temp);

            if (numberTrip.getText().isEmpty() == false) {
                int number = Integer.parseInt(numberTrip.getText());
                if (toTable.size() > number) {
                    Bus[] array = new Bus[number];
                    for (int i = 0; i < number; i++) {
                        array[i] = toTable.get(i);
                    }
                    toTable = new ArrayList<>(Arrays.asList(array));
                }
            }

            for (int i = 0; i < tableBus.getItems().size(); i++) {
                tableBus.getItems().clear();
            }
            for (Object Bus : toTable) {
                tableBus.getItems().add((Bus) Bus);
            }
        } else {
            if (numberTrip.getText().isEmpty() == false) {
                int number = Integer.parseInt(numberTrip.getText());
                if (number > 0) {
                    ArrayList<Bus> toTable = new ArrayList<>(tableBus.getItems());
                    int flag = 0;
                    tableBus.getItems().clear();

                    for (Object Bus : toTable) {
                        tableBus.getItems().add((Bus) Bus);
                        flag++;
                        if (flag == number)
                            break;
                    }
                } else
                    tableBus.getItems().clear();
            }
        }
    }

    private FilteredList<Bus> FilterByDate(FilteredList<Bus> temp){
        if(datePickerFrom.getValue() == null  && datePickerTo.getValue() == null)
            return temp;

        if(datePickerFrom.getValue() != null  && datePickerTo.getValue() == null) {
            if(temp != null){
                FilteredList<Bus> first = new FilteredList<>(temp,
                        bus -> datePickerFrom.getValue().isBefore(bus.getLocalDate()) || datePickerFrom.getValue().isEqual(bus.getLocalDate()));
                temp = new FilteredList<>(first, bus -> true);
            } else {
                temp = tableBus.getItems().filtered(bus -> datePickerFrom.getValue().isBefore(bus.getLocalDate()) || datePickerFrom.getValue().isEqual(bus.getLocalDate()));
            }

            return temp;
        }

        if(datePickerFrom.getValue() == null  && datePickerTo.getValue() != null){
            if(temp != null){
                FilteredList<Bus> first = new FilteredList<>(temp,
                        bus -> datePickerTo.getValue().isAfter(bus.getLocalDate()) || datePickerTo.getValue().isEqual(bus.getLocalDate()));
                temp = new FilteredList<>(first, bus -> true);
            } else {
                temp = tableBus.getItems().filtered(bus -> datePickerTo.getValue().isAfter(bus.getLocalDate()) || datePickerTo.getValue().isEqual(bus.getLocalDate()));

            }

            return temp;
        }

        if(datePickerFrom.getValue() != null  && datePickerTo.getValue() != null){
            if(temp != null){
                FilteredList<Bus> first = new FilteredList<>(temp,
                        bus -> (datePickerFrom.getValue().isBefore(bus.getLocalDate()) || datePickerFrom.getValue().isEqual(bus.getLocalDate())) && (datePickerTo.getValue().isAfter(bus.getLocalDate()) || datePickerTo.getValue().isEqual(bus.getLocalDate())));
                temp = new FilteredList<>(first, bus -> true);
            } else {
                temp = tableBus.getItems().filtered(bus ->
                        (datePickerFrom.getValue().isBefore(bus.getLocalDate()) || datePickerFrom.getValue().isEqual(bus.getLocalDate())) && (datePickerTo.getValue().isAfter(bus.getLocalDate()) || datePickerTo.getValue().isEqual(bus.getLocalDate())));
            }

            return temp;
        }

        return temp;
    }

    private FilteredList<Bus> FilterByChoiceBox() {
        FilteredList<Bus> temp = null;

        String selectedChoiceDist = chooseDist.getSelectionModel().getSelectedItem();
        String selectedChoiceTime = chooseTime.getSelectionModel().getSelectedItem();
        if (selectedChoiceTime != null || selectedChoiceDist != null) {
            if (selectedChoiceTime != null && selectedChoiceDist != null) {
                FilteredList<Bus> first = tableBus.getItems().filtered(bus -> bus.getTakeTime().equals(selectedChoiceTime));
                temp = new FilteredList<>(first, bus -> bus.getDestination().equals(selectedChoiceDist));

            } else if (selectedChoiceTime != null && selectedChoiceDist == null) {
                temp = tableBus.getItems().filtered(bus -> bus.getTakeTime().equals(selectedChoiceTime));

            } else if (selectedChoiceTime == null && selectedChoiceDist != null) {
                temp = tableBus.getItems().filtered(bus -> bus.getDestination().equals(selectedChoiceDist));
            }
        }
        return temp;
    }

    private FilteredList<Bus> FilterByCheckBox(FilteredList<Bus> temp){
        if(weekday.isSelected() == false && weekend.isSelected() == false)
            return temp;
        if(weekend.isSelected() == true) {
            if (temp == null) {
                temp = tableBus.getItems().filtered(
                        bus ->  bus.getLocalDate().getDayOfWeek().toString().equals("SATURDAY") ||
                                bus.getLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
            return temp;
            } else {
                FilteredList<Bus> here = new FilteredList<>(temp,
                        bus ->  bus.getLocalDate().getDayOfWeek().toString().equals("SATURDAY") ||
                                bus.getLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
                return here;
            }
        }
        if(weekday.isSelected() == true) {
            if (temp == null) {
                temp = tableBus.getItems().filtered(
                        bus ->  !bus.getLocalDate().getDayOfWeek().toString().equals("SATURDAY") &&
                                !bus.getLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
                return temp;
            } else {
                FilteredList<Bus> here = new FilteredList<>(temp,
                        bus ->  !bus.getLocalDate().getDayOfWeek().toString().equals("SATURDAY") &&
                                !bus.getLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
                return here;
            }
        }
        return null;
    }

    private void LineChartFill(Map<String, Integer> map){

        XYChart.Series series = new XYChart.Series();
        series.setName("Line of the distribution");
        for(Map.Entry entry : map.entrySet()){
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
    }

    private void BarChartFill(Map<String, Integer> map) {

        XYChart.Series series = new XYChart.Series();
        series.setName("Histogram of the distribution");
        for(Map.Entry entry : map.entrySet()){
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
    }

    private void TableFill(){
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        beginStation.setCellValueFactory(new PropertyValueFactory<>("beginStation"));
        platform.setCellValueFactory(new PropertyValueFactory<>("platform"));
        endStation.setCellValueFactory(new PropertyValueFactory<>("endStation"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        takeTime.setCellValueFactory(new PropertyValueFactory<>("takeTime"));
    }

    @Override
    public void initialize(URL usr, ResourceBundle rb){

        thisUrl = usr;

        Map<String, Integer> lineChartFill = new HashMap<>();
        Map<String, Integer> barChartFill = new HashMap<>();
        Set<String> distBus = new HashSet<>();
        Set<String> timeBus = new HashSet<>();

        BusDepo busDepo = new BusDepo();

        busDepo.reset();

        while(busDepo.hasNext()){
            Bus bus = busDepo.getNext();
            tableBus.getItems().add(bus);
            int cost = Integer.parseInt(bus.getCost());
            int number = 1;

            if (lineChartFill.get(bus.getDestination()) == null) {
            } else {
                cost +=lineChartFill.get(bus.getDestination());
                number = barChartFill.get(bus.getDestination()) + 1;
            }

            lineChartFill.put(bus.getDestination(), cost);
            barChartFill.put(bus.getDestination(), number);

            distBus.add(bus.getDestination());
            timeBus.add(bus.getTakeTime());
        }

        TableFill();

        LineChartFill(lineChartFill);

        BarChartFill(barChartFill);

        ObservableList<String> availableChoices = FXCollections.observableArrayList(distBus);
        chooseDist.setItems(availableChoices);

        availableChoices = FXCollections.observableArrayList(timeBus);
        chooseTime.setItems(availableChoices);
    }

}

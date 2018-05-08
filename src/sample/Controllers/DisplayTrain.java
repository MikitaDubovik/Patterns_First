package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Iterator.TrainDepo;
import sample.Models.Train;
import sample.Singlton.Transport;
import java.net.URL;
import java.util.*;

public class DisplayTrain implements Initializable {

    private URL thisUrl;

    @FXML
    private TableView<Train> tableTrain;

    @FXML
    private TableColumn<Train, String> number, beginDate, endDate, beginTime, endTime, destination, beginStation, platform, endStation, type, cost;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
	private BarChart<String, Number> barChart;

    @FXML
    private ChoiceBox<String> chooseDist;

    @FXML
    private ChoiceBox<String> chooseType;

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

    public void onRefresh() {
        FilteredList<Train> temp = FilterByChoiceBox();
        temp = FilterByCheckBox(temp);
        temp = FilterByDate(temp);

        //REINITIALIZE TABLE
        tableTrain.getItems().clear();
        initialize(thisUrl, null);

        SetSize(temp);

        if (datePickerTo.getValue() != null)
            datePickerTo.setValue(null);
        if (datePickerFrom.getValue() != null)
            datePickerFrom.setValue(null);
        if(weekend.isSelected() == true)
            weekend.setSelected(false);
        if(weekday.isSelected() == true)
            weekday.setSelected(false);
    }

    private void SetSize(FilteredList<Train> temp){
        if (temp != null) {
            ArrayList<Train> toTable = new ArrayList<>(temp);

            if (numberTrip.getText().isEmpty() == false) {
                int number = Integer.parseInt(numberTrip.getText());
                if (toTable.size() > number) {
                    Train[] array = new Train[number];
                    for (int i = 0; i < number; i++) {
                        array[i] = toTable.get(i);
                    }
                    toTable = new ArrayList<>(Arrays.asList(array));
                }
            }

            for (int i = 0; i < tableTrain.getItems().size(); i++) {
                tableTrain.getItems().clear();
            }
            for (Object Train : toTable) {
                tableTrain.getItems().add((Train) Train);
            }
        } else {
            if (numberTrip.getText().isEmpty() == false) {
                int number = Integer.parseInt(numberTrip.getText());
                if (number > 0) {
                    ArrayList<Train> toTable = new ArrayList<>(tableTrain.getItems());
                    int flag = 0;
                    tableTrain.getItems().clear();

                    for (Object Train : toTable) {
                        tableTrain.getItems().add((Train) Train);
                        flag++;
                        if (flag == number)
                            break;
                    }
                } else
                    tableTrain.getItems().clear();
            }
        }
    }

    private FilteredList<Train> FilterByDate(FilteredList<Train> temp){
        if(datePickerFrom.getValue() == null  && datePickerTo.getValue() == null)
            return temp;

        if(datePickerFrom.getValue() != null  && datePickerTo.getValue() == null) {
            if(temp != null){
                FilteredList<Train> first = new FilteredList<>(temp,
                        Train -> datePickerFrom.getValue().isBefore(Train.getStartLocalDate())|| datePickerFrom.getValue().isEqual(Train.getStartLocalDate()));
                temp = new FilteredList<>(first, Train -> true);
            } else {
                temp = tableTrain.getItems().filtered(Train -> datePickerFrom.getValue().isBefore(Train.getStartLocalDate())|| datePickerFrom.getValue().isEqual(Train.getStartLocalDate()));
            }

            return temp;
        }

        if(datePickerFrom.getValue() == null  && datePickerTo.getValue() != null){
            if(temp != null){
                FilteredList<Train> first = new FilteredList<>(temp,
                        Train -> datePickerTo.getValue().isAfter(Train.getEndLocalDate())  || datePickerTo.getValue().isEqual(Train.getEndLocalDate()));
                temp = new FilteredList<>(first, Train -> true);
            } else {
                temp = tableTrain.getItems().filtered(Train -> datePickerTo.getValue().isAfter(Train.getEndLocalDate())  || datePickerTo.getValue().isEqual(Train.getEndLocalDate()));

            }

            return temp;
        }

        if(datePickerFrom.getValue() != null  && datePickerTo.getValue() != null){
            if(temp != null){
                FilteredList<Train> first = new FilteredList<>(temp,
                        Train -> (datePickerFrom.getValue().isBefore(Train.getStartLocalDate())|| datePickerFrom.getValue().isEqual(Train.getStartLocalDate())) && (datePickerTo.getValue().isAfter(Train.getEndLocalDate()) || datePickerTo.getValue().isEqual(Train.getEndLocalDate())));
                temp = new FilteredList<>(first, Train -> true);
            } else {
                temp = tableTrain.getItems().filtered(Train ->
                        (datePickerFrom.getValue().isBefore(Train.getStartLocalDate())|| datePickerFrom.getValue().isEqual(Train.getStartLocalDate())) && (datePickerTo.getValue().isAfter(Train.getEndLocalDate()) || datePickerTo.getValue().isEqual(Train.getEndLocalDate())));
            }

            return temp;
        }

        return temp;
    }

    private FilteredList<Train> FilterByChoiceBox() {
        FilteredList<Train> temp = null;

        String selectedChoiceDist = chooseDist.getSelectionModel().getSelectedItem();
        String selectedChoiceType = chooseType.getSelectionModel().getSelectedItem();
        if (selectedChoiceType != null || selectedChoiceDist != null) {
            if (selectedChoiceType != null && selectedChoiceDist != null) {
                FilteredList<Train> first = tableTrain.getItems().filtered(Train -> Train.getType().equals(selectedChoiceType));
                temp = new FilteredList<>(first, Train -> Train.getDestination().equals(selectedChoiceDist));

            } else if (selectedChoiceType != null && selectedChoiceDist == null) {
                temp = tableTrain.getItems().filtered(Train -> Train.getType().equals(selectedChoiceType));

            } else if (selectedChoiceType == null && selectedChoiceDist != null) {
                temp = tableTrain.getItems().filtered(Train -> Train.getDestination().equals(selectedChoiceDist));
            }
        }
        return temp;
    }

    private FilteredList<Train> FilterByCheckBox(FilteredList<Train> temp){
        if(weekday.isSelected() == false && weekend.isSelected() == false)
            return temp;
        if(weekend.isSelected() == true) {
            if (temp == null) {
                temp = tableTrain.getItems().filtered(
                        Train ->  Train.getStartLocalDate().getDayOfWeek().toString().equals("SATURDAY") ||
                                  Train.getStartLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
                return temp;
            } else {
                FilteredList<Train> here = new FilteredList<>(temp,
                        Train ->  Train.getStartLocalDate().getDayOfWeek().toString().equals("SATURDAY") ||
                                  Train.getStartLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
                return here;
            }
        }
        if(weekday.isSelected() == true) {
            if (temp == null) {
                temp = tableTrain.getItems().filtered(
                        Train ->  !Train.getStartLocalDate().getDayOfWeek().toString().equals("SATURDAY") &&
                                  !Train.getStartLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
                return temp;
            } else {
                FilteredList<Train> here = new FilteredList<>(temp,
                        Train ->  !Train.getStartLocalDate().getDayOfWeek().toString().equals("SATURDAY") &&
                                  !Train.getStartLocalDate().getDayOfWeek().toString().equals("SUNDAY"));
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
        beginDate.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        beginTime.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        beginStation.setCellValueFactory(new PropertyValueFactory<>("beginStation"));
        platform.setCellValueFactory(new PropertyValueFactory<>("platform"));
        endStation.setCellValueFactory(new PropertyValueFactory<>("endStation"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    @Override
    public void initialize(URL usr, ResourceBundle rb){

        thisUrl = usr;

        Map<String, Integer> lineChartFill = new HashMap<String, Integer>();
        Map<String, Integer> barChartFill = new HashMap<String, Integer>();

        Set<String> distTrain = new HashSet<>();
        Set<String> typeTrain = new HashSet<>();

        TrainDepo trainDepo = new TrainDepo();

        while(trainDepo.hasNext()){
            Train train = trainDepo.getNext();
            tableTrain.getItems().add(train);
            int cost = Integer.parseInt(train.getCost());
            int number = 1;

            if(lineChartFill.get(train.getDestination())!=null)
            {
                cost +=lineChartFill.get(train.getDestination());
                number = barChartFill.get(train.getDestination()) + 1;
            }

            lineChartFill.put(train.getDestination(), cost);
            barChartFill.put(train.getDestination(), number);

            distTrain.add(train.getDestination());
            typeTrain.add(train.getType());
        }

        TableFill();

        LineChartFill(lineChartFill);

        BarChartFill(barChartFill);

        ObservableList<String> availableChoices = FXCollections.observableArrayList(distTrain);
        chooseDist.setItems(availableChoices);

        availableChoices = FXCollections.observableArrayList(typeTrain);
        chooseType.setItems(availableChoices);
    }
}


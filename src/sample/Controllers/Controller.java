package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Factory.File.CsvFactory;
import sample.Factory.File.FileFactory;
import sample.Factory.File.XmlFactory;
import java.io.File;
import java.io.IOException;

public class Controller {

    public Button exit;

    @FXML
    private RadioButton busButton;

    public void ChooseFile() throws Exception {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        file.getName();
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String format = fileName.substring(dotIndex + 1);
        System.out.println(format);
        FileFactory factory;
        if(format.equals("xml"))
        {
            factory = new XmlFactory();
            factory.ParseFile(file, GetTextFromButton(busButton));
        }
        else if(format.equals("csv"))
        {
            factory = new CsvFactory();
            factory.ParseFile(file, GetTextFromButton(busButton));
        }
        else{
            throw new Exception();
        }

        GoToView();
    }

    private String GetTextFromButton(RadioButton button)
    {
        if(button.isSelected())
            return "Bus";
        else
            return "Train";
    }

    private void GoToView() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/" + GetTextFromButton(busButton) + ".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Exit(){
        System.exit(0);
    }

}

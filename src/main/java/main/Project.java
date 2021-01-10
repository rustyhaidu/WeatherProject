package main;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.City;
import main.model.table.TableItem;
import main.model.weather.WeatherBody;
import main.utils.FileUtils;
import main.utils.Geography;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Project extends Application {
    private Button reset;
    private ComboBox<String> countryChoiceBox;
    private ComboBox<String> cityChoiceBox;
    static List<String> countryList;
    static Map<String, List<City>> geoMap;
    static Map<String, List<String>> geoCityMap;

    public static void main(String[] args) {
        Geography geography = new Geography();
        geoMap = geography.getGeoMap();
        geoCityMap = geography.getGeoCityMap();

        countryList = new ArrayList<>(geography.getCountries());
        Collections.sort(countryList);
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(Project.class.getSimpleName());
        stage.setWidth(1000);
        stage.setHeight(800);

        // JavaFX
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();

        reset = new Button("RESET");
        TableView<TableItem> tableView = new TableView<>();

        reset.setOnAction(event -> tableView.getItems().clear());


        TableColumn<TableItem, String> column1 = new TableColumn<>("Country");
        PropertyValueFactory<TableItem, String> country = new PropertyValueFactory<>("country");
        column1.setCellValueFactory(country);

        TableColumn<TableItem, String> column2 = new TableColumn<>("City");
        column2.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<TableItem, String> column3 = new TableColumn<>("Temperature");
        column3.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        VBox vbox = new VBox(tableView);

        ObservableList<String> obsCountryList = FXCollections.observableArrayList();
        obsCountryList.addAll(countryList);

        countryChoiceBox = new ComboBox<>();
        cityChoiceBox = new ComboBox<>();
        //cityChoiceBox.setEditable(true);
        countryChoiceBox.setItems(obsCountryList);

        countryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            String countryName = countryChoiceBox.getItems().get((Integer) newValue);

            System.out.println(countryName);
            ObservableList<String> obsCityList = FXCollections.observableArrayList();

            List<String> cityListPerCountry = geoCityMap.get(countryName);
            Collections.sort(cityListPerCountry);
            obsCityList.addAll(cityListPerCountry);
            cityChoiceBox.setItems(obsCityList);

            cityChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable1, oldValue1, newValue1) -> {
                if (!newValue1.equals(-1)) {
                    String cityName = cityChoiceBox.getItems().get((Integer) newValue1);
                    System.out.println(cityName);

                    ObjectMapper mapper = new ObjectMapper();
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=91b14ab8d3eb4985193f39010f433451&units=metric")
                            .build(); // defaults to GET

                    Response response;
                    try {
                        response = client.newCall(request).execute();
                        WeatherBody weatherBody = mapper.readValue(response.body().byteStream(), WeatherBody.class);

                        TableItem tableItem = new TableItem();
                        String country1 = weatherBody.getSys().getCountry();
                        tableItem.setCountry(country1);

                        String city = weatherBody.getName();
                        tableItem.setCity(city);

                        double temperature = weatherBody.getMain().getTemp();
                        tableItem.setTemperature(weatherBody.getMain().getTemp());

                        tableView.getItems().add(tableItem);

                        FileUtils.writeToFile(country1 + " " + city + " " + temperature + " " + LocalDateTime.now());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        });

        layout.getChildren().addAll(countryChoiceBox, cityChoiceBox, vbox, reset);
    }
}

package cost.inventorymanagement;

import cost.inventorymanagement.model.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * The Main class that will run the application.
 *  FUTURE ENHANCEMENT: I would like to add more of a design aspect to the front end to make more appealing.
 *  I would only allow the database manager access to add parts manually and anyone that's not DBAdmin would
 *  have a preset list that would be a dropdown list/menu with those options that were created or added by DBadmin
 *
 * @author Troy Cost, Student ID: 002197948, Software I - C482
 *
 */


public class Main extends Application {


    /**
     * Loads the main inventory management form.
     *
     * @param stage
     * @throws IOException
     */
    @FXML
    public void start(Stage stage) throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene scene = new Scene(loader);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the entry point of the application.
     * After the sample data is generated, the main method launches the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        /**
         * When the application loads temporary in-house parts are added to the tableview
         */

        InHouse Brakes = new InHouse(Inventory.getPartID(), "New Brakes", 205.99, 12, 1, 30, 501);
        InHouse Wheel = new InHouse(Inventory.getPartID(), "New Wheel", 349.99, 10, 1, 25, 502);
        InHouse Tire1 = new InHouse(Inventory.getPartID(), "Front Tire", 99.99, 10, 1, 20, 503);
        InHouse Tire2 = new InHouse(Inventory.getPartID(), "Rear Tire", 99.99, 10, 1, 20, 504);


        Inventory.addPart(Brakes);
        Inventory.addPart(Wheel);
        Inventory.addPart(Tire1);
        Inventory.addPart(Tire2);

        /**
         * Add temporary outsourced products to display automatically when the application loads
         */
        Outsourced HeatedSeat = new Outsourced(Inventory.getPartID(), "Heated Seat", 149.99, 5, 1, 30, "Harley Davidson Direct");
        Outsourced Muffler = new Outsourced(Inventory.getPartID(), "Muffler", 199.99, 10, 1, 15, "2 Guys that Bike");
        Outsourced handlebars = new Outsourced(Inventory.getPartID(), "Handle Bars", 140.99, 10, 1, 20, "Biker Boyz");

        Inventory.addPart(HeatedSeat);
        Inventory.addPart(Muffler);
        Inventory.addPart(handlebars);

/**
 * Add temporary products to display automatically when the application loads
 */
        Product StreetGlide = new Product(Inventory.getProductID(), "Street Glide", 21500.99, 5, 2, 30);
        Product RoadGlide = new Product(Inventory.getProductID(), "Road Glide", 14780.99, 3, 1, 20);
        Product SportGlide = new Product(Inventory.getProductID(), "Sport Glide", 18599.99, 10, 1, 20);


        Inventory.addProduct(StreetGlide);
        Inventory.addProduct(RoadGlide);
        Inventory.addProduct(SportGlide);

        launch(args);
    }



}
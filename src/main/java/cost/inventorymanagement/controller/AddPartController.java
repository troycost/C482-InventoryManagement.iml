package cost.inventorymanagement.controller;

import cost.inventorymanagement.model.InHouse;
import cost.inventorymanagement.model.Inventory;
import cost.inventorymanagement.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The AddPart Controller class provides the control for the add part screen.
 */
public class AddPartController implements Initializable {

    @FXML
    private TextField nameTxtFld;
    @FXML
    private TextField minTxtFld;
    @FXML
    private TextField priceCostTxtFld;
    @FXML
    private TextField maxTxtFld;
    @FXML
    private TextField machineIDField;
    @FXML
    private TextField inventoryTxtFld;
    @FXML
    private TextField idTxtFld;
    @FXML
    private Label machineIDLabel;
    @FXML
    private RadioButton outsourceRadioButton;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private void onInHouseSelected(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID:");

    }

    /**
     * This method changes the machine ID label from machine ID to Company Name when the outsourced radio button is selected.
     *
     * @param actionEvent
     */
    @FXML
    private void onOutsourceSelected(ActionEvent actionEvent) {
        machineIDLabel.setText("Company Name:");
    }

    /**
     * The minimum valid method will stop a user from entering invalid values
     *
     * for min and  max fields
     *
     * @param min
     * @param max
     * @return
     */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please enter a valid number.");

            alert.showAndWait();
        }

        return isValid;
    }

    /**
     * This ensures that inventory level is equal to or between the min and max values.
     *
     * @param min   The minimum inventory level value for the part.
     * @param max   The maximum inventory level value for the part.
     * @param stock The inventory level for the part.
     * @return Boolean that indicates if the inventory values entered are valid.
     */

    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please enter an inventory value that is between the min and max values..");

            alert.showAndWait();
        }

        return isValid;
    }

    /**
     * Adds the part record in the inventory class and loads MainController.
     * If the users' inputs are not valid, alert messages will display.
     *
     * @param actionEvent Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    private void onSave(ActionEvent actionEvent) {

        try {
            int id = 0;
            String name = nameTxtFld.getText();
            Double price = Double.parseDouble(priceCostTxtFld.getText());
            int stock = Integer.parseInt(inventoryTxtFld.getText());
            int min = Integer.parseInt(minTxtFld.getText());
            int max = Integer.parseInt(maxTxtFld.getText());
            int machineId;
            String companyName = machineIDField.getText();
            boolean partAddSuccessful = false;

            if (name.isBlank() ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("uh oh, we've encountered an error!");
                alert.setContentText("Please enter a part name.");
                alert.showAndWait();

            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineId = Integer.parseInt(machineIDField.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getPartID());
                            Inventory.addPart(newInHousePart);
                            partAddSuccessful = true;
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("uh oh, we've encountered an error!");
                            alert.setContentText("Please enter only numbers in the max, min, inv, price, and machine ID fields");
                            alert.showAndWait();
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("uh oh, we've encountered an error!");
                            alert.setContentText("Please enter a valid min and max number.\nThe min number can not be higher than the max number.");
                        }
                    }

                    if (outsourceRadioButton.isSelected() && companyName.isBlank()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("uh oh, we've encountered an error!");
                        alert.setContentText("Please enter a company name.");
                        alert.showAndWait();

                }else
                    if (outsourceRadioButton.isSelected()) {

                        companyName = machineIDField.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        newOutsourcedPart.setId(Inventory.getPartID());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
//                        Parent root = FXMLLoader.load(getClass().getResource("/cost/inventorymanagement/mainForm.fxml"));
//                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//                        stage.setTitle("Inventory Management");
//                        stage.setScene(new Scene(root));
//                        stage.show();
                    }
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please enter only numbers in the max, min, inv, price, and machine ID fields.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please validate your input.");
            alert.showAndWait();
        }
    }
    /**
     * Allows the user to cancel their request and confirms the request with dialog box.
     *RUNTIME ERROR I kept getting error when running my application when trying to save my newly added product.
     * The reason for the error was my machineID had a typo within the FXML and was listed as machineIdField instead
     * machineIDField. Easy fix once I ran my e.printStackTrace(); method to see where the error was happening.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancellation Request");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Initialize the add part controller and preselect the In-House radio button.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inHouseRadioButton.setSelected(true);
    }
}


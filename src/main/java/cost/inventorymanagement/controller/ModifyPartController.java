package cost.inventorymanagement.controller;

import cost.inventorymanagement.model.InHouse;
import cost.inventorymanagement.model.Inventory;
import cost.inventorymanagement.model.Outsourced;
import cost.inventorymanagement.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The ModifyPart Controller class provides the control logic for the modify part screen.
 */

public class ModifyPartController implements Initializable {

    @FXML
    private ToggleGroup modtoggleGrp;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader
    @FXML // fx:id="idLabel"
    private Label idLabel; // Value injected by FXMLLoader
    @FXML // fx:id="idTextField"
    private TextField idTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="inHouseRadioButton"
    private RadioButton inHouseRadioButton; // Value injected by FXMLLoader
    @FXML // fx:id="invLabel"
    private Label invLabel; // Value injected by FXMLLoader
    @FXML // fx:id="inventoryTextField"
    private TextField inventoryTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="machineIdLabel"
    private Label machineIdLabel; // Value injected by FXMLLoader
    @FXML // fx:id="machineIdTextField"
    private TextField machineIdTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="maxLabel"
    private Label maxLabel; // Value injected by FXMLLoader
    @FXML // fx:id="maxTextField"
    private TextField maxTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="minLabel"
    private Label minLabel; // Value injected by FXMLLoader
    @FXML // fx:id="minTextField"
    private TextField minTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="modifyPartLabel"
    private Label modifyPartLabel; // Value injected by FXMLLoader
    @FXML // fx:id="nameLabel"
    private Label nameLabel; // Value injected by FXMLLoader
    @FXML // fx:id="nameTextField"
    private TextField nameTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="outsourceRadioButton"
    private RadioButton outsourceRadioButton; // Value injected by FXMLLoader
    @FXML // fx:id="priceCostLabel"
    private Label priceCostLabel; // Value injected by FXMLLoader
    @FXML // fx:id="priceCostTextField"
    private TextField priceCostTxtFld; // Value injected by FXMLLoader
    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader
    private Part selectedPart;

    /**
     * This method retrieves the selected part's data and displays it via the radio button and text fields in the modify part screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainController.getPartToModify();

        if (selectedPart instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            machineIdLabel.setText("Machine ID");
            machineIdTxtFld.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));//casting
        }

        if (selectedPart instanceof Outsourced) {
            outsourceRadioButton.setSelected(true);
            machineIdLabel.setText("Company Name");
            machineIdTxtFld.setText(((Outsourced) selectedPart).getCompanyName());//casting
        }

        idTxtFld.setText(String.valueOf(selectedPart.getId()));
        nameTxtFld.setText(selectedPart.getName());
        inventoryTxtFld.setText(String.valueOf(selectedPart.getStock()));
        priceCostTxtFld.setText(String.valueOf(selectedPart.getPrice()));
        maxTxtFld.setText(String.valueOf(selectedPart.getMax()));
        minTxtFld.setText(String.valueOf(selectedPart.getMin()));
    }

    /**
     * This method changes the machine ID label from machine ID to Company Name when the outsourced radio button is selected.
     *
     * @param actionEvent
     */
    @FXML
    private void onOutsourceSelected(ActionEvent actionEvent) {

        machineIdLabel.setText("Company Name:");

    }

    /**
     * Validates that min is greater than 0 and less than max.
     *
     * @param min The minimum inventory level value for the part.
     * @param max The maximum inventory level value for the part.
     * @return Boolean that indicates if the minimum inventory level value is valid.
     */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Oops, we have encountered an error!");
            alert.setContentText("Please enter a valid number.");

            alert.showAndWait();

        }

        return isValid;
    }

    /**
     * Validates that inventory level is equal to or between the min and max values.
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
            alert.setHeaderText("Oops, we have encountered an error!");
            alert.setContentText("Please enter an inventory value that is between the min and max values..");

            alert.showAndWait();
        }

        return isValid;
    }

    /**
     * Updates the part record in the inventory class  and loads MainController.
     * If the users' inputs are not valid, alert messages will display.
     *
     * @param actionEvent Save button action.
     * @throws IOException From FXMLLoader.
     */

    public void onSave(ActionEvent actionEvent) {
        int selectedIndex = Inventory.getAllParts().indexOf(selectedPart);
        try {
            int id = selectedPart.getId();
            String name = nameTxtFld.getText();
            Double price = Double.parseDouble(priceCostTxtFld.getText());
            int stock = Integer.parseInt(inventoryTxtFld.getText());
            int min = Integer.parseInt(minTxtFld.getText());
            int max = Integer.parseInt(maxTxtFld.getText());
            int machineId;
            String companyName = machineIdTxtFld.getText();
            boolean partAddSuccessful = false;
            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Oops, we have encountered an error!");
                alert.setContentText("Please enter a part name.");
                alert.showAndWait();
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineId = Integer.parseInt(machineIdTxtFld.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            Inventory.updatePart(selectedIndex, newInHousePart);
                            partAddSuccessful = true;
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Oops, we have encountered an error!");
                            alert.setContentText("Please enter only numbers in the max, min, inv, price, and machine ID fields");
                            alert.showAndWait();
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Oops, we have encountered an error!");
                            alert.setContentText("Please enter a correct value.");
                        }
                    }
                    if (outsourceRadioButton.isSelected() && companyName.isBlank()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Oops, we have encountered an error!");
                        alert.setContentText("Please enter a company name.");
                        alert.showAndWait();
                    } else if (outsourceRadioButton.isSelected()) {
                        companyName = machineIdTxtFld.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        Inventory.updatePart(selectedIndex, newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                    }
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Oops, we have encountered an error!");
            alert.setContentText("Please enter only numbers in the max, min, inv, price, and machine ID fields.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Oops, we have encountered an error!");
            alert.setContentText("Please validate your input.");
            alert.showAndWait();
        }


    }


    /**
     * Allows the user to cancel his or her request and  or confirm the request to cancel.
     *
     * @param actionEvent
     * @throws IOException
     */

    public void onCancel(ActionEvent actionEvent) {
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
     * The method for the InHouse selected radio button.
     *
     * @param actionEvent changes the machine ID label to machine ID.
     */
    public void onInHouseSelected(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID:");
    }


}
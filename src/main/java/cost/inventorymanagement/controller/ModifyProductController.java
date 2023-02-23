package cost.inventorymanagement.controller;

import cost.inventorymanagement.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The ModifyProduct Controller class provides the control logic for the modify product screen.
 */
public class ModifyProductController implements Initializable {

    @FXML
    private TextField productPriceTxtFld;
    @FXML
    private TextField productNameTxtFld;
    @FXML
    private TextField productInStockTxtFld;
    @FXML
    private TextField inStockMaxTxtFld;
    @FXML
    private TextField inStockMinTxtFld;
    @FXML
    private TextField addPartSearchTxtFld;

    @FXML
    private TextField autoGeneratedIDTxtFld;
    @FXML
    private TableView<Part> associatedPartTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> associatedPriceCostPerUnitColumn;
    @FXML
    private TableView<Part> addPartTableView;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> addPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceCostPerUnitColumn;
    private Product selectedProduct;
    /**
     * The associated parts list.
     */
    private ObservableList<Part> associatedParts = observableArrayList();

    /**
     * The min valid method will stop a user from entering invalid values for min and max text fields.
     *
     * @param min
     * @param max
     * @return
     */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * The inventory valid method will stop a user from entering invalid values for the stock(inventory level) text fields.
     *
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * The user is able to select the part in the parts table to be added to the associated parts to table.
     *
     * @param event
     */
    @FXML
    void onAddPartButton(ActionEvent event) {
        Part selectedPart = addPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please select a part to add to the associate parts table.");
            alert.showAndWait();

        } else {

            associatedParts.add(selectedPart);
            associatedPartTableView.setItems(associatedParts);

        }
    }

    /**
     * The user searches for a part by ID or name (partial or full name) using the text field.
     *
     * @param event
     */
    @FXML
    void onAddPartSearch(ActionEvent event) {
        String searchPartsString = addPartSearchTxtFld.getText();
        if (searchPartsString.isBlank()) {
            addPartTableView.setItems(Inventory.getAllParts());
        } else {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchPartsString));
                if (part != null) {
                    addPartTableView.getSelectionModel().select(part);
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> partsLocated = Inventory.lookupPart(searchPartsString);
                if (partsLocated.size() > 0) {
                    addPartTableView.setItems(partsLocated);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("uh oh, we've encountered an error!");
                    alert.setContentText("Please enter a valid part ID or name.");
                    alert.showAndWait();
                    addPartTableView.setItems(Inventory.getAllParts());                }
            }
        }
    }


    /**
     * The user is able to cancel their request and return to the main screen.
     *
     * @param event
     */
    @FXML
    void onCancelMod(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    /**
     * This allows the users to remove the associated part that was added
     *
     * @param event
     */
    @FXML
    void onRemoveAssocPart(ActionEvent event) {
        Part selectedAssocPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedAssocPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please select a part to delete.");

            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected Part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedAssocPart);
                associatedPartTableView.setItems(associatedParts);
            }
        }
    }

    /**
     * The modification of a products is saved and the data in the textfields are validated.
     *
     * @param event
     */
    @FXML
    void onSaveMod(ActionEvent event) {
        int selectedIndex = Inventory.getAllProducts().indexOf(selectedProduct);
        try {
            int id = selectedProduct.getId();
            String name = productNameTxtFld.getText();
            double price = Double.parseDouble(productPriceTxtFld.getText());
            int stock = Integer.parseInt(productInStockTxtFld.getText());
            int min = Integer.parseInt(inStockMinTxtFld.getText());
            int max = Integer.parseInt(inStockMaxTxtFld.getText());
            boolean productAddSuccessful = false;

            if (productNameTxtFld.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("uh oh, we've encountered an error!");
                alert.setContentText("Please enter a product name.");
                alert.show();
            }
                if (!minValid(min, max)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("uh oh, we've encountered an error!");
                    alert.setContentText("Please check that the min value is not greater than the max value.");
                    alert.show();
                }
                    if ((!inventoryValid(min, max, stock))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("uh oh, we've encountered an error!");
                        alert.setContentText("Please enter an inventory value that is between the min and max values.");
                        alert.show();
                    }
                    else{

                    Product selectedProduct = new Product(id, name, price, stock, min, max, associatedParts);
                    Inventory.updateProduct(selectedIndex, selectedProduct);
                    productAddSuccessful = true;
                }

                if (productAddSuccessful) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please enter only numbers in the max, min, inv, and price fields.");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Unable to save modification.");
            alert.show();

        }

    }

    /**
     * The product selected for modification in the main controller is passed to the modify product controller.
     *A null point exception was corrected for this method by wrapping the set statements with an if statement.
     * @param product
     */
    public void sendProductsToProductForm(Product product) {
        if (product != null) {
            autoGeneratedIDTxtFld.setText(String.valueOf(product.getId()));
            productNameTxtFld.setText(product.getName());
            productInStockTxtFld.setText(String.valueOf(product.getStock()));
            productPriceTxtFld.setText(String.valueOf(product.getPrice()));
            inStockMaxTxtFld.setText(String.valueOf(product.getMax()));
            inStockMinTxtFld.setText(String.valueOf(product.getMin()));
        }

    }


    /**
     * Populating the data for the parts table in the modify product form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainController.getProductToModify();
        addPartTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        sendProductsToProductForm(selectedProduct);


        /**
         * Populating the data for the associates parts table
         */
        associatedParts = observableArrayList(selectedProduct.getAllAssociatedParts());
        associatedPartTableView.setItems(associatedParts);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
















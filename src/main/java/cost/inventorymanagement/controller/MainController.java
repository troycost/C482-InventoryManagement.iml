package cost.inventorymanagement.controller;

import cost.inventorymanagement.model.Inventory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import cost.inventorymanagement.model.Part;
import cost.inventorymanagement.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The Main controller class.
 */
public class MainController implements Initializable {

    @FXML
    private Label addProductLabel;
    @FXML
    private Button addPartsButton1;
    @FXML
    private Label partsLabel;
    @FXML
    private Label productsLabel;
    @FXML
    private AnchorPane InventoryManagementForm;

//   Tables,columns, fields, and buttons from Scene Builder for products

    @FXML
    private TextField productSearchTxtFld;
    @FXML
    private TableColumn<Product, Double> productsPriceCostPerUnitColumn;
    @FXML
    private TableColumn<Product, Integer> productCurrentStockColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableView<Product> productsTableView;

//   Tables,columns, fields, and buttons from Scene Builder for parts

    @FXML
    private Button exitButton;

    @FXML
    private Button modifyPartButton;
    @FXML
    private Button addPartsButton;
    @FXML
    private TableColumn<Part, Double> partsPriceCostPerUnitColumn;
    @FXML
    private TableColumn<Part, String> partsNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsCurrentStockColumn;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partsIDColumn;

    @FXML
    private TextField partSearchTxtFld;

    /**
     * The part object selected in the table view by the user.
     */
    private static Part partToModify;

    /**
     * The product object selected in the table view by the user.
     */
    private static Product productToModify;

    /**
     * This method populate parts' and products' table temporary data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        Populate parts' table temporary data

        partsTableView.setItems(Inventory.getAllParts());
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsCurrentStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

//        Populate products' table temporary data

        productsTableView.setItems(Inventory.getAllProducts());
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsPriceCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCurrentStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    /**
     * Initiates a search based on value in parts search text field and updates the parts
     * The product table view will display the search results.
     *
     * @param actionEvent Part search button action.
     */
    public void onPartSearch(ActionEvent actionEvent) throws IOException {
        String searchPartsString = partSearchTxtFld.getText();

        if (searchPartsString.isBlank()) {

            partsTableView.setItems(Inventory.getAllParts());
        } else {

            try {

                Part part = Inventory.lookupPart(Integer.parseInt(searchPartsString));

                if (part != null) {
                    partsTableView.getSelectionModel().select(part);
                } else {

                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {


                ObservableList<Part> partsLocated = Inventory.lookupPart(searchPartsString);

                if (partsLocated.size() > 0) {
                    partsTableView.setItems(partsLocated);

                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("uh oh, we've encountered an error!");
                    alert.setContentText("Please enter a valid part ID or name.");
                    alert.showAndWait();
                    partsTableView.setItems(Inventory.getAllParts());
                }
            }
        }

    }

    /**
     * Loads the add part form.
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/cost/inventorymanagement/addPartForm.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Part Form");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Gets the part object selected by the user in the part table.
     *
     * @return A part object, null if no part selected.
     */

    public static Part getPartToModify() {
        return partToModify;
    }


    /**
     * Loads the modify part form.
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part modifyPart = partsTableView.getSelectionModel().getSelectedItem();
        if (modifyPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();

        } else {

            partToModify = partsTableView.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/cost/inventorymanagement/modifyPartForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Modify Part Form");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        }
    }

    /**
     * This method selects the part to be deleted and verifies before its deleted
     *
     * @param actionEvent
     */
    public void onDeletePart(ActionEvent actionEvent) {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
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
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * This method closes the application when the button is clicked.
     *
     * @param actionEvent
     */
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Loads the add product form
     */
    public void onAddProd(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/cost/inventorymanagement/addProductForm.fxml"));
        Parent scene = loader.load();
        Scene s = new Scene(scene);
        AddProductController apController = loader.getController();

        apController.sendPartsToProduct(partsTableView.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(s);
        stage.setTitle("Add Product Form");
        stage.show();
    }

    /**
     * Gets the product object selected by the user in the product table.
     *
     * @return A part object, null if no product selected.
     */
    public static Product getProductToModify() {
        return productToModify;
    }


    /**
     * Loads modify product form.
     * If the user does not select a product then an error message will display.
     */
    public void onModifyProd(ActionEvent actionEvent) throws IOException {

        productToModify = productsTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();

        } else {

            Parent root = FXMLLoader.load(getClass().getResource("/cost/inventorymanagement/modifyProductForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Modify Product Form");
            stage.setScene(new Scene(root));
            stage.show();


        }
    }

    /**
     * This method selects the product to be deleted and verifies if the user really wants to delete the record.
     *
     * @param actionEvent
     */
    public void onDeleteProd(ActionEvent actionEvent) {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please select a product part to delete.");

            alert.showAndWait();
        }

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("uh oh, we've encountered an error!");
            alert.setContentText("Please delete the associated parts first, before deleting this product.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected Part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /**
     * Initiates a search based on value in products' search text field and updates the products to show items that contain searched information.
     *
     * @param actionEvent Product search button action.
     */
    public void onProdSearch(ActionEvent actionEvent) {

        String searchString = productSearchTxtFld.getText();
        if (searchString.isBlank()) {
            productsTableView.setItems(Inventory.getAllProducts());
        } else {
            try {
                Product p = Inventory.lookupProduct(Integer.parseInt(searchString));
                if (p != null) {
                    productsTableView.getSelectionModel().select(p);
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {

                ObservableList<Product> productsLocated = Inventory.lookupProduct(searchString);
                if (productsLocated.size() > 0) {
                    productsTableView.setItems(productsLocated);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("uh oh, there was an error!");
                    alert.setContentText("Please enter a valid product ID or name.");
                    alert.showAndWait();
                    productsTableView.setItems(Inventory.getAllProducts());
                }
            }
        }

    }
}




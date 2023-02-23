module cost.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens cost.inventorymanagement to javafx.fxml;
    exports cost.inventorymanagement;
    exports cost.inventorymanagement.controller;
    opens cost.inventorymanagement.controller to javafx.fxml;
    exports cost.inventorymanagement.model;
    opens cost.inventorymanagement.model to javafx.fxml;
}
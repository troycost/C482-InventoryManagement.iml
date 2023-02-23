package cost.inventorymanagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Product class.
 */
public class Product {
    /**
     * The observable list to store the associated part elements.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The product's ID.
     */
    private int id;
    /**
     * The product's name.
     */
    private String name;
    /**
     * The product's price.
     */
    private double price;

    /**
     * The product's inventory level.
     */
    private int stock;

    /**
     * The minimum inventory level of the product.
     */
    private int min;

    /**
     * The maximum inventory level of the product.
     */
    private int max;

    /**
     * The constructor for the  new instance of a product
     *
     * @param id    The ID for the product.
     * @param name  The name of the product.
     * @param price The price of the product.
     * @param stock The inventory level of the product.
     * @param min   The products minimum inventory level.
     * @param max   The maximum inventory level for the product.
     */

    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The constructor used to assign the associated parts to the product.
     *
     * @param id              the ID for the product.
     * @param name            The name of the product.
     * @param price           The price of the product.
     * @param stock           The inventory level of the product.
     * @param min             The products minimum inventory level.
     * @param max             The maximum inventory level for the product.
     * @param associatedParts The associated parts of the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }


    /**
     * The getter to retrieve the product's ID.
     *
     * @return The product's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for storing the ID.
     *
     * @param id The product id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter to retrieve the product's name.
     *
     * @return The product's name.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for storing the product's name.
     *
     * @param name The name of the product to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter to retrieve the product's price.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * The setter for storing the product's price.
     *
     * @param price The product's price to be set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * The getter for the product's inventory level.
     *
     * @return The amount of product in-stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * The setter for storing the product's inventory level.
     *
     * @param stock The in-stock number set for the inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The getter for the minimum number of product(s).
     *
     * @return The min number of the product(s) in inventory.
     */
    public int getMin() {
        return min;
    }

    /**
     * The setter for the minimum number of product(s).
     *
     * @param min Store the minimum number of product(s) in inventory.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The getter for the maximum number of product(s).
     *
     * @return Store the maximum number of product(s) in inventory.
     */
    public int getMax() {
        return max;
    }

    /**
     * The setter for the maximum number of product(s).
     *
     * @param max Store the maximum number of product(s) in inventory.
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * Adds a part to the associated parts list for the product.
     *
     * @param part The part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes the product's associated part from the list.
     * *
     *
     * @param selectedAssociatedPart The selected part to be deleted.
     * @return A boolean providing the status of the part deletion request.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else
            return false;
    }

    /**
     * Get a list of the product's associated parts.
     *
     * @return A list of  associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
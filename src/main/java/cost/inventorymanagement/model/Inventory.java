package cost.inventorymanagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class that extends to the part class.
 */
public class Inventory {
    /**
     * List of all parts.
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List of all products.
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();


    private static int partID = 1;
    private static int productID = 1000;


    /**
     * This method is used to autogenerate part IDs.
     */

    public static int getPartID() {
        return partID++;
    }

    /**
     * This method is used to autogenerate product IDs.
     */
    public static int getProductID() {
        return productID++;
    }


    /**
     * Adds a part to the allParts list.
     *
     * @param newPart The part to be added to the list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * The observable list to get all parts.
     *
     * @return All parts in the observable list are returned.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Adds a product to the allProducts list.
     *
     * @param newProduct The product to be added to the list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * The observable list to get all products.
     *
     * @return All products in the observable list are returned.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method looks up the part ID entered in the search text field and selects the part.
     *
     * @param partId The selected part ID is returned as a highlighted row.
     * @return
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (partId == part.getId()) {
                return part;
            }
        }
        return null;
    }

    /**
     * This method looks up the product that has been entered in the search shows the product that contains information entered.
     *
     * @param productId The selected product ID is returned as a highlighted row.
     * @return
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (productId == product.getId()) {
                return product;
            }
        }
        return null;
    }

    /**
     * This method looks up the part name entered in the search text field and shows the part with the information enter
     *
     * @param searchPartName The selected part name is returned as a highlighted row.
     * @return
     */
    public static ObservableList lookupPart(String searchPartName) {
        ObservableList<Part> locatedParts = FXCollections.observableArrayList();

        if (searchPartName.length() == 0) {
            locatedParts = allParts;
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().toLowerCase().contains(searchPartName.toLowerCase())) {
                    locatedParts.add(allParts.get(i));
                }
            }
        }

        return locatedParts;
    }

    /**
     * This method looks up the product name entered in the search text field and selects the product.
     *
     * @param searchProductName The selected product name is returned as a highlighted row.
     * @return
     */
    public static ObservableList lookupProduct(String searchProductName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if (searchProductName.length() == 0) {
            foundProducts = allProducts;
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName().toLowerCase().contains(searchProductName.toLowerCase())) {
                    foundProducts.add(allProducts.get(i));
                }
            }
        }

        return foundProducts;
    }

    /**
     * This method retrieves the selected part to be updated.
     *
     * @param index        The array index of the selected part.
     * @param selectedPart The selected part in the index location.
     */

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method retrieves the selected product to be updated.
     *
     * @param index
     * @param selectedProduct The selected product in the index location.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * This method updates retrieves the selected part to be deleted.
     *
     * @param selectedPart The selected part to be deleted.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This method updates retrieves the selected product to be deleted.
     *
     * @param selectedProduct The selected product to be deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
}



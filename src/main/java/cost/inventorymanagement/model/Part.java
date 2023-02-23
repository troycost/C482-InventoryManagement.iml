package cost.inventorymanagement.model;
/**
 *
 * @author Troy Cost Student ID: 002197948, Software I - C482
 */

/**
 * This is the part class.
 */
public abstract class Part {
    /**
     * The part's ID.
     */
    private int id;
    /**
     * The part's name.
     */
    private String name;
    /**
     * The part's price.
     */
    private double price;

    /**
     * The part's inventory level.
     */
    private int stock;

    /**
     * The minimum inventory level of the part.
     */
    private int min;

    /**
     * The maximum inventory level of the part.
     */
    private int max;

    /**
     * The constructor for the new instance of a part.
     *
     * @param id    The ID for the part.
     * @param name  The name of the part.
     * @param price The price of the part.
     * @param stock The inventory level of the part.
     * @param min   The parts minimum inventory level.
     * @param max   The maximum inventory level for the part.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * The getter to retrieve the part's ID.
     *
     * @return The part's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for storing the ID.
     *
     * @param id The part id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter to retrieve the part's name.
     *
     * @return The part's name.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for storing the part's name.
     *
     * @param name The name of the part to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter to retrieve the part's price.
     *
     * @return The price of the part.
     */
    public double getPrice() {
        return price;
    }

    /**
     * The setter for storing the part's price.
     *
     * @param price The part's price to be set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * The getter for the part's inventory level.
     *
     * @return The amount of parts in-stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * The setter for storing the part's inventory level.
     *
     * @param stock The in-stock number set for the inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The getter for the minimum number of part(s).
     *
     * @return The min number of the part(s) in inventory.
     */
    public int getMin() {
        return min;
    }

    /**
     * The setter for the minimum number of part(s).
     *
     * @param min Store the minimum number of part(s) in inventory.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The getter for the maximum number of part(s).
     *
     * @return Store the maximum number of part(s) in inventory.
     */
    public int getMax() {
        return max;
    }

    /**
     * The setter for the maximum number of part(s).
     *
     * @param max Store the maximum number of part(s) in inventory.
     */
    public void setMax(int max) {
        this.max = max;
    }

}
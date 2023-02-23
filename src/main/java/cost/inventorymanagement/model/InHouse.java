package cost.inventorymanagement.model;

/**
 * The InHouse class that extends to the Part class.
 */
public class InHouse extends Part {
    /**
     * The part machine ID
     */
    int machineID;

    /**
     * The constructor for a new instance of an InHouse object.
     *
     * @param id        The part's ID.
     * @param name      The part's name.
     * @param price     The part's price.
     * @param stock     The part's inventory level.
     * @param min       The part's minimum level.
     * @param max       The part's maximum level.
     * @param machineId The part's machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineID = machineId;
    }


    /**
     * The getter to retrieve the machine ID.
     *
     * @return The machine ID of the in-house part.
     */
    public int getMachineId() {
        return machineID;
    }

    /**
     * The setter for setting the machine ID
     *
     * @param machineId The stored machine ID of the in-house part.
     */
    public void setMachineId(int machineId) {
        this.machineID = machineID;
    }
}

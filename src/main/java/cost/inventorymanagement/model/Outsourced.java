package cost.inventorymanagement.model;

/**
 * The Outsourced class that extends to the part class.
 */
public class Outsourced extends Part {
    String companyName;

    /**
     * This is the constructor for the new instance of an outsourced part.
     *
     * @param id          The ID for the part.
     * @param name        The name of the part.
     * @param price       The price of the part.
     * @param stock       The inventory level of the part.
     * @param min         The parts minimum inventory level.
     * @param max         The maximum inventory level for the part.
     * @param companyName The company name for the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The getter to get the company's name for the outsourced part.
     *
     * @return The outsourced part's company name.
     */

    public String getCompanyName() {
        return companyName;
    }

    /**
     * The setter for setting the company's name for the outsourced part.
     *
     * @param companyName The outsourced part's company name to be set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

public class DataRecord {
    private String customerName;
    private double totalSales;

    public DataRecord(String customerName, double totalSales) {
        this.customerName = customerName;
        this.totalSales = totalSales;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalSales() {
        return totalSales;
    }
}

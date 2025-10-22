package OOPs;

public class Program{
    public static void main(String[] args){
        StandardShipping s1 = new StandardShipping("Delhi", 12);
        StandardShipping s2 = new StandardShipping("Banglore", 7);
        ExpressShipping s3 = new ExpressShipping("Pune", 4);
        StorePickup s4 = new StorePickup("Mumbai", 12);

        ShipmentService service = new ShipmentService();
        service.addShipment(s1);
        service.addShipment(s2);
        service.addShipment(s3);
        service.addShipment(s4);

        service.displayAllShipments();
        System.out.println("Total Cost : "+service.calculateTotalShippingCost());
    }
}
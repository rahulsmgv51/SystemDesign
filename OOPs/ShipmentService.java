package OOPs;

import java.util.ArrayList;
import java.util.List;

public class ShipmentService {
    private List<ShippingMethod> shipments = new ArrayList<>();

    public void addShipment(ShippingMethod shipment){
        shipments.add(shipment);
    }

    public void displayAllShipments(){
        for(ShippingMethod shipment : shipments){
            System.out.println(shipment.getDetails());
        }
    }

    public double calculateTotalShippingCost(){
        double totalCost = 0;
        for(ShippingMethod shipment : shipments){
            totalCost += shipment.calculateCost();
        }
        return totalCost;
    }
}

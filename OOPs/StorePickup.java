package OOPs;

public class StorePickup extends ShippingMethod{

    public StorePickup(String destination, double weight){
        super(destination, weight);
    }

    @Override
    public double calculateCost(){
        // Fixed Cost -> Rs. 15 per Kg.
        return 0;
    }

    @Override
    public String getDetails(){
        return "Store  Shipping to "+ destination + " Price for weight "+weight + " kg. : "+calculateCost()+" Rs.";
    }
}

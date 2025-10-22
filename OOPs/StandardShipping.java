package OOPs;

public class StandardShipping extends ShippingMethod{

    public StandardShipping(String destination, double weight){
        super(destination, weight);
    }

    @Override
    public double calculateCost(){
        // Fixed Cost -> Rs. 15 per Kg.
        return weight * 15;
    }

    @Override
    public String getDetails(){
        return "Standerd Shipping to "+ destination + " Price for weight "+weight + " kg. : "+calculateCost()+" Rs.";
    }
}

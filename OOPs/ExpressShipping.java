package OOPs;

public class ExpressShipping extends ShippingMethod{
    
    public ExpressShipping(String destination, double weight){
       super(destination, weight);
    }

    @Override
    public double calculateCost(){
        // Fixed Cost -> Rs. 30 per Kg.
        return weight * 30;
    }

    @Override
    public String getDetails(){
        return "Express Shipping to "+ destination + " Price for weight "+weight + " kg. : "+calculateCost()+" Rs.";
    }
}

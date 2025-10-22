package SOLID_Principle.solid_principles_java_code.dip;

public class SMSService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}
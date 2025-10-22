package SOLID_Principle.solid_principles_java_code.dip;

public class EmailService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("Sending Email: " + message);
    }
}
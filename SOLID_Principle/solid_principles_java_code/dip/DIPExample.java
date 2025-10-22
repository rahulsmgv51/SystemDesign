package SOLID_Principle.solid_principles_java_code.dip;

public class DIPExample {
    public static void main(String[] args) {
        MessageService email = new EmailService();
        MessageService sms = new SMSService();

        NotificationService emailNotification = new NotificationService(email);
        NotificationService smsNotification = new NotificationService(sms);

        emailNotification.notifyUser("Welcome to SOLID Principles!");
        smsNotification.notifyUser("Dependency Inversion in Action!");
    }
}
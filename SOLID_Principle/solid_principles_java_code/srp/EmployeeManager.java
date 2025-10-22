package SOLID_Principle.solid_principles_java_code.srp;

public class EmployeeManager {
    private EmployeeRepository repository = new EmployeeRepository();
    private ReportService reportService = new ReportService();
    private EmailService emailService = new EmailService();

    public void onboardEmployee(String name) {
        repository.addEmployee(name);
        reportService.generateReport(name);
        emailService.sendEmail(name);
    }
}
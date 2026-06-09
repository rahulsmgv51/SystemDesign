package DesignPatteren.Singleton;

//Singleton Pattern — Thread-Safe with Double-Checked Locking (DCL)
public class Singleton {

    // volatile is mandatory for DCL
    private static volatile Singleton instance;

    // Private constructor prevents external instantiation
    private Singleton() {
        System.out.println("Singleton Instance Created");
    }

    public static Singleton getInstance() {

        // First Check (No Lock)
        if (instance == null) {

            synchronized (Singleton.class) {

                // Second Check (With Lock)
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton");
    }
}
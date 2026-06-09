package DesignPatteren.Singleton;

public class SingletonPatternDemo {
    public static void main(String[] args) {
        System.out.println("Singleton pattern demo");
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        Singleton s3 = Singleton.getInstance();

        s1.showMessage();

        System.out.println("s1 HashCode : " + s1.hashCode());
        System.out.println("s2 HashCode : " + s2.hashCode());
        System.out.println("s3 HashCode : " + s3.hashCode());

        System.out.println("Same Object : " + (s1 == s2));
    }
}

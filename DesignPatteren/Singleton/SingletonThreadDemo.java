package DesignPatteren.Singleton;

//This proves that only one instance is created even when multiple threads access it simultaneously.
public class SingletonThreadDemo {

    public static void main(String[] args) {

        Runnable task = () -> {

            Singleton singleton = Singleton.getInstance();

            System.out.println(Thread.currentThread().getName() + " -> " + singleton.hashCode());
        };

        for (int i = 1; i <= 10; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }
}

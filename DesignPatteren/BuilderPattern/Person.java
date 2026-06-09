package DesignPatteren.BuilderPattern;
public class Person {

    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;
    private final String phoneNumber;
    private final String address;

    private Person(PersonBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class PersonBuilder {

        private String firstName;
        private String lastName;
        private int age;
        private String email;
        private String phoneNumber;
        private String address;

        public PersonBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PersonBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Person build() {

            if (firstName == null || firstName.isBlank()) {
                throw new IllegalArgumentException(
                        "First Name is mandatory");
            }

            if (lastName == null || lastName.isBlank()) {
                throw new IllegalArgumentException(
                        "Last Name is mandatory");
            }

            if (age < 0) {
                throw new IllegalArgumentException(
                        "Age cannot be negative");
            }

            return new Person(this);
        }
    }
}
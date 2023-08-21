// this is the Customer class that will store the necessary information of a customer
class Customer {
    // the necessary attributes of a Customer
    String name;
    String contactNumber;
    String streetAddress;
    String city;
    String email;

    // the constructor for Customer
    Customer(String name, String contactNumber, String streetAddress, String city,
            String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.streetAddress = streetAddress;
        this.city = city;
        this.email = email;
    }    
}
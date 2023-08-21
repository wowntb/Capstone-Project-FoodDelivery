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

    /*
     * this is a nested/inner class that will contain the details of the meal order.
     * I watched this YT video to learn about inner classes:
     * https://www.youtube.com/watch?v=BwkmIXjWWJc&t=263s
     */
    class Order {
        // the attributes of the Order object
        String orderNumber;
        String meal;
        double price;
        int quantity;
        String instructions;

        Order(String orderNumber, String meal, double price, int quantity, String instructions) {
            this.orderNumber = orderNumber;
            this.meal = meal;
            this.price = price;
            this.quantity = quantity;
            this.instructions = instructions;
        }

        // this method will calculate the total of the order
        double calculateTotal() {
            double total;
            total = price * quantity;
            return total;
        }
    }
}
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// this program must take input for the information of the customer and restaurant, see if there are drivers in the area, and produce an invoice
class Main {
    public static void main(String[] args) {
        try {
            File drivers = new File("./drivers.txt");
            // this scanner will read data from the drivers.txt file
            Scanner driversScanner = new Scanner(drivers);
            // this scanner object will be used to read input from the console
            Scanner consoleScanner = new Scanner(System.in);
            // creates the invoice text file
            Formatter invoice = new Formatter("./invoice.txt");
            // a Customer object is constructed without specified values
            Customer theCustomer = new Customer(null, null, null, null, null);
            // a Restaurant object is constructed without specified values
            Restaurant theRestaurant = new Restaurant(null, null, null);

            /*
             * the user will first be prompted to input the locations of the customer and
             * restaurant. inputs are stored lowercase so that they are not case-sensitive.
             */
            System.out.println("In which city is the customer?");
            theCustomer.city = consoleScanner.nextLine().toLowerCase();
            System.out.println("In which city is your restaurant?");
            theRestaurant.city = consoleScanner.nextLine().toLowerCase();

            List<String[]> availableDrivers = getAvailableDrivers(driversScanner, theCustomer, theRestaurant);

            formatInvoice(consoleScanner, invoice, theCustomer, theRestaurant, availableDrivers);

            consoleScanner.close();
            driversScanner.close();
            invoice.close();

        } catch (FileNotFoundException e) {
            /*
             * here is the 1st catch. this handles the exception thrown when the drivers.txt
             * file can't be found.
             */
            System.out.println(e.getMessage());
        } catch (SecurityException e) {
            /*
             * here is the 2nd cathc. this will handlt the excpetion thrown when there are
             * access problems to the drivers.txt file.
             */
            System.out.println(e.getMessage());
        }
    }

    /*
     * this method will gather more info from the user and determine how the invoice
     * will be formatted.
     */
    static void formatInvoice(Scanner consoleScanner, Formatter invoice, Customer theCustomer,
            Restaurant theRestaurant, List<String[]> availableDrivers) {
        if (availableDrivers.isEmpty()) {
            /*
             * if there are no drivers in the availableDrivers list then the invoice will
             * notify the user that none were found in their area.
             */
            invoice.format("Sorry! Our drivers are too far away from you to be able to deliver to your location.");
        } else {
            /*
             * if the availableDrivers list is not empty, the user will continue to fill
             * out the rest of the details of the customer & restaurant
             */
            System.out.println("What is the customer's name?");
            theCustomer.name = consoleScanner.nextLine();
            System.out.println("Provide the customer's contact number:");
            theCustomer.contactNumber = consoleScanner.nextLine();
            System.out.println("What is the customer's street address?");
            theCustomer.streetAddress = consoleScanner.nextLine();
            System.out.println("What is the customer's email address?");
            theCustomer.email = consoleScanner.nextLine();

            System.out.println("What is the name of the restaurant?");
            theRestaurant.name = consoleScanner.nextLine();
            System.out.println("Provide the contact number of the restaurant:");
            theRestaurant.contactNumber = consoleScanner.nextLine();

            // an order is constructed for the details of the meal order
            Order theOrder = new Order(null, null, 0, 0, null);
            System.out.println("Provide the order number:");
            theOrder.orderNumber = consoleScanner.nextLine();
            System.out.println("What meal is being ordered?");
            theOrder.meal = consoleScanner.nextLine();
            System.out.println("What is the price of the item?");
            theOrder.price = Double.parseDouble(consoleScanner.nextLine());
            System.out.println("How many are being ordered?");
            theOrder.quantity = Integer.parseInt(consoleScanner.nextLine());
            System.out.println("Are there any special instructions for this order?");
            theOrder.instructions = consoleScanner.nextLine();

            /*
             * lowestLoad initially assumes that the 1st element in the list is the driver
             * with the least load. index [2] is the weight element of the array.
             */
            int lowestLoad = Integer.parseInt(availableDrivers.get(0)[2]);
            String[] lowestLoad_driver = availableDrivers.get(0);
            /*
             * if there is more than 1 element in list they will be compared using a for
             * loop.
             */
            if (availableDrivers.size() > 1) {
                for (int i = 1; i < availableDrivers.size(); i++) {
                    /*
                     * index (i) iterates through different drivers while index [2] returns the
                     * city of that driver.
                     */
                    int temporary_load = Integer.parseInt(availableDrivers.get(i)[2]);
                    /*
                     * if the temporary load is less than lowerLoad the temporary load will be the
                     * new lowestLoad and lowestLoad_driver will get the index where the new
                     * lighter load was found for future reference.
                     */
                    if (temporary_load < lowestLoad) {
                        lowestLoad = Integer.parseInt(availableDrivers.get(i)[2]);
                        lowestLoad_driver = availableDrivers.get(i);
                    }
                }
            }

            /*
             * this block prints out a structured invoice with details of the order,
             * customer, and restaurant for the user.
             */
            invoice.format("Order number: %s\n", theOrder.orderNumber);
            invoice.format("Customer: %s\n", theCustomer.name);
            invoice.format("Email: %s\n", theCustomer.email);
            invoice.format("Phone number: %s\n", theCustomer.contactNumber);
            invoice.format("Location: %s\n\n", theCustomer.city);
            invoice.format("You have ordered the following from %s in %s:\n\n", theRestaurant.name,
                    theRestaurant.city);
            invoice.format("%s x %s (R%s)\n\n", theOrder.quantity, theOrder.meal, theOrder.price);
            invoice.format("Special instructions: %s\n\n", theOrder.instructions);
            invoice.format("Total: R%s\n\n", theOrder.calculateTotal());
            invoice.format(
                    "%s is nearest to the restaurant so they will be delivering your order at:\n\n%s\n%s\n\n",
                    lowestLoad_driver[0], theCustomer.streetAddress, theCustomer.city);
            invoice.format("If you need to contact the restaurant, their number is %s.",
                    theRestaurant.contactNumber);
        }
    }

    // this method creates the list of available drivers.
    static List<String[]> getAvailableDrivers(Scanner driversScanner, Customer theCustomer,
            Restaurant theRestaurant) {
        /*
         * this array list will store the lines of the drivers with cities matching the
         * restaurant's and customer's
         */
        List<String[]> availableDrivers = new ArrayList<String[]>();
        // this do-while block will go through drivers.txt
        while (driversScanner.hasNextLine()) {
            // each line will be stored as the variable "line"
            String line = driversScanner.nextLine();
            /*
             * the line will be converted to an array of strings so that specific parts of
             * it can be accessed (the city in this case, and the load later). the elements
             * of the array are separated by comma + space because of the format of the
             * drivers.txt file.
             */
            String[] lineAsArray = line.split(", ");

            /*
             * if the line's city (lineAsArray[1]) matches the city of the customer AND
             * restaurant, that line will be added to the list of available drivers
             */
            if (lineAsArray[1].toLowerCase().equals(theCustomer.city)
                    && lineAsArray[1].toLowerCase().equals(theRestaurant.city)) {
                availableDrivers.add(lineAsArray);
            }
        }
        return availableDrivers;
    }
}
import org.nocrala.tools.texttablefmt.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    enum Color {
        ANSI_RED("\u001B[31m"),
        ANSI_BLACK("\u001B[30m");

        private final String color;

        Color(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    // Initialize Scanner
    private static final Scanner scanner = new Scanner(System.in);

    // Initialize Buses
    private static int numberOfBuses;
    private static int numberSeatsPerBus;
    private static int[][] buses;
    private static int availableSeats;
    private static int unavailableSeats;

    public static void main(String[] args) {

        // Initialize Scanner

        System.out.println("-------------- Setting up Buses --------------");

        System.out.print("-> Enter number of Buses: ");
        while (!scanner.hasNext("[1-9]*")) {
            if (scanner.hasNext("[0-9]")) {
                System.out.println("");
            }
            System.out.println(Color.ANSI_RED.getColor() + "-> Error: Invalid Input. Please enter only numeric values." + Color.ANSI_BLACK.getColor());
            System.out.print("-> Enter number of Buses: ");
            scanner.next();
        }
        numberOfBuses = scanner.nextInt();

        System.out.print("-> Enter number Seat of bus: ");
        while (!scanner.hasNext("[1-9]*")) {
            System.out.println("Error: Invalid number Seat of bus.");
            System.out.print("-> Enter number Seat of bus: ");
            scanner.next();
        }
        numberSeatsPerBus = scanner.nextInt();

        // Initialize Buses
        buses = new int[numberOfBuses][numberSeatsPerBus];

        int option;

        do {
            System.out.println("-------------- Bus Management System --------------");
            System.out.println("1- Check Bus");
            System.out.println("2- Booking Bus");
            System.out.println("3- Cancel Booking");
            System.out.println("4- Reset Bus");
            System.out.println("5- Exit");
            System.out.println("---------------------------------------------------");
            System.out.print("-> Choose option(1-5): ");
            while (!scanner.hasNext("[1-9]*")) {
                System.out.println("Error: Invalid Input. Please enter only numeric values.");
                System.out.print("-> Choose option(1-5): ");
                scanner.next();
            }
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayAllBusInformation();
                    break;
                case 2:
                    bookingBus();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    resetBus();
                    break;
                case 5:
                    System.out.println("-> Good bye!");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != 5);

    }

    private static void resetBus() {
        System.out.print("-> Enter bus’s Id: ");
        int busId = scanner.nextInt();

        displayBusInformation(busId);

        System.out.print("=> Do you want to reset bus " + busId + "? (y/n): ");
        char choice = scanner.next().charAt(0);

        if (choice == 'y' || choice == 'Y') {
            for (int i = 0; i < numberSeatsPerBus; i++) {
                buses[busId - 1][i] = 0;
            }
            System.out.println("Bus " + busId + " was reset successfully!");
        }
    }

    private static void cancelBooking() {
        System.out.print("-> Enter bus’s Id: ");
        int busId = scanner.nextInt();

        displayBusInformation(busId);

        System.out.print("-> Enter Chair number to cancel booking: ");
        int chairNumber = scanner.nextInt();

        System.out.print("=> Do you want to cancel booking chair number " + chairNumber + "? (y/n): ");
        char choice = scanner.next().charAt(0);

        if (choice == 'y' || choice == 'Y') {
            if (buses[busId - 1][chairNumber - 1] == 1) {
                buses[busId - 1][chairNumber - 1] = 0;
                System.out.println("Chair number " + chairNumber + " was canceled booking successfully!");
            } else {
                System.out.println("Error: Chair number " + chairNumber + " is already available.");
            }
        }
    }

    private static void bookingBus() {
        System.out.print("-> Enter bus’s Id: ");
        int busId = scanner.nextInt();

        displayBusInformation(busId);

        System.out.print("-> Enter Chair number to booking: ");
        int chairNumber = scanner.nextInt();

        System.out.print("=> Do you want to book chair number " + chairNumber + "? (y/n): ");
        char choice = scanner.next().charAt(0);

        if (choice == 'y' || choice == 'Y') {
            if (buses[busId - 1][chairNumber - 1] == 0) {
                buses[busId - 1][chairNumber - 1] = 1;
                System.out.println("Chair number " + chairNumber + " was booked successfully!");
            } else {
                System.out.println("Error: Chair number " + chairNumber + " is already booked.");
            }
        }

    }

    private static void displayAllBusInformation() {
        // ANSI codes for colors
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BLUE = "\u001B[34m";

        CellStyle numberCellStyle = new CellStyle(CellStyle.HorizontalAlign.center);

        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        // Add Header
        table.addCell("Display All Bus information", numberCellStyle, 4);

        table.addCell(ANSI_GREEN + "ID" + ANSI_GREEN, numberCellStyle);
        table.addCell(ANSI_GREEN + "Seat" + ANSI_GREEN, numberCellStyle);
        table.addCell(ANSI_GREEN + "Available" + ANSI_GREEN, numberCellStyle);
        table.addCell(ANSI_RED + "Unavailable" + ANSI_RED, numberCellStyle);

        table.setColumnWidth(0, 10, 15);
        table.setColumnWidth(1, 20, 25);
        table.setColumnWidth(2, 20, 25);
        table.setColumnWidth(3, 20, 25);

        // loop
        for (int i = 0; i < numberOfBuses; i++) {
            availableSeats = 0;
            unavailableSeats = 0;
            for (int j = 0; j < numberSeatsPerBus; j++) {
                if (buses[i][j] == 0) {
                    availableSeats++;
                } else {
                    unavailableSeats++;
                }
            }
            table.addCell(String.valueOf(i + 1), numberCellStyle);
            table.addCell(ANSI_BLUE + numberSeatsPerBus + ANSI_BLUE, numberCellStyle);
            table.addCell(ANSI_GREEN + availableSeats + ANSI_GREEN, numberCellStyle);
            table.addCell(ANSI_RED + (unavailableSeats) + ANSI_RED, numberCellStyle);
        }

        System.out.println("---------- Display All Bus information ----------");
        System.out.println(table.render());

        System.out.print("-> Enter bus’s Id: ");
        int busId = scanner.nextInt();

        if (busId > 0 && busId <= numberOfBuses) {
            displayBusInformation(busId);
        } else {
            System.out.println("Error: Invalid bus’s Id.");
        }
    }

    private static void displayBusInformation(int busId) {
        System.out.println("---------- Display Bus information ----------");

        availableSeats = 0;
        unavailableSeats = 0;

        for (int i = 0; i < numberSeatsPerBus; i++) {
            if (buses[busId - 1][i] == 0) {
                System.out.print("(+) ");
                availableSeats++;
            } else {
                System.out.print("(-) ");
                unavailableSeats++;
            }
            System.out.print((i + 1) + "\t\t");
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();

        System.out.println("(-) : Unavailable(" + unavailableSeats + ")" + "\t\t" + "(+) : Available(" + (availableSeats) + ")");

    }
}

import java.util.Scanner;

public class Main {
    private static Inventory inventory = new Inventory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStore Management System");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Search Item");
            System.out.println("4. Display Items");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    removeItem(scanner);
                    break;
                case 3:
                    searchItem(scanner);
                    break;
                case 4:
                    displayItems();
                    break;
                case 5:
                    saveToFile(scanner);
                    break;
                case 6:
                    loadFromFile(scanner);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Item item = new Item(name, category, quantity);
        inventory.addItem(item);
        System.out.println("Item added successfully.");
    }

    private static void removeItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        System.out.print("Enter quantity to remove: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        inventory.removeItem(name, category, quantity);
    }

    private static void searchItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();

        Item item = inventory.searchItem(name, category);
        if (item != null) {
            System.out.println("Item found: " + item);
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void displayItems() {
        System.out.println("Displaying all items:");
        inventory.displayItems();
    }

    private static void saveToFile(Scanner scanner) {
        System.out.print("Enter filename to save to: ");
        String filename = scanner.nextLine();
        inventory.saveToFile(filename);
        System.out.println("Items saved to file.");
    }

    private static void loadFromFile(Scanner scanner) {
        System.out.print("Enter filename to load from: ");
        String filename = scanner.nextLine();
        inventory.loadFromFile(filename);
        System.out.println("Items loaded from file.");
    }
}
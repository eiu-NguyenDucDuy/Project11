import java.io.*;
import java.util.*;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        for (Item i : items) {
            if (i.getName().equals(item.getName()) && i.getCategory().equals(item.getCategory())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(String name, String category, int quantity) {
        for (Item i : items) {
            if (i.getName().equals(name) && i.getCategory().equals(category)) {
                if (i.getQuantity() >= quantity) {
                    i.setQuantity(i.getQuantity() - quantity);
                    if (i.getQuantity() == 0) {
                        items.remove(i);
                    }
                } else {
                    System.out.println("Not enough quantity to remove.");
                }
                return;
            }
        }
        System.out.println("Item not found.");
    }

    public Item searchItem(String name, String category) {
        for (Item i : items) {
            if (i.getName().equals(name) && i.getCategory().equals(category)) {
                return i;
            }
        }
        return null;
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        for (Item i : items) {
            System.out.println(i);
        }
    }

    // Save items to a file
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item item : items) {
                writer.write(item.getName() + "," + item.getCategory() + "," + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Load items from a file
    public void loadFromFile(String filename) {
        items.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String category = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    items.add(new Item(name, category, quantity));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}

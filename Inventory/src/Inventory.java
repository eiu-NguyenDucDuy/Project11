import java.io.*;
import java.util.*;

public class Inventory {
    private Map<String, Item> items = new HashMap<>();
    private static final String FILE_PATH = "inventory.txt";

    public Inventory() {
        loadFromFile();
    }

    public void addItem(String name, String category, int quantity) {
        if (items.containsKey(name)) {
            Item item = items.get(name);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(name, new Item(name, category, quantity));
        }
        saveToFile();
    }

    public void removeItem(String name, int quantity) {
        if (items.containsKey(name)) {
            Item item = items.get(name);
            int currentQuantity = item.getQuantity();
            if (quantity >= currentQuantity)
                items.remove(name);
            else
                item.setQuantity(currentQuantity - quantity);
        }
        saveToFile();
    }

    public Item searchItem(String name) {
        return items.get(name);
    }

    public Collection<Item> getAllItems() {
        return items.values();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Item item : items.values()) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String name = parts[0];
                    String category = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    items.put(name, new Item(name, category, quantity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
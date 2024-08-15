import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Inventory inventory = new Inventory();
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private static JPanel displayPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Inventory Store Mangement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField("1");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton searchButton = new JButton("Search");
        JButton displayButton = new JButton("Display");

        inputPanel.add(new JLabel("Item Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Item Category"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Item Quantity"));
        inputPanel.add(quantityField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(searchButton);
        inputPanel.add(displayButton);

        mainPanel.add(inputPanel, "Main Menu");

        // Display panel
        displayPanel = new JPanel();
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        JButton backButton = new JButton("Back");

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.add(backButton);
        mainPanel.add(displayPanel, "Inventory");

        // Action listeners for buttons
        addButton.addActionListener(e -> addItem(nameField, categoryField, quantityField));
        removeButton.addActionListener(e -> removeItem(nameField, quantityField));
        searchButton.addActionListener(e -> searchItem(nameField));
        displayButton.addActionListener(e -> showInventory());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void addItem(JTextField nameField, JTextField categoryField, JTextField quantityField) {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String quantityText = quantityField.getText().trim();
        int quantity = 1;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item name cannot be empty.");
            return;
        }
        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item category cannot be empty.");
            return;
        }
        if (!quantityText.isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Setting quantity to 1.");
            }
        }
        inventory.addItem(name, category, quantity);
    }

    private static void removeItem(JTextField nameField, JTextField quantityField) {
        String name = nameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        int quantity = 1;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item name cannot be empty.");
            return;
        }
        if (!quantityText.isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Setting quantity to 1.");
            }
        }
        inventory.removeItem(name, quantity);
    }

    private static void searchItem(JTextField nameField) {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item name cannot be empty.");
            return;
        }
        inventory.searchItem(name);
    }

    private static void showInventory() {
        displayPanel.removeAll();
        JButton backButton = new JButton("Back");
        displayPanel.add(backButton);

        // Display Inventory
        Map<String, ArrayList<Item>> items = new HashMap<>();

        for (Item item : inventory.getAllItems())
            items.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);

        for (Map.Entry<String, ArrayList<Item>> display : items.entrySet()) {
            StringBuilder categoryDisplay = new StringBuilder("\nCategory: ").append(display.getKey()).append("\n");
            for (Item item : display.getValue()) {
                categoryDisplay.append("  Name: ").append(item.getName())
                        .append(", Quantity: ").append(item.getQuantity())
                        .append("\n");
            }
            displayPanel.add(new JLabel("<html>" + categoryDisplay.toString().replaceAll("\n", "<br/>") + "</html>"));
        }

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        cardLayout.show(mainPanel, "Inventory");
    }
}
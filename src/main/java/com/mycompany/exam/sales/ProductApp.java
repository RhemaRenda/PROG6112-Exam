package com.mycompany.exam.sales;

import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import javax.swing.*;

public class ProductApp extends JFrame {
    private final JTextField customerField = new JTextField(20);
    private final JTextField priceField = new JTextField(20);
    private final JComboBox<String> products = new JComboBox<>(new String[] {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    });

    public ProductApp() {
        super("Product App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Customer Name:"));
        form.add(customerField);
        form.add(new JLabel("Select Product:"));
        form.add(products);
        form.add(new JLabel("Enter Price:"));
        form.add(priceField);
        form.add(new JLabel());

        JButton processButton = new JButton("PROCESS");
        processButton.addActionListener(event -> processSale());
        form.add(processButton);

        add(form);
        setJMenuBar(createMenuBar());
        pack();
        setLocationRelativeTo(null);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> dispose());
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        processItem.addActionListener(event -> processSale());
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(event -> clearForm());
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        return menuBar;
    }

    private void clearForm() {
        customerField.setText("");
        priceField.setText("");
        products.setSelectedIndex(0);
        customerField.requestFocusInWindow();
    }

    private void processSale() {
        String customer = customerField.getText().trim();
        if (customer.isEmpty()) {
            showError("Enter the customer's name.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException error) {
            showError("Enter a valid price, for example 3500.");
            return;
        }

        ProductCalculations sale = new ProductCalculations(price);
        if (price <= 0 || !Double.isFinite(sale.GetTotal())) {
            showError("Enter a valid price greater than zero.");
            return;
        }

        String details = String.format(Locale.ROOT,
                "Customer Name: %s%nProduct: %s%nVAT: R %.2f%n"
                + "COMMISSION: R %.2f%nTOTAL: R %.2f",
                customer, products.getSelectedItem(),
                sale.GetVat(), sale.GetCommission(), sale.GetTotal());

        try {
            saveSale(details);
        } catch (IOException error) {
            showError("Could not save the sale to data.txt.");
            return;
        }

        JOptionPane.showMessageDialog(this, details, "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveSale(String details) throws IOException {
        Path file = Path.of("data.txt");
        String newLine = System.lineSeparator();
        String divider = "********************************" + newLine;
        String heading = "";

        // Write the heading once and append each sale below it.
        if (!Files.exists(file) || Files.size(file) == 0) {
            heading = "DATA LOG" + newLine + divider;
        }

        Files.writeString(file, heading + details + newLine + divider,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Product App",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}
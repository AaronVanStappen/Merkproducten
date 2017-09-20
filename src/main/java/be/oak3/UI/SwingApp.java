package be.oak3.UI;

import be.oak3.model.Product;
import be.oak3.persistence.Bestelling;
import be.oak3.persistence.BestellingImpl;
import be.oak3.persistence.Data;

import javax.swing.*;
import java.awt.*;

public class SwingApp extends JFrame {
    private JLabel lblTitle, lblResultInput, lblResultButton;
    private JList<Product> lstProducts;
    private JButton btnResult;

    public SwingApp() {
       initComponents();
       layoutComponents();
       initListeners();
    }
    // nieuwe componenten toevoegen
    private void initComponents() {
        setTitle("First Swingers' Application");
        setSize(500, 500);
        setLocation(100, 100);
        lblTitle = new JLabel("Swingers' Application");
        lstProducts = new JList<>();
        lstProducts.setListData(Data.getData().toArray(new Product[0]));
        btnResult = new JButton("Click your brains out!!!");
        lblResultInput = new JLabel();
        lblResultButton = new JLabel();
        setVisible(true);
    }
    // lay-out bepalen
    private void layoutComponents() {
        add(lblTitle, BorderLayout.NORTH);
        add(lstProducts, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(btnResult);
        southPanel.add(lblResultButton);
        southPanel.add(lblResultInput);
        add(southPanel, BorderLayout.SOUTH);
    }
    //ActionListeners aanmaken
    private void initListeners() {
        btnResult.addActionListener(e -> {
            lblResultButton.setText("ik heb geswingd!!!!");
            lblResultInput.setText("Swingen is fun");

            lblResultInput.setOpaque(true);
            lblResultButton.setOpaque(true);

            lblResultInput.setBackground(Color.BLUE);
            lblResultButton.setBackground(Color.RED);
        });
    }

    public static void main(String[] args) {
        new SwingApp();
    }
}

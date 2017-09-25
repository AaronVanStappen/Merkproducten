package be.oak3.UI;

import be.oak3.model.Product;
import be.oak3.persistence.Bestelling;
import be.oak3.persistence.BestellingImpl;
import be.oak3.persistence.BestellingImplDao;
import be.oak3.persistence.Data;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingApp extends JFrame {
    private JList<Product> lstProducts;
    private JLabel lblResult;
    private JButton btnSorteer, btnSorteerMerk, btnSorteerVolume, btnPerMerk, btnParfums, btnGoedkoop, btnDuurste, btnTotaal;
    private Bestelling bestelling;

    private SwingApp() {
       initComponents();
       layoutComponents();
       initListeners();
    }
    // nieuwe componenten toevoegen
    private void initComponents() {
        bestelling = new BestellingImplDao();
        setTitle("Merkproducten Application");
        setSize(500, 500);
        setLocation(100, 100);
        lstProducts = new JList<>();
        bestelling.sorteer();
        lstProducts.setListData(bestelling.getBestelling().toArray(new Product[0]));
        lblResult = new JLabel();
        btnSorteer = new JButton("sorteer");
        btnSorteerMerk = new JButton("sorteer op merk");
        btnSorteerVolume = new JButton("sorteer op volume");
        btnPerMerk = new JButton("toon per merk");
        btnParfums = new JButton("toon de parfums");
        btnGoedkoop = new JButton("toon goedkoopste producten");
        btnDuurste = new JButton("toon duurste product");
        btnTotaal = new JButton("bereken totale prijs");
        setVisible(true);
    }
    // lay-out bepalen
    private void layoutComponents() {
        add(lblResult, BorderLayout.NORTH);
        add(lstProducts, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(btnSorteer);
        southPanel.add(btnSorteerMerk);
        southPanel.add(btnSorteerVolume);
        southPanel.add(btnPerMerk);
        southPanel.add(btnParfums);
        southPanel.add(btnGoedkoop);
        southPanel.add(btnDuurste);
        southPanel.add(btnTotaal);
        add(southPanel, BorderLayout.SOUTH);
        pack();
    }
    // ActionListeners aanmaken
    private void initListeners() {
        btnSorteer.addActionListener(e -> {
            bestelling.sorteer();
            lstProducts.setListData(bestelling.getBestelling().toArray(new Product[0]));
        });
        btnSorteerMerk.addActionListener(e -> {
            bestelling.sorteerOpMerk();
            bestelling.getBestelling().forEach(System.out::println);
            lstProducts.setListData(bestelling.getBestelling().toArray(new Product[0]));
        });
        btnSorteerVolume.addActionListener(e -> {
            bestelling.sorteerOpVolume();
            lstProducts.setListData(bestelling.getBestelling().toArray(new Product[0]));
        });
        btnPerMerk.addActionListener(e -> {
            String merk = JOptionPane.showInputDialog("Geef een merk op:");
            lstProducts.setListData(bestelling.lijstVanBepaaldMerk(merk).toArray(new Product[0]));
        });
        btnParfums.addActionListener(e -> {
            lstProducts.setListData(bestelling.lijstVanParfums().toArray(new Product[0]));
        });
        btnGoedkoop.addActionListener(e -> {
            lstProducts.setListData(bestelling.lijstVanGoedkopeProducten().toArray(new Product[0]));
        });
        btnDuurste.addActionListener(e -> {
            lblResult.setText(bestelling.zoekDuursteProduct().toString());
        });
        btnTotaal.addActionListener(e -> {
            lblResult.setText(String.valueOf(bestelling.totalePrijs()));
        });
    }

    public static void main(String[] args) {
        new SwingApp();
    }
}

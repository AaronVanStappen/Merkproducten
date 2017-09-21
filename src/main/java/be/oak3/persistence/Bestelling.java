package be.oak3.persistence;

import be.oak3.model.Product;

import java.util.List;

public interface Bestelling {
    void voegProductToe(Product product);
    void sorteer();
    void sorteerOpMerk();
    void sorteerOpVolume();
    List<Product> lijstVanBepaaldMerk(String merk);
    List<Product> lijstVanParfums();
    List<Product> lijstVanGoedkopeProducten();
    Product zoekDuursteProduct();
    double totalePrijs();
    //extra method voor de klasse BestellingImplTest.java
    Product get(int i);
    //extra method voor de klasse SwingApp.java
    List<Product> getBestelling();
}

package be.oak3.persistence;

import be.oak3.model.Product;

import java.util.List;

public interface Bestelling {
    public void voegProductToe(Product product);
    public void sorteer();
    public void sorteerOpMerk();
    public void sorteerOpVolume();
    public List<Product> lijstVanBepaaldMerk(String merk);
    public List<Product> lijstVanParfums();
    public List<Product> lijstVanGoedkopeProducten();
    public Product zoekDuursteProduct();
    public double totalePrijs();
    //extra methods voor de klasse BestellingImplTest.java
    public Product get(int i);
    public List<Product> getList(int i);
}

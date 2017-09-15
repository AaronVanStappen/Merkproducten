package be.oak3.persistence;

import be.oak3.model.Product;

public interface Bestelling {
    public void voegProductToe(Product product);
    public void sorteer();
    public void sorteerOpMerk();
    public void sorteerOpVolume();
    public void toonPerMerk(String merk);
    public void toonParfums();
    public void toonGoedkopeProducten();
    public Product zoekDuursteProduct();
    public double totalePrijs();
}

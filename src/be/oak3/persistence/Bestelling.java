package be.oak3.persistence;

import be.oak3.model.Product;

public interface Bestelling {
    int PRODUCTNUMMER = 1000;

    public void voegProductToe(Product product);
    public void sorteer();
    public void sorteerOpMerk();
    public void sorteerOpVolume();
    public void toonPerMerk();
    public void toonGoedkopeProducten();
    public void zoekDuursteProduct();
}

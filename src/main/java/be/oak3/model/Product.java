package be.oak3.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public abstract class Product implements Comparator<Product>, Comparable<Product>{
    private int productNummer;
    private static int productNr = 1000;
    private String merk;
    private String naam;
    private int volume;
    private double prijs;

    public Product(int productNummer, String merk, String naam, int volume, double prijs) {
        this.productNummer = productNr;
        this.merk = merk;
        this.naam = naam;
        this.volume = volume;
        this.prijs = prijs;
        this.productNr++;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getMerk() {
        return merk;
    }

    public String getNaam() {
        return naam;
    }

    public int getVolume() {
        return volume;
    }

    public double getPrijs() {
        return prijs;
    }

    public String getProductCode() {
        return StringUtils.join(getMerk().substring(0,3), getNaam().substring(0,3),
                           getVolume()).replaceAll(" ", "_")
                          .toUpperCase();
    }

    public static Comparator<Product> sorteerOpMerknaam() {
        return Comparator.comparing(Product::getMerk);
    }

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getMerk().compareTo(o2.getMerk());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }

        Product p = (Product) o;
        return productNummer == p.getProductNummer();
    }

    @Override
    public int hashCode() {
        return productNummer;
    }

    @Override
    public int compareTo(Product p) {
        return productNummer - p.getProductNummer();
    }

    @Override
    public String toString() {
        return String.format("%d %s %-20s %10s %-24s %10s %3sml %8s %4.2f %5s %s",
                getProductNummer(), "Merk:", getMerk(), "Naam:", getNaam(), "Volume:", getVolume(),
                "Prijs:", getPrijs(), "Code:", getProductCode());
    }
}

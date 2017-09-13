package be.oak3.model;

import java.text.Format;
import java.util.Comparator;

public abstract class Product implements Comparator<Product>, Comparable<Product>{
    int productNummer;
    String merk;
    String naam;
    int volume;
    double prijs;

    public Product(int productNr, String merk, String naam, int volume, double prijs) {
        productNummer = productNr;
        this.merk = merk;
        this.naam = naam;
        this.volume = volume;
        this.prijs = prijs;
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

    public String getProductCode() {
        StringBuilder code = new StringBuilder();
        code.append(getMerk().toUpperCase().replace(' ', '_').substring(0,3))
            .append(getNaam().toUpperCase().replace(' ', '_').substring(0,3))
            .append(String.valueOf((getVolume())));
        /*for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == ' ') {
                code.setCharAt(i, '_');
            }
        }*/
        return code.toString();
    }


    public static Comparator<Product> sorteerOpMerknaam(Product p1, Product p2) {
        Comparator<Product> c = new Comparator;
        c.compare(p1,p2);
        return c;
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
        Format formatter;
        formatter.
        return productNummer + " " +
                "Merk: " + merk + "\t" +
                "Naam: " + naam + "\t" +
                "Volume: " + volume + "\t" +
                "Prijs: " + prijs + "\t" +
                "Code: " + getProductCode().toString() + "\t";
    }
}

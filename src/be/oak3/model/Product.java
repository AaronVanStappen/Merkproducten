package be.oak3.model;

import java.text.NumberFormat;
import java.util.Comparator;

public abstract class Product implements Comparator<Product>, Comparable<Product>{
    int productNummer;
    public static int productNr = 1000;
    public String merk;
    String naam;
    int volume;
    double prijs;

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

    public static Comparator<Product> sorteerOpMerknaam() {
        return Comparator.comparing(Product::getMerk);
        //return (p1, p2) -> p1.getMerk().compareTo(p2.getMerk());
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
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
            return productNummer + " " +
            "Merk: " + merk + "\t" +
            "Naam: " + naam + "\t" +
            "Volume: " + volume +"ml" + "\t" +
            "Prijs: " + formatter.format(prijs).toString() + "\t" +
            "Code: " + getProductCode().toString() + "\t";
    }
}

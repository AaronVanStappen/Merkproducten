package be.oak3.persistence;

import be.oak3.model.Parfum;
import be.oak3.model.Product;

import com.google.common.collect.Lists;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BestellingImpl implements Bestelling {
    private static final Logger LOGGER = Logger.getLogger(BestellingImpl.class);
    private List<Product> bestelling;
    private static int productNr = 1000;

    public BestellingImpl() {
        bestelling = Lists.newArrayList();
    }

    @Override
    public void voegProductToe(Product p) {
        p.setProductNummer(productNr);
        bestelling.add(p);
        productNr++;
    }

    @Override
    public void sorteer() {
        Collections.sort(bestelling, Comparator.comparing(Product::getProductNummer));
    }

    @Override
    public void sorteerOpMerk() {
        Collections.sort(bestelling, Product.sorteerOpMerknaam());
    }

    @Override
    public void sorteerOpVolume() {
        Collections.sort(bestelling, Comparator.comparing(Product::getVolume).thenComparing(Product::getProductCode));
    }

    @Override
    public List<Product> lijstVanBepaaldMerk(String merk) {
        return bestelling.stream().filter(product -> product.getMerk().equals(merk))
                .sorted(Comparator.comparing(Product::getProductCode))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> lijstVanParfums() {
        return bestelling.stream().filter(p -> p instanceof Parfum)
                .sorted(Comparator.comparing(Product::getProductCode))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> lijstVanGoedkopeProducten() {
        return bestelling.stream().filter(product -> product.getPrijs() <= 50.00D)
                .sorted(Comparator.comparing(Product::getProductNummer))
                .collect(Collectors.toList());
    }

    @Override
    public Product zoekDuursteProduct() {
        return bestelling.stream().max(Comparator.comparing(Product::getPrijs)).orElseThrow(RuntimeException::new);
    }

    @Override
    public double totalePrijs() {
        return bestelling.stream().mapToDouble(Product::getPrijs).sum();
    }

    //Extra methods voor de klasse BestellingImplTest.java
    @Override
    public Product get(int i) {
        return bestelling.get(i);
    }

    //Extra method voor de klasse SwingApp.java
    @Override
    public List<Product> getBestelling() {
        return new ArrayList<>(bestelling);
    }
}

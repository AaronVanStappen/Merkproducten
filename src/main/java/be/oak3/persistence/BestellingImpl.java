package be.oak3.persistence;

import be.oak3.model.Parfum;
import be.oak3.model.Product;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BestellingImpl implements Bestelling {
    public List<Product> bestelling = new ArrayList<>();
    public BestellingImpl() { }
    private static final Logger LOGGER = Logger.getLogger(BestellingImpl.class.getName());

    @Override
    public void voegProductToe(Product p) {
        bestelling.add(p);
    }

    @Override
    public void sorteer() {
        bestelling.stream().sorted().forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));
    }

    @Override
    public void sorteerOpMerk() {
        bestelling.stream().sorted(Product.sorteerOpMerknaam())
                  .forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));
    }

    @Override
    public void sorteerOpVolume() {
        bestelling.stream().sorted(Comparator.comparing(Product::getVolume).thenComparing(Product::getProductCode))
                  .forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));
    }

    @Override
    public List<Product> lijstVanBepaaldMerk(String merk) {
        /*bestelling.stream().filter(product -> product.getMerk().equals(merk))
                  .sorted(Comparator.comparing(Product::getProductCode))
                  .forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));*/

        return bestelling.stream().filter(product -> product.getMerk().equals(merk))
                         .sorted(Comparator.comparing(Product::getProductCode))
                         .collect(Collectors.toList());
    }

   @Override
    public List<Product> lijstVanParfums() {
        /*List<Product> parfums = new ArrayList<>();
        for (Product p : bestelling) {
            if (p instanceof Parfum) {
                parfums.add(p);
            }
        }
       parfums.stream().sorted(Comparator.comparing(Product::getProductCode))
              .forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));*/

       return bestelling.stream().filter(p -> p instanceof Parfum)
                 .sorted(Comparator.comparing(Product::getProductCode))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Product> lijstVanGoedkopeProducten() {
        /*bestelling.stream().filter(product -> product.getPrijs() <= 50.00D)
                  .sorted(Comparator.comparing(Product::getProductCode))
                  .forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));*/

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

    @Override
    public List<Product> getList(int i) {
        if (i == 1) {
            return bestelling.stream().sorted(Comparator.comparing(Product::getMerk)).collect(Collectors.toList());
        } else {
            return bestelling.stream().sorted(Comparator.comparing(Product::getVolume)).collect(Collectors.toList());
        }
    }
}

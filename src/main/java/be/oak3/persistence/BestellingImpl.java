package be.oak3.persistence;

import be.oak3.model.Parfum;
import be.oak3.model.Product;

import com.google.common.collect.Lists;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BestellingImpl implements Bestelling {
    private static final Logger LOGGER = Logger.getLogger(BestellingImpl.class.getName());
    private List<Product> bestelling;
    private  static  int productNr = 1000;

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
        //bestelling.stream().sorted().forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));
        bestelling.sort(Comparator.comparing(Product::getProductNummer));
    }

    @Override
    public void sorteerOpMerk() {
        /*bestelling.stream().sorted(Product.sorteerOpMerknaam())
                  .forEach(bestelling -> LOGGER.log(Level.DEBUG,bestelling));*/
        bestelling.sort(Product.sorteerOpMerknaam());
    }

    @Override
    public void sorteerOpVolume() {
        bestelling.sort(Comparator.comparing(Product::getVolume).thenComparing(Product::getProductCode));
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
}

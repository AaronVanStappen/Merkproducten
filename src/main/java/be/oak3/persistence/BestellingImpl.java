package be.oak3.persistence;

import be.oak3.model.Parfum;
import be.oak3.model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BestellingImpl implements Bestelling {
    public List<Product> bestelling = new ArrayList<>();
    public BestellingImpl() { }

    @Override
    public void voegProductToe(Product p) {
        bestelling.add(p);
    }

    @Override
    public void sorteer() {
        bestelling.stream().sorted().forEach(System.out::println);
    }

    @Override
    public void sorteerOpMerk() {
        bestelling.stream().sorted(Comparator.comparing(Product::getMerk)).forEach(System.out::println);
    }

    @Override
    public void sorteerOpVolume() {
        bestelling.stream().sorted(Comparator.comparing(Product::getVolume).thenComparing(Product::getProductCode))
                .forEach(System.out::println);
    }

    @Override
    public void toonPerMerk(String merk) {
        bestelling.stream().filter(product -> product.getMerk().equals(merk))
                .sorted(Comparator.comparing(Product::getProductCode)).forEach(System.out::println);
    }

   @Override
    public void toonParfums() {
        List<Product> parfums = new ArrayList<>();
        for (Product p : bestelling) {
            if (p instanceof Parfum) {
                parfums.add(p);
            }
        }
        parfums.stream().sorted(Comparator.comparing(Product::getProductCode)).forEach(System.out::println);
    }

    @Override
    public void toonGoedkopeProducten() {
        bestelling.stream().filter(product -> product.getPrijs() <= 50.00D)
                .sorted(Comparator.comparing(Product::getProductCode)).forEach(System.out::println);
    }

    @Override
    public Product zoekDuursteProduct() {
        return bestelling.stream().max(Comparator.comparing(Product::getPrijs)).get();
    }

    @Override
    public double totalePrijs() {
        double totalePrijs = 0.00D;
        for (Product p : bestelling) {
            totalePrijs = totalePrijs +  p.getPrijs();
        }
        return totalePrijs;
    }
}

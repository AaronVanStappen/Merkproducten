package be.oak3;

import be.oak3.model.Deodorant;
import be.oak3.model.Parfum;
import be.oak3.model.Product;
import be.oak3.persistence.Bestelling;
import be.oak3.persistence.BestellingImpl;
import be.oak3.persistence.Data;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.*;

public class BestellingImplTest {
    private Bestelling bestelling;
    private Bestelling bestel2;

    @Before
    public void init() {
        List<Product> lijst;
        lijst = Data.getData();
        bestelling = new BestellingImpl();
        for (Product product : lijst) {
            bestelling.voegProductToe(product);
        }
        bestel2 = new BestellingImpl();
        for (Product product : lijst) {
            bestel2.voegProductToe(product);
        }
    }

    @Test
    public void testVoegProductToe() {
        bestelling.voegProductToe(new Parfum(0, "Isay", "L'eau", 100, 95.00));
        assertThat(bestelling).isNotNull();
        assertThat(bestelling.totalePrijs()).isEqualTo(674.05);
        assertThat(bestelling.get(11)).extracting(Product::getMerk).contains("Isay");
    }

    @Test
    public void testLijstVanParfum() {
        assertThat(bestelling.lijstVanParfums()).isNotNull();
        assertThat(bestelling.lijstVanParfums()).hasSize(7);
        assertThat(bestelling.lijstVanParfums()).first().isInstanceOf(Parfum.class);
        assertThat(extractProperty("merk").ofType(String.class).from(bestelling.lijstVanParfums()))
                .contains("Givency").doesNotContain("Sauron", "Elrond");
    }

    @Test
    public void testLijstVanBepaaldMerk() {
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).isNotNull();
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).hasSize(3);
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).first().isInstanceOf(Product.class);
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani"))
                .isEqualTo(bestel2.lijstVanBepaaldMerk("Georgio Armani"));
        assertThat(extractProperty("volume").ofType(Integer.class)
                .from(bestelling.lijstVanBepaaldMerk("Georgio Armani")))
                .contains(50).doesNotContain(65, 110);
        assertThat(bestelling.lijstVanBepaaldMerk("BVLGARI"))
                .extracting(Product::getMerk).containsOnly("BVLGARI");
    }

    @Test
    public void testLijstVanGoedkopeProducten() {
        assertThat(bestelling.lijstVanGoedkopeProducten()).isNotNull();
        assertThat(bestelling.lijstVanGoedkopeProducten()).hasSize(5);
        assertThat(bestelling.lijstVanGoedkopeProducten()).first().isInstanceOf(Deodorant.class);
        assertThat(bestelling.lijstVanGoedkopeProducten())
                .isEqualTo(bestel2.lijstVanGoedkopeProducten());
        //assertThat(extractProperty("productNummer").ofType(Integer.class)
                //.from(bestelling.lijstVanGoedkopeProducten()))
                //.contains(1002, 1010, 1007).doesNotContain(1003, 1006);
    }

    @Test
    public void testZoekDuursteProduct() {
        assertThat(bestelling.zoekDuursteProduct()).isNotNull();
        assertThat(bestelling.zoekDuursteProduct().getPrijs()).isEqualTo(76.00);
        assertThat(bestelling.zoekDuursteProduct()).isEqualTo(bestel2.zoekDuursteProduct());
    }

    @Test
    public void testTotalePrijs() {
        assertThat(bestelling.totalePrijs()).isNotNegative();
        assertThat(bestelling.totalePrijs()).isNotZero();
        assertThat(bestelling.totalePrijs()).isEqualTo(579.05);
        assertThat(bestelling.totalePrijs()).isEqualTo(bestel2.totalePrijs());
    }

    @Test
    public void testSorteer() {
        bestelling.sorteer();
        Product p = bestelling.get(0);
        assertThat(p.getNaam()).isEqualTo("Light Blue");
        assertThat(p.getMerk()).isEqualTo("Dolce & Gabbana");
    }

    @Test
    public void testSorteerOpMerk() {
        bestelling.sorteerOpMerk();
        Product p = bestelling.get(0);
        assertThat(p.getNaam()).isEqualTo("BLV");
        assertThat(p.getMerk()).isEqualTo("BVLGARI");
    }

    @Test
    public void testSorteerOpVolume() {
        bestelling.sorteerOpVolume();
        Product p = bestelling.get(0);
        assertThat(p.getNaam()).isEqualTo("Code Donna");
        assertThat(p.getVolume()).isEqualTo(30);
    }
}

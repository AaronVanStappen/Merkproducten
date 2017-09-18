package be.oak3;

import be.oak3.model.Parfum;
import be.oak3.model.Product;
import be.oak3.persistence.Bestelling;
import be.oak3.persistence.BestellingImpl;
import be.oak3.persistence.Data;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.CompareTo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class BestellingImplTest {
    private List<Product> lijst = Data.getData();
    private Bestelling bestelling = new BestellingImpl();

    @Before
    public void init() {
        for (Product product : lijst) {
            bestelling.voegProductToe(product);
        }
    }

    @Test
    public void testLijstVanParfum() {
        assertThat(bestelling.lijstVanParfums()).isNotNull();
        assertThat(bestelling.lijstVanParfums()).size().isEqualTo(7);
        assertThat(bestelling.lijstVanParfums()).first().isInstanceOf(Parfum.class);
    }

    @Test
    public void testLijstVanBepaaldMerk() {
        Bestelling bestel2 = new BestellingImpl();
        for (Product product : lijst) {
            bestel2.voegProductToe(product);
        }
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).isNotNull();
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).size().isEqualTo(3);
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).first().isInstanceOf(Product.class);
        assertThat(bestelling.lijstVanBepaaldMerk("Georgio Armani")).isEqualTo(bestel2.lijstVanBepaaldMerk("Georgio Armani"));
    }

    @Test
    public void testSorteerOpMerk() {
    }
}

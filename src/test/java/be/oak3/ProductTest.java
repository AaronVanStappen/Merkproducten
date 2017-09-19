package be.oak3;

import be.oak3.model.AfterShave;
import be.oak3.model.Deodorant;
import be.oak3.model.Parfum;
import be.oak3.model.Product;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {
    private Product parfum = new Parfum(0, "Dolce & Gabana", "Li(f)e",
            100, 78.35D);
    private Product deo = new Deodorant(0, "Axe", "Ice-Ice Baby",
            75, 10.5D, Deodorant.DeoType.STICK);
    private Product aftershave = new AfterShave(0, "Cacharel", "Anais",
            50, 35.25, AfterShave.Soort.GEL);

    @Test
    public void testProduct() {
        assertThat(parfum.getProductNummer()).isEqualTo(1003);
        assertThat(parfum.getMerk()).isEqualTo("Dolce & Gabana");
        assertThat(parfum.getNaam()).isEqualTo("Li(f)e");
        assertThat(parfum.getVolume()).isEqualTo(100);
        assertThat(parfum.getPrijs()).isEqualTo(78.35D);
    }

    @Test
    public void testProductCode() {
        assertThat(aftershave.getProductCode()).isNotNull();
        assertThat(aftershave.getProductCode()).isEqualTo("CACANA50");
    }

    @Test
    public void testAfterShave() {
        assertThat(aftershave.toString()).containsIgnoringCase("GEL");
        assertThat(aftershave).isInstanceOf(Product.class);
    }

    @Test
    public void testDeodorant() {
        assertThat(deo.toString()).containsIgnoringCase("STICK");
        assertThat(deo).isInstanceOf(Product.class);
    }
}

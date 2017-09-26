package be.oak3.persistence;

import be.oak3.model.AfterShave;
import be.oak3.model.Deodorant;
import be.oak3.model.Parfum;
import be.oak3.model.Product;
import com.google.common.collect.Lists;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class BestellingImplDao implements Bestelling {
    private static final String URL = "jdbc:mysql://localhost/merkproducten?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "12345";
    private List<Product> bestelling;

    public BestellingImplDao() {
        bestelling = new ArrayList<>();
    }

    @Override
    public void voegProductToe(Product product) {
        String addProduct = "insert into producten (prod_nr, prod_code, merk, naam, volume, prijs, soort_id, type_id) " +
                "values (null, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(addProduct)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getMerk());
            stmt.setString(3, product.getNaam());
            stmt.setInt(4, product.getVolume());
            stmt.setDouble(5, product.getPrijs());
            if (product instanceof AfterShave) {
                AfterShave a = (AfterShave) product;
                stmt.setInt(6, 1);
                stmt.setInt(7, a.getSoort().toString().equalsIgnoreCase("vapo") ? 1 : 3);
            } else if (product instanceof Deodorant) {
                Deodorant d = (Deodorant) product;
                stmt.setInt(6, 2);
                stmt.setInt(7, d.getSoort().toString().equalsIgnoreCase("vapo") ? 1 : 2);
            } else {
                stmt.setInt(6, 3);
                stmt.setNull(7, Types.INTEGER);
            }
            int i = stmt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sorteer() {
        String sorteer = "select * from producten order by prod_nr";
        addToList(sorteer);
    }

    @Override
    public void sorteerOpMerk() {
        String sorteerMerk = "select * from producten order by merk";
        addToList(sorteerMerk);
    }

    @Override
    public void sorteerOpVolume() {
        String sorteerVolume = "select * from producten order by volume";
        addToList(sorteerVolume);
    }

    @Override
    public List<Product> lijstVanBepaaldMerk(String merk) {
        String merkLijst = "select * from producten where merk='" + merk + "'";
        addToList(merkLijst);
        return bestelling;
    }

    @Override
    public List<Product> lijstVanParfums() {
        String parfums = "select * from producten where soort_id=3;";
        addToList(parfums);
        return bestelling;
    }

    @Override
    public List<Product> lijstVanGoedkopeProducten() {
        String goedkoop = "select * from producten where prijs<=50.00";
        addToList(goedkoop);
        return bestelling;
    }

    @Override
    public Product zoekDuursteProduct() {
        String max = "select * from producten where prijs = (select max(prijs) from producten)";
        addToList(max);
        return bestelling.get(0);
    }

    @Override
    public double totalePrijs() {
        double totale_prijs = 0;
        String query = "select sum(prijs) as totale_prijs from producten;";
        try (Connection con = getConnection(); Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    totale_prijs = rs.getDouble("totale_prijs");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totale_prijs;
    }

    @Override
    public Product get(int index) {
        String get = "select * from producten where prod_nr=" + index;
        addToList(get);
        return bestelling.stream().findFirst().get();
    }

    @Override
    public List<Product> getBestelling() {
        return bestelling;
    }

    private void addToList(String query) {
        bestelling.clear();
        try (Connection con = getConnection(); Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int prodNr = rs.getInt("prod_nr");
                    String hetMerk = rs.getString("merk");
                    String naam = rs.getString("naam");
                    int volume = rs.getInt("volume");
                    float prijs = rs.getFloat("prijs");
                    int soort = rs.getInt("soort_id");
                    String prodCode = rs.getString("prod_code");
                    int type = rs.getInt("type_id");
                    switch (soort) {
                        case 1:
                            if (type == 1) {
                                bestelling.add(new AfterShave(prodNr, hetMerk, naam, volume, prijs, AfterShave.Soort.VAPO));
                            } else {
                                bestelling.add(new AfterShave(prodNr, hetMerk, naam, volume, prijs, AfterShave.Soort.GEL));
                            }
                            break;
                        case 2:
                            if (type == 1) {
                                bestelling.add(new Deodorant(prodNr, hetMerk, naam, volume, prijs, Deodorant.DeoType.VAPO));
                            } else {
                                bestelling.add(new Deodorant(prodNr, hetMerk, naam, volume, prijs, Deodorant.DeoType.STICK));
                            }
                            break;
                        default:
                            bestelling.add(new Parfum(prodNr, hetMerk, naam, volume, prijs));
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

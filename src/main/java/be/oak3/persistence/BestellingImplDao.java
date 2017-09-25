package be.oak3.persistence;

import be.oak3.model.AfterShave;
import be.oak3.model.Deodorant;
import be.oak3.model.Parfum;
import be.oak3.model.Product;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class BestellingImplDao implements Bestelling {
    private static final String URL = "jdbc:mysql://localhost/merkproducten?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "12345";
    private  List<Product> bestelling;
    private int prodNr;
    private String hetMerk;
    private String naam;
    private int volume;
    private float prijs;
    private String prodCode;
    private int soort;
    private int type;

    public BestellingImplDao() {
        bestelling = new ArrayList<>(); }

    @Override
    public void voegProductToe(Product product) {
        String addProduct = "insert into beers (prod_code, merk, naam, volume, prijs, soort_id, type_id) " +
                "values (?, ?, ?, ?, ?, ?, ?);";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(addProduct)) {
            switch (product.getClass().getName()) {
                case "Aftershave":
                    if (AfterShave.Soort.VAPO.toString() == "VAPO") {
                        stmt.setString(1, product.getProductCode());
                        stmt.setString(2, product.getMerk());
                        stmt.setString(3, product.getNaam());
                        stmt.setInt(4, product.getVolume());
                        stmt.setDouble(5, product.getPrijs());
                        stmt.setInt(6, 1);
                        stmt.setInt(7, 1);
                    } else {
                        stmt.setString(1, product.getProductCode());
                        stmt.setString(2, product.getMerk());
                        stmt.setString(3, product.getNaam());
                        stmt.setInt(4, product.getVolume());
                        stmt.setDouble(5, product.getPrijs());
                        stmt.setInt(6, 1);
                        stmt.setInt(7, 3);
                    }
                case "Deodorant":
                    if (Deodorant.DeoType.VAPO.toString() == "VAPO") {
                        stmt.setString(1, product.getProductCode());
                        stmt.setString(2, product.getMerk());
                        stmt.setString(3, product.getNaam());
                        stmt.setInt(4, product.getVolume());
                        stmt.setDouble(5, product.getPrijs());
                        stmt.setInt(6, 2);
                        stmt.setInt(7, 1);
                    } else {
                        stmt.setString(1, product.getProductCode());
                        stmt.setString(2, product.getMerk());
                        stmt.setString(3, product.getNaam());
                        stmt.setInt(4, product.getVolume());
                        stmt.setDouble(5, product.getPrijs());
                        stmt.setInt(6, 2);
                        stmt.setInt(7, 2);
                    }
                default:
                    stmt.setString(1, product.getProductCode());
                    stmt.setString(2, product.getMerk());
                    stmt.setString(3, product.getNaam());
                    stmt.setInt(4, product.getVolume());
                    stmt.setDouble(5, product.getPrijs());
                    stmt.setInt(6, 3);
            }

        } catch (SQLException e) {
            e.getStackTrace();
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
        String merkLijst = "select * from producten where merk=" + merk;
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
        String query = "select count(prijs) as totale_prijs from producten;";
        try (Connection con = getConnection(); Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()){
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
        String get = "select * from producten where prod_nr=" +index;
        addToList(get);
        return bestelling.stream().findFirst().get();
    }

    @Override
    public List<Product> getBestelling() {
        return addToList("selec*from producten");
    }

    private List<Product> addToList(String query) {
        try (Connection con = getConnection(); Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()){
                    prodNr = rs.getInt("prod_nr");
                    hetMerk = rs.getString("merk");
                    naam = rs.getString("naam");
                    volume = rs.getInt("volume");
                    prijs = rs.getFloat("prijs");
                    soort = rs.getInt("soort_id");
                    prodCode = rs.getString("prod_code");
                    type = rs.getInt("type_id");
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
        return bestelling;
    }

    private static  Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER ,PASSWORD);
    }

    public static void main(String[] args) {
        Bestelling b = new BestellingImplDao();
        b.sorteerOpMerk();
        b.getBestelling().forEach(System.out::println);
    }
}

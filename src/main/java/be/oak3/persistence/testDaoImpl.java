package be.oak3.persistence;

import be.oak3.model.AfterShave;
import be.oak3.model.Deodorant;
import be.oak3.model.Parfum;
import be.oak3.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testDaoImpl {
    private static String URL = "jdbc:mysql://localhost/merkproducten?useSSL=false";
    private static String USER = "cursist";
    private static String PASSWORD = "12345";
    private static List<Product> bestelling = new ArrayList<>();
    static int prodNr;
    static String hetMerk;
    static String naam;
    static int volume;
    static float prijs;
    static String prodCode;
    static int soort;
    static int type;

    public static void main(String[] args) {
        System.out.println(lijstVanBepaaldMerk("Georgio Armani"));
    }

    public static List<Product> lijstVanBepaaldMerk(String merk) {
        String merkLijst = "select * from producten where merk=?;";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareCall(merkLijst, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, merk);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
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
                                bestelling.add(new Deodorant(prodNr, hetMerk ,naam, volume, prijs, Deodorant.DeoType.STICK));
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
        return new ArrayList<>(bestelling);
    }

    private static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER ,PASSWORD);
    }
}

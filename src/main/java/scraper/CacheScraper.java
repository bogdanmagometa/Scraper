package scraper;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CacheScraper implements Scraper {
    private final Scraper scraper = new DefaultScraper();
    @Override @SneakyThrows
    public Home scrape(String url) {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
        Statement statement = connection.createStatement();

        String query = String.format("select count(*) as count from homes where url='%s'", url);
        ResultSet rs = statement.executeQuery(query);

        Home home;
        if (rs.getInt("count") > 0) {
            query = String.format("select * from homes where url='%s'", url);
            rs = statement.executeQuery(query);
            home = new Home(rs.getInt("price"), rs.getDouble("beds"), rs.getDouble("bathes"), rs.getDouble("garages"));
        } else {
            home = scraper.scrape(url);
            String update = "INSERT INTO homes(url, price, beds, bathes, garages) VALUES ('%s', %d, %f, %f, %f)";
            update = String.format(update, url, home.getPrice(), home.getBeds(), home.getBathes(), home.getGarages());
            statement.executeUpdate(update);
        }
        return home;
    }
}

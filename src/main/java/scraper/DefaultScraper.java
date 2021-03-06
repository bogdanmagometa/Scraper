package scraper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DefaultScraper implements Scraper {
    private static final String PRICE_SELECTOR = ".detail__info-xlrg";
    private static final String BEDS_SELECTOR = ".nhs_BedsInfo";
    private static final String BATHES_SELECTOR = ".nhs_BathsInfo";
    private static final String GARAGES_SELECTOR = ".nhs_GarageInfo";

    @Override @SneakyThrows
    public Home scrape(String url) {
        Document doc = Jsoup.connect(url).get();
        int price = getPrice(doc);
        double beds = getBeds(doc);
        double bathes = getBathes(doc);
        double garages = getGarages(doc);
        return new Home(price, beds, bathes, garages);
    }

    private int getPrice(Document doc) {
        String price = doc.selectFirst(PRICE_SELECTOR).text().replaceAll("[^0-9]+", "");
        return Integer.parseInt(price);
    }

    private int getBeds(Document doc) {
        String price = doc.selectFirst(BEDS_SELECTOR).text().replaceAll("[^0-9.]+", "");
        return Integer.parseInt(price);
    }

    private int getBathes(Document doc) {
        String bathes = doc.selectFirst(BATHES_SELECTOR).text().replaceAll("[^0-9.]+", "");
        return Integer.parseInt(bathes);
    }

    private int getGarages(Document doc) {
        String garages = doc.selectFirst(GARAGES_SELECTOR).text().replaceAll("[^0-9.]+", "");
        return Integer.parseInt(garages);
    }
}

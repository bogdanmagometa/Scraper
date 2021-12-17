package scraper;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.newhomesource.com/plan/the-cornwall-bidwell-village-coventry-ct/1825557";
        Scraper cacheScraper = new CacheScraper();
        Home home = cacheScraper.scrape(url);
        System.out.println(home);
    }
}

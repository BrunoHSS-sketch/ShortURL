package shorturl.repository;

public interface UrlRepository {
    public void saveUrl(String originalUrl, String shortUrl);
    public String getShortUrl( String originalUrl);
    public String getOriginalUrl(String shortUrl);
    public Long incrementCounter();
}

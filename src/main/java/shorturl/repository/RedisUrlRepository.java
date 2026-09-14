package shorturl.repository;

import redis.clients.jedis.Jedis;

public class RedisUrlRepository implements UrlRepository{

    private Jedis jedis = new Jedis("localhost", 6379);

    @Override
    public void saveUrl(String originalUrl, String shortUrl) {
        jedis.set(originalUrl, shortUrl);
        jedis.set(shortUrl, originalUrl);
    }

    @Override
    public String getShortUrl(String originalUrl) {
        return jedis.get(originalUrl);
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        return jedis.get(shortUrl);
    }
}

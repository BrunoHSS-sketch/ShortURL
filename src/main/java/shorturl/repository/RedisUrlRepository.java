package shorturl.repository;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class RedisUrlRepository implements UrlRepository{

    private Jedis jedis = new Jedis(System.getenv().getOrDefault("REDIS_HOST", "localhost"), 6379);

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

    @Override
    public Long incrementCounter() { return jedis.incr("counter"); }
}

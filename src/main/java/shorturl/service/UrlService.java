package shorturl.service;

import shorturl.repository.UrlRepository;

public class UrlService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {
        Long counter = urlRepository.incrementCounter();
        StringBuilder sb = new StringBuilder();

        do {
            int base62Counter = (int) (counter % 62);
            char letra = ALPHABET.charAt(base62Counter);
            sb.append(letra);
            counter = counter / 62;
        } while (counter > 0);

        String linkCurto = sb.reverse().toString();
        urlRepository.saveUrl(originalUrl, linkCurto);

        return linkCurto;
    }

    public String getOriginalUrl(String shortUrl) {
        return urlRepository.getOriginalUrl(shortUrl);
    }
}

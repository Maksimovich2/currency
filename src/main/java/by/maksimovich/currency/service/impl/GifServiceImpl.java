package by.maksimovich.currency.service.impl;

import by.maksimovich.currency.client.FeignGiphyGifClient;
import by.maksimovich.currency.dto.ResponseDto;
import by.maksimovich.currency.service.GifService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Maksim Maksimovich
 */
@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {
    private final FeignGiphyGifClient feignGiphyGifClient;

    @Value("${giphy.api.key}")
    private String apiKey;

    /**
     * Ответ от Giphy.com просто перекидывается клиенту
     * в виде ResponseEntity
     *
     * @param gifTag хэштэг для гифки
     * @return
     */
    @Override
    public ResponseDto showGif(String gifTag) {
        ResponseEntity<ResponseDto> result = feignGiphyGifClient.showRandomGif(apiKey, gifTag);
        return result.getBody();
    }
}

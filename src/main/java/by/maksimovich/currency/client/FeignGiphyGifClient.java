package by.maksimovich.currency.client;

import by.maksimovich.currency.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Maksim Maksimovich
 */
@FeignClient(name = "giphyClient", url = "${giphy.url.general}")
public interface FeignGiphyGifClient {

    @GetMapping("/random")
    ResponseEntity<ResponseDto> showRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}

package by.maksimovich.currency.service.impl;

import by.maksimovich.currency.client.FeignGiphyGifClient;
import by.maksimovich.currency.dto.ResponseDto;
import by.maksimovich.currency.service.GifService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author Maksim Maksimovich
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("by.maksimovich.currency")
class GifServiceImplTest {
    @Autowired
    private GifService gifService;
    @MockBean
    private FeignGiphyGifClient gifClient;


    @Test
    void whenPositiveChanges() {
        ResponseEntity<ResponseDto> testEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(gifClient.showRandomGif(anyString(), anyString()))
                .thenReturn(testEntity);
    }

}
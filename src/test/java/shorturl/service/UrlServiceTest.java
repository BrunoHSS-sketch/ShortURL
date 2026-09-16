package shorturl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shorturl.repository.UrlRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository; // Cria um banco de dados falso

    @InjectMocks
    private UrlService urlService; // Injeta o banco falso no nosso Serviço real

    @Test
    public void deveEncurtarUrlComSucessoQuandoContadorForZero() {
        // 1. Arrange (Preparação)
        String urlOriginal = "https://www.vaga-java.com";
        // Ensina o banco falso a devolver 0L quando pedirem um ID
        when(urlRepository.incrementCounter()).thenReturn(0L);

        // 2. Act (Ação)
        String urlCurta = urlService.shortenUrl(urlOriginal);

        // 3. Assert (Verificação)
        assertEquals("a", urlCurta); // 0 na Base62 deve ser "a"

        // Verifica se o serviço mandou o banco salvar a URL exatamente 1 vez
        verify(urlRepository, times(1)).saveUrl(urlOriginal, "a");
    }

    @Test
    public void deveLancarExcecaoQuandoUrlForVazia() {
        // 1. Arrange (Preparação)
        String urlVazia = "";

        // 2. Act & Assert (Ação e Verificação juntas)
        // Confere se o sistema "estourou" a exceção correta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            urlService.shortenUrl(urlVazia);
        });

        // Confere se a mensagem de erro é exatamente a que definimos
        assertEquals("A URL original não pode estar vazia!", exception.getMessage());

        // Garante que o banco de dados NUNCA foi chamado (protegendo a performance)
        verify(urlRepository, never()).incrementCounter();
    }
}
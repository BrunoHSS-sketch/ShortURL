package shorturl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shorturl.dto.UrlRequest;
import shorturl.service.UrlService;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Tag(name = "Encurtador de URL", description = "Endpoints para criar links curtos e redirecionar usuários") // <-- AQUI
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/teste")
    @Operation(summary = "Teste da API", description = "Endpoint de teste para verificar se a API está funcionando.") // <-- AQUI
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("API funcionando!");
    }

    @PostMapping("/encurtar")
    @Operation(summary = "Encurta uma URL longa", description = "Recebe uma URL original e devolve um código alfanumérico exclusivo gerado em Base62.") // <-- AQUI
    public ResponseEntity<String> encurtarUrl(@RequestBody UrlRequest request) {
        String linkCurto = urlService.shortenUrl(request.originalUrl());
        return ResponseEntity.ok(linkCurto);
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Redireciona para o site original", description = "Recebe o código curto, busca no Redis e realiza um redirecionamento HTTP 302 (Found) para a URL original.") // <-- AQUI
    public ResponseEntity<Void> redirecionar(@PathVariable String codigo) {
        String urlOriginal = urlService.getOriginalUrl(codigo);

        if (urlOriginal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlOriginal))
                .build();
    }
}
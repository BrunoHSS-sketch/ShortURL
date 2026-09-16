package shorturl.dto;

import java.time.LocalDateTime;

public class ErroResponse {

    private int status;
    private String mensagem;
    private LocalDateTime momentoDaFalha;

    public ErroResponse() {
    }

    public ErroResponse(int status, String mensagem, LocalDateTime momentoDaFalha) {
        this.status = status;
        this.mensagem = mensagem;
        this.momentoDaFalha = momentoDaFalha;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getMomentoDaFalha() {
        return momentoDaFalha;
    }
}
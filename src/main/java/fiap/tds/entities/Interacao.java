package fiap.tds.entities;

import java.time.LocalDateTime;

public class Interacao {

    private int id;
    private int usuarioId;
    private String mensagemUsuario;
    private String respostaIA;
    private LocalDateTime dataHora;

    public Interacao() {
    }

    public Interacao(int id, int usuarioId, String mensagemUsuario, String respostaIA, LocalDateTime dataHora) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mensagemUsuario = mensagemUsuario;
        this.respostaIA = respostaIA;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMensagemUsuario() {
        return mensagemUsuario;
    }

    public void setMensagemUsuario(String mensagemUsuario) {
        this.mensagemUsuario = mensagemUsuario;
    }

    public String getRespostaIA() {
        return respostaIA;
    }

    public void setRespostaIA(String respostaIA) {
        this.respostaIA = respostaIA;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}

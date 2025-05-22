package fiap.tds.services;

import fiap.tds.entities.Interacao;
import fiap.tds.repositories.InteracaoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class InteracaoService {

    private final InteracaoRepository repository = new InteracaoRepository();

    public void salvar(Interacao interacao) {
        interacao.setDataHora(LocalDateTime.now());
        repository.salvar(interacao);
    }

    public List<Interacao> listarPorUsuario(int usuarioId) {
        return repository.buscarPorUsuario(usuarioId);
    }
}

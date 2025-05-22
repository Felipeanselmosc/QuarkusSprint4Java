package fiap.tds.services;

import fiap.tds.dtos.UsuarioDto;
import fiap.tds.entities.Usuario;
import fiap.tds.repositories.UsuarioRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    private final UsuarioRepository repository = new UsuarioRepository();

    public void cadastrar(Usuario usuario) {
        usuario.setDataCadastro(new Date());
        usuario.setUltimoAcesso(new Date());

        repository.insert(usuario);
    }

    public List<UsuarioDto> listar() {
        return repository.findAll().stream()
                .map(u -> new UsuarioDto(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getPreferenciasAcessibilidade(),
                        u.getDataCadastro(),
                        u.getUltimoAcesso()
                ))
                .collect(Collectors.toList());
    }

    public UsuarioDto buscarPorId(int id) {
        Usuario u = repository.findById((long) id);
        if (u == null) return null;

        return new UsuarioDto(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getPreferenciasAcessibilidade(),
                u.getDataCadastro(),
                u.getUltimoAcesso()
        );
    }

    public boolean atualizar(Usuario usuario) {
        usuario.setUltimoAcesso(new Date());
        return repository.update(usuario);
    }
}

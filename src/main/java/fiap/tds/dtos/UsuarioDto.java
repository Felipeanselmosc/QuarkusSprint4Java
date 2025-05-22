package fiap.tds.dtos;

import java.util.Date;

public record UsuarioDto(
        int id,
        String nome,
        String email,
        String preferenciasAcessibilidade,
        Date dataCadastro,
        Date ultimoAcesso
) {}
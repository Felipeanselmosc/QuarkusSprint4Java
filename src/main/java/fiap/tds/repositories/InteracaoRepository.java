package fiap.tds.repositories;

import fiap.tds.entities.Interacao;
import fiap.tds.infrastructure.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InteracaoRepository {

    public void salvar(Interacao interacao) {
        String sql = "INSERT INTO interacao (usuario_id, mensagem_usuario, resposta_ia, data_hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, interacao.getUsuarioId());
            stmt.setString(2, interacao.getMensagemUsuario());
            stmt.setString(3, interacao.getRespostaIA());
            stmt.setTimestamp(4, Timestamp.valueOf(interacao.getDataHora()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar interação: " + e.getMessage());
        }
    }

    public List<Interacao> buscarPorUsuario(int usuarioId) {
        List<Interacao> interacoes = new ArrayList<>();
        String sql = "SELECT * FROM interacao WHERE usuario_id = ? ORDER BY data_hora DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Interacao i = new Interacao();
                    i.setId(rs.getInt("id"));
                    i.setUsuarioId(rs.getInt("usuario_id"));
                    i.setMensagemUsuario(rs.getString("mensagem_usuario"));
                    i.setRespostaIA(rs.getString("resposta_ia"));
                    i.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    interacoes.add(i);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar interações: " + e.getMessage());
        }

        return interacoes;
    }
}

package fiap.tds.repositories;

import fiap.tds.entities.Usuario;
import fiap.tds.infrastructure.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM spr_usuario";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPreferenciasAcessibilidade(rs.getString("preferencias_acessibilidade"));
                usuario.setDataCadastro(rs.getTimestamp("data_cadastro")); // usa Timestamp
                usuario.setUltimoAcesso(rs.getTimestamp("ultimo_acesso")); // pode ser null

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
        }

        return usuarios;
    }

    public void insert(Usuario usuario) {
        String sql = "INSERT INTO spr_usuario " +
                     "(nome, email, senha, preferencias_acessibilidade, data_cadastro, ultimo_acesso) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPreferenciasAcessibilidade());

            // dataCadastro obrigatório → usa Timestamp
            if (usuario.getDataCadastro() != null) {
                stmt.setTimestamp(5, new java.sql.Timestamp(usuario.getDataCadastro().getTime()));
            } else {
                stmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            }

            // ultimoAcesso opcional → pode ser null
            if (usuario.getUltimoAcesso() != null) {
                stmt.setTimestamp(6, new java.sql.Timestamp(usuario.getUltimoAcesso().getTime()));
            } else {
                stmt.setNull(6, Types.TIMESTAMP);
            }

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public Usuario findById(Long id) {
        String sql = "SELECT * FROM spr_usuario WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPreferenciasAcessibilidade(rs.getString("preferencias_acessibilidade"));
                    usuario.setDataCadastro(rs.getTimestamp("data_cadastro"));
                    usuario.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }

        return null;
    }

    public boolean update(Usuario usuario) {
        String sql = "UPDATE spr_usuario " +
                     "SET nome = ?, email = ?, senha = ?, preferencias_acessibilidade = ?, ultimo_acesso = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPreferenciasAcessibilidade());

            if (usuario.getUltimoAcesso() != null) {
                stmt.setTimestamp(5, new java.sql.Timestamp(usuario.getUltimoAcesso().getTime()));
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }

            stmt.setInt(6, usuario.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }
}

package fiap.tds.entities;

import java.util.Date;
import java.util.Objects;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String preferenciasAcessibilidade;
    private Date dataCadastro;
    private Date ultimoAcesso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPreferenciasAcessibilidade() {
        return preferenciasAcessibilidade;
    }

    public void setPreferenciasAcessibilidade(String preferenciasAcessibilidade) {
        this.preferenciasAcessibilidade = preferenciasAcessibilidade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return getId() == usuario.getId() && Objects.equals(getNome(), usuario.getNome()) && Objects.equals(getEmail(), usuario.getEmail()) && Objects.equals(getSenha(), usuario.getSenha()) && Objects.equals(getPreferenciasAcessibilidade(), usuario.getPreferenciasAcessibilidade()) && Objects.equals(getDataCadastro(), usuario.getDataCadastro()) && Objects.equals(getUltimoAcesso(), usuario.getUltimoAcesso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEmail(), getSenha(), getPreferenciasAcessibilidade(), getDataCadastro(), getUltimoAcesso());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", preferenciasAcessibilidade='" + preferenciasAcessibilidade + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", ultimoAcesso=" + ultimoAcesso +
                '}';
    }
}
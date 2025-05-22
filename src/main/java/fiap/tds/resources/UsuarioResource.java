package fiap.tds.resources;

import fiap.tds.dtos.UsuarioDto;
import fiap.tds.entities.Usuario;
import fiap.tds.services.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/usuarios")
public class UsuarioResource {

    private final UsuarioService service = new UsuarioService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int pageSize
    ) {
        try {
            page = Math.max(page, 1);
            List<UsuarioDto> usuarios = service.listar();

            usuarios = usuarios.stream()
                    .sorted(Comparator.comparing(UsuarioDto::nome))
                    .collect(Collectors.toList());

            int start = (page - 1) * pageSize;
            int end = Math.min(usuarios.size(), start + pageSize);

            if (start >= usuarios.size()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Página fora do intervalo")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            List<UsuarioDto> paginado = usuarios.subList(start, end);
            return Response.ok(paginado, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioById(@PathParam("id") int id) {
        try {
            UsuarioDto usuario = service.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok(usuario, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(Usuario usuario) {
        try {
            service.cadastrar(usuario);
            return Response.status(Response.Status.CREATED)
                    .entity("Usuário cadastrado com sucesso")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar usuário: " + e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(@PathParam("id") int id, Usuario usuarioAtualizado) {
        try {
            usuarioAtualizado.setId(id);
            boolean atualizado = service.atualizar(usuarioAtualizado);
            if (!atualizado) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado para atualização")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok("Usuário atualizado com sucesso", MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar usuário: " + e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}

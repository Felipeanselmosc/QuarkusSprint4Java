package fiap.tds.resources;

import fiap.tds.entities.Interacao;
import fiap.tds.services.InteracaoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/interacoes")
public class InteracaoResource {

    private final InteracaoService service = new InteracaoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvarInteracao(Interacao interacao) {
        try {
            service.salvar(interacao);
            return Response.status(Response.Status.CREATED)
                    .entity("Interação registrada com sucesso.")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar interação: " + e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarInteracoes(@PathParam("usuarioId") int usuarioId) {
        try {
            List<Interacao> historico = service.listarPorUsuario(usuarioId);
            return Response.ok(historico, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar interações: " + e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}

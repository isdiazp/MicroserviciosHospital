package com.hospital.notificaciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.notificaciones.Dto.NotificacionesDetalleDTO;
import com.hospital.notificaciones.Dto.PacienteDTO;
import com.hospital.notificaciones.model.NotificacionesModel;
import com.hospital.notificaciones.service.NotificacionesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionesController.class)
class NotificacionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificacionesService notificacionesService;

    // --- TESTS PARA GET /api/notificaciones ---

    @Test
    @DisplayName("GET /api/notificaciones -> Retorna 200 y lista de notificaciones")
    public void listar_CuandoExistenNotificaciones_DeberiaRetornarLista() throws Exception {
        var notif1 = new NotificacionesModel();
        notif1.setId(1L);
        notif1.setIdPaciente(12345678L);
        notif1.setMensaje("Cita confirmada");
        notif1.setTipo("EMAIL");
        notif1.setEstado("ENVIADA");

        var notif2 = new NotificacionesModel();
        notif2.setId(2L);
        notif2.setIdPaciente(98765432L);
        notif2.setMensaje("Recordatorio de medicamento");
        notif2.setTipo("SMS");
        notif2.setEstado("PENDIENTE");

        List<NotificacionesModel> lista = Arrays.asList(notif1, notif2);
        when(notificacionesService.listarTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].mensaje").value("Cita confirmada"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].mensaje").value("Recordatorio de medicamento"));
    }

    @Test
    @DisplayName("GET /api/notificaciones -> Retorna 200 y lista vacía")
    public void listar_CuandoNoHayNotificaciones_DeberiaRetornarListaVacia() throws Exception {
        when(notificacionesService.listarTodas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- TESTS PARA POST /api/notificaciones ---

    @Test
    @DisplayName("POST /api/notificaciones -> Retorna 201 al crear exitosamente")
    public void crear_CuandoDatosValidos_DeberiaRetornarStatusCreated() throws Exception {
        var notif = new NotificacionesModel();
        notif.setId(1L);
        notif.setIdPaciente(11111111L);
        notif.setMensaje("Su cita es mañana");
        notif.setTipo("EMAIL");
        notif.setEstado("PENDIENTE");
        notif.setFechaEnvio(LocalDateTime.now());

        when(notificacionesService.guardar(any(NotificacionesModel.class))).thenReturn(notif);

        String jsonRequestBody = objectMapper.writeValueAsString(notif);

        mockMvc.perform(post("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Su cita es mañana"));
    }

    @Test
    @DisplayName("POST /api/notificaciones -> Retorna 400 cuando el Body es inválido")
    public void crear_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/notificaciones/{id} ---

    @Test
    @DisplayName("GET /api/notificaciones/{id} -> Retorna 200 cuando existe")
    public void obtener_CuandoIdExiste_DeberiaRetornarNotificacion() throws Exception {
        var notif = new NotificacionesModel();
        notif.setId(1L);
        notif.setIdPaciente(12345678L);
        notif.setMensaje("Cita cancelada");
        notif.setTipo("EMAIL");
        notif.setEstado("ENVIADA");

        when(notificacionesService.buscarPorId(1L)).thenReturn(notif);

        mockMvc.perform(get("/api/notificaciones/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Cita cancelada"));
    }

    @Test
    @DisplayName("GET /api/notificaciones/{id} -> Retorna 200 cuando no existe (o null)")
    public void obtener_CuandoIdNoExiste_DeberiaRetornarNull() throws Exception {
        when(notificacionesService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/notificaciones/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/notificaciones/{id} ---

    @Test
    @DisplayName("DELETE /api/notificaciones/{id} -> Retorna 204 al eliminar")
    public void borrar_CuandoIdExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(notificacionesService).eliminar(1L);

        mockMvc.perform(delete("/api/notificaciones/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/notificaciones/{id} -> Retorna 204 incluso si no existe")
    public void borrar_CuandoIdNoExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(notificacionesService).eliminar(999L);

        mockMvc.perform(delete("/api/notificaciones/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/notificaciones/{id} ---

    @Test
    @DisplayName("PUT /api/notificaciones/{id} -> Retorna 200 y notificación actualizada")
    public void actualizar_CuandoIdExiste_DeberiaRetornarNotificacionModificada() throws Exception {
        var notifActualizada = new NotificacionesModel();
        notifActualizada.setId(1L);
        notifActualizada.setIdPaciente(12345678L);
        notifActualizada.setMensaje("Mensaje actualizado");
        notifActualizada.setTipo("SMS");
        notifActualizada.setEstado("ENVIADA");

        when(notificacionesService.actualizar(eq(1L), any(NotificacionesModel.class))).thenReturn(notifActualizada);

        String jsonRequestBody = objectMapper.writeValueAsString(notifActualizada);

        mockMvc.perform(put("/api/notificaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Mensaje actualizado"));
    }

    @Test
    @DisplayName("PUT /api/notificaciones/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var notifDatos = new NotificacionesModel();
        notifDatos.setIdPaciente(12345678L);
        notifDatos.setMensaje("Mensaje");
        notifDatos.setTipo("EMAIL");
        notifDatos.setEstado("PENDIENTE");

        when(notificacionesService.actualizar(eq(999L), any(NotificacionesModel.class)))
                .thenThrow(new RuntimeException("Notificación no encontrada"));

        String jsonRequestBody = objectMapper.writeValueAsString(notifDatos);

        mockMvc.perform(put("/api/notificaciones/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/notificaciones/detalle/{id} (Reactivo / Mono) ---

    @Test
    @DisplayName("GET /api/notificaciones/detalle/{id} -> Retorna 200 y detalle cuando existe (Reactivo)")
    public void detalle_CuandoIdExiste_DeberiaRetornarDetalle() throws Exception {
        var notif = new NotificacionesModel();
        notif.setId(1L);
        notif.setIdPaciente(12345678L);
        notif.setMensaje("Cita confirmada");

        var paciente = new PacienteDTO();
        paciente.setIdPaciente(12345678L);
        paciente.setRutPaciente("12345678-9");
        paciente.setNombrePaciente("Juan");
        paciente.setApellidoPaciente("Perez");

        var detalle = new NotificacionesDetalleDTO(notif, paciente);

        when(notificacionesService.obtenerNotificacionConPaciente(1L)).thenReturn(Mono.just(detalle));

        MvcResult mvcResult = mockMvc.perform(get("/api/notificaciones/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/notificaciones/detalle/{id} -> Retorna 404 si el Mono viene vacío")
    public void detalle_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(notificacionesService.obtenerNotificacionConPaciente(999L)).thenReturn(Mono.empty());

        MvcResult mvcResult = mockMvc.perform(get("/api/notificaciones/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

package com.hospital.agenda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.agenda.Controller.AgendaController;
import com.hospital.agenda.Dto.AgendaDetalleDTO;
import com.hospital.agenda.Dto.MedicoDTO;
import com.hospital.agenda.Model.AgendaModel;
import com.hospital.agenda.Service.AgendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendaController.class)
class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AgendaService agendaService;

    // --- TESTS PARA GET /api/v1/agendas ---

    @Test
    @DisplayName("GET /api/v1/agendas -> Retorna 200 y lista de agendas")
    public void listar_CuandoExistenAgendas_DeberiaRetornarLista() throws Exception {
        var agenda1 = new AgendaModel();
        agenda1.setIdAgenda(1L);
        agenda1.setIdMedico(111L);
        agenda1.setFecha(LocalDate.now());
        agenda1.setHoraInicio(LocalTime.of(8, 0));
        agenda1.setHoraFin(LocalTime.of(12, 0));
        agenda1.setDuracionMinutos(30);
        agenda1.setCuposDisponibles(8);
        agenda1.setEstado("DISPONIBLE");
        agenda1.setActivo(true);

        var agenda2 = new AgendaModel();
        agenda2.setIdAgenda(2L);
        agenda2.setIdMedico(222L);
        agenda2.setFecha(LocalDate.now());
        agenda2.setHoraInicio(LocalTime.of(14, 0));
        agenda2.setHoraFin(LocalTime.of(18, 0));
        agenda2.setDuracionMinutos(30);
        agenda2.setCuposDisponibles(8);
        agenda2.setEstado("DISPONIBLE");
        agenda2.setActivo(true);

        List<AgendaModel> lista = Arrays.asList(agenda1, agenda2);
        when(agendaService.listarTodo()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/agendas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].idAgenda").value(1))
                .andExpect(jsonPath("$[0].estado").value("DISPONIBLE"))
                .andExpect(jsonPath("$[1].idAgenda").value(2))
                .andExpect(jsonPath("$[1].estado").value("DISPONIBLE"));
    }

    @Test
    @DisplayName("GET /api/v1/agendas -> Retorna 404 cuando no hay agendas")
    public void listar_CuandoNoHayAgendas_DeberiaRetornarNotFound() throws Exception {
        when(agendaService.listarTodo()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/agendas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA POST /api/v1/agendas ---

    @Test
    @DisplayName("POST /api/v1/agendas -> Retorna 200 al crear exitosamente")
    public void guardar_CuandoDatosValidos_DeberiaRetornarStatusOk() throws Exception {
        var agenda = new AgendaModel();
        agenda.setIdAgenda(1L);
        agenda.setIdMedico(111L);
        agenda.setFecha(LocalDate.now());
        agenda.setHoraInicio(LocalTime.of(8, 0));
        agenda.setHoraFin(LocalTime.of(12, 0));
        agenda.setDuracionMinutos(30);
        agenda.setCuposDisponibles(8);
        agenda.setEstado("DISPONIBLE");
        agenda.setActivo(true);

        when(agendaService.guardar(any(AgendaModel.class))).thenReturn(agenda);

        String jsonRequestBody = objectMapper.writeValueAsString(agenda);

        mockMvc.perform(post("/api/v1/agendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idAgenda").value(1))
                .andExpect(jsonPath("$.estado").value("DISPONIBLE"));
    }

    @Test
    @DisplayName("POST /api/v1/agendas -> Retorna 400 cuando el Body es inválido")
    public void guardar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/v1/agendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/agendas/{id} ---

    @Test
    @DisplayName("GET /api/v1/agendas/{id} -> Retorna 200 cuando existe")
    public void buscarPorId_CuandoIdExiste_DeberiaRetornarAgenda() throws Exception {
        var agenda = new AgendaModel();
        agenda.setIdAgenda(1L);
        agenda.setIdMedico(111L);
        agenda.setFecha(LocalDate.now());
        agenda.setHoraInicio(LocalTime.of(8, 0));
        agenda.setHoraFin(LocalTime.of(12, 0));
        agenda.setDuracionMinutos(30);
        agenda.setCuposDisponibles(8);
        agenda.setEstado("DISPONIBLE");
        agenda.setActivo(true);

        when(agendaService.buscarId(1L)).thenReturn(agenda);

        mockMvc.perform(get("/api/v1/agendas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idAgenda").value(1))
                .andExpect(jsonPath("$.estado").value("DISPONIBLE"));
    }

    @Test
    @DisplayName("GET /api/v1/agendas/{id} -> Retorna 404 cuando no existe")
    public void buscarPorId_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(agendaService.buscarId(999L)).thenThrow(new RuntimeException("Agenda no encontrada"));

        mockMvc.perform(get("/api/v1/agendas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/v1/agendas/{id} ---

    @Test
    @DisplayName("PUT /api/v1/agendas/{id} -> Retorna 200 y agenda actualizada")
    public void actualizar_CuandoIdExiste_DeberiaRetornarAgendaModificada() throws Exception {
        var agendaActualizada = new AgendaModel();
        agendaActualizada.setIdAgenda(1L);
        agendaActualizada.setIdMedico(111L);
        agendaActualizada.setFecha(LocalDate.now());
        agendaActualizada.setHoraInicio(LocalTime.of(8, 0));
        agendaActualizada.setHoraFin(LocalTime.of(12, 0));
        agendaActualizada.setDuracionMinutos(30);
        agendaActualizada.setCuposDisponibles(5);
        agendaActualizada.setEstado("OCUPADO");
        agendaActualizada.setActivo(true);

        when(agendaService.actualizar(eq(1L), any(AgendaModel.class))).thenReturn(agendaActualizada);

        String jsonRequestBody = objectMapper.writeValueAsString(agendaActualizada);

        mockMvc.perform(put("/api/v1/agendas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idAgenda").value(1))
                .andExpect(jsonPath("$.estado").value("OCUPADO"));
    }

    @Test
    @DisplayName("PUT /api/v1/agendas/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var agendaDatos = new AgendaModel();
        agendaDatos.setIdMedico(111L);
        agendaDatos.setFecha(LocalDate.now());
        agendaDatos.setHoraInicio(LocalTime.of(8, 0));
        agendaDatos.setHoraFin(LocalTime.of(12, 0));
        agendaDatos.setDuracionMinutos(30);
        agendaDatos.setCuposDisponibles(8);
        agendaDatos.setEstado("DISPONIBLE");
        agendaDatos.setActivo(true);

        when(agendaService.actualizar(eq(999L), any(AgendaModel.class)))
                .thenThrow(new RuntimeException("Agenda no encontrada"));

        String jsonRequestBody = objectMapper.writeValueAsString(agendaDatos);

        mockMvc.perform(put("/api/v1/agendas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/v1/agendas/{id} ---

    @Test
    @DisplayName("DELETE /api/v1/agendas/{id} -> Retorna 200 al eliminar")
    public void eliminar_CuandoIdExiste_DeberiaRetornarOk() throws Exception {
        doNothing().when(agendaService).delete(1L);

        mockMvc.perform(delete("/api/v1/agendas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/v1/agendas/{id} -> Retorna 404 cuando no existe")
    public void eliminar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        doThrow(new RuntimeException("Agenda no encontrada")).when(agendaService).delete(999L);

        mockMvc.perform(delete("/api/v1/agendas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/agendas/detalle/{id} (Reactivo / Mono) ---

    @Test
    @DisplayName("GET /api/v1/agendas/detalle/{id} -> Retorna 200 y detalle cuando existe (Reactivo)")
    public void obtenerDetalle_CuandoIdExiste_DeberiaRetornarDetalle() throws Exception {
        var agenda = new AgendaModel();
        agenda.setIdAgenda(1L);
        agenda.setIdMedico(111L);
        agenda.setFecha(LocalDate.now());
        agenda.setHoraInicio(LocalTime.of(8, 0));
        agenda.setHoraFin(LocalTime.of(12, 0));
        agenda.setDuracionMinutos(30);
        agenda.setCuposDisponibles(8);
        agenda.setEstado("DISPONIBLE");
        agenda.setActivo(true);

        var medico = new MedicoDTO();
        medico.setIdMedico(111L);
        medico.setNombreMedico("Juan");
        medico.setApellidoMedico("Perez");

        var detalle = new AgendaDetalleDTO(agenda, medico);

        when(agendaService.obtenerAgendaConMedico(1L)).thenReturn(Mono.just(detalle));

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/agendas/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/agendas/detalle/{id} -> Retorna 404 si el Mono viene vacío")
    public void obtenerDetalle_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(agendaService.obtenerAgendaConMedico(999L)).thenReturn(Mono.empty());

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/agendas/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

package com.hospital.reservas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.reservas.Controller.ReservasController;
import com.hospital.reservas.Dto.AgendaDTO;
import com.hospital.reservas.Dto.MedicoDTO;
import com.hospital.reservas.Dto.PacienteDTO;
import com.hospital.reservas.Dto.ReservaDetalleDTO;
import com.hospital.reservas.Model.ReservasModel;
import com.hospital.reservas.Service.ReservasService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(ReservasController.class)
class ReservasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservasService reservasService;

    // --- TESTS PARA GET /api/v1/reservas ---

    @Test
    @DisplayName("GET /api/v1/reservas -> Retorna 200 y lista de reservas")
    public void listar_CuandoExistenReservas_DeberiaRetornarLista() throws Exception {
        var reserva1 = new ReservasModel();
        reserva1.setIdReservas(1L);
        reserva1.setIdPaciente(12345678L);
        reserva1.setIdMedico(111L);
        reserva1.setIdAgenda(222L);
        reserva1.setFechaAtencion(LocalDate.now());
        reserva1.setHoraAtencion(LocalTime.of(10, 0));
        reserva1.setMotivoConsulta("Dolor de cabeza");
        reserva1.setEstado("PENDIENTE");
        reserva1.setObservacion("Primera consulta");

        var reserva2 = new ReservasModel();
        reserva2.setIdReservas(2L);
        reserva2.setIdPaciente(98765432L);
        reserva2.setIdMedico(333L);
        reserva2.setIdAgenda(444L);
        reserva2.setFechaAtencion(LocalDate.now());
        reserva2.setHoraAtencion(LocalTime.of(11, 0));
        reserva2.setMotivoConsulta("Fiebre");
        reserva2.setEstado("CONFIRMADO");
        reserva2.setObservacion("Segunda consulta");

        List<ReservasModel> lista = Arrays.asList(reserva1, reserva2);
        when(reservasService.listarTodo()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].idReservas").value(1))
                .andExpect(jsonPath("$[0].motivoConsulta").value("Dolor de cabeza"))
                .andExpect(jsonPath("$[1].idReservas").value(2))
                .andExpect(jsonPath("$[1].motivoConsulta").value("Fiebre"));
    }

    @Test
    @DisplayName("GET /api/v1/reservas -> Retorna 204 cuando no hay reservas")
    public void listar_CuandoNoHayReservas_DeberiaRetornarNoContent() throws Exception {
        when(reservasService.listarTodo()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA POST /api/v1/reservas ---

    @Test
    @DisplayName("POST /api/v1/reservas -> Retorna 201 al crear exitosamente")
    public void guardar_CuandoDatosValidos_DeberiaRetornarStatusCreated() throws Exception {
        var reserva = new ReservasModel();
        reserva.setIdReservas(1L);
        reserva.setIdPaciente(11111111L);
        reserva.setIdMedico(222L);
        reserva.setIdAgenda(333L);
        reserva.setFechaAtencion(LocalDate.now());
        reserva.setHoraAtencion(LocalTime.of(10, 0));
        reserva.setMotivoConsulta("Dolor de cabeza");
        reserva.setEstado("PENDIENTE");
        reserva.setObservacion("Primera consulta");

        when(reservasService.guardar(any(ReservasModel.class))).thenReturn(reserva);

        String jsonRequestBody = objectMapper.writeValueAsString(reserva);

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.idReservas").value(1))
                .andExpect(jsonPath("$.motivoConsulta").value("Dolor de cabeza"));
    }

    @Test
    @DisplayName("POST /api/v1/reservas -> Retorna 400 cuando el Body es inválido")
    public void guardar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/reservas/{id} ---

    @Test
    @DisplayName("GET /api/v1/reservas/{id} -> Retorna 200 cuando existe")
    public void buscar_CuandoIdExiste_DeberiaRetornarReserva() throws Exception {
        var reserva = new ReservasModel();
        reserva.setIdReservas(1L);
        reserva.setIdPaciente(12345678L);
        reserva.setIdMedico(111L);
        reserva.setIdAgenda(222L);
        reserva.setFechaAtencion(LocalDate.now());
        reserva.setHoraAtencion(LocalTime.of(10, 0));
        reserva.setMotivoConsulta("Dolor de cabeza");
        reserva.setEstado("PENDIENTE");
        reserva.setObservacion("Primera consulta");

        when(reservasService.buscarId(1L)).thenReturn(reserva);

        mockMvc.perform(get("/api/v1/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idReservas").value(1))
                .andExpect(jsonPath("$.motivoConsulta").value("Dolor de cabeza"));
    }

    @Test
    @DisplayName("GET /api/v1/reservas/{id} -> Retorna 404 cuando no existe")
    public void buscar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(reservasService.buscarId(999L)).thenThrow(new RuntimeException("Reserva no encontrada"));

        mockMvc.perform(get("/api/v1/reservas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/v1/reservas/{id} ---

    @Test
    @DisplayName("PUT /api/v1/reservas/{id} -> Retorna 200 y reserva actualizada")
    public void actualizar_CuandoIdExiste_DeberiaRetornarReservaModificada() throws Exception {
        var reservaActualizada = new ReservasModel();
        reservaActualizada.setIdReservas(1L);
        reservaActualizada.setIdPaciente(12345678L);
        reservaActualizada.setIdMedico(111L);
        reservaActualizada.setIdAgenda(222L);
        reservaActualizada.setFechaAtencion(LocalDate.now());
        reservaActualizada.setHoraAtencion(LocalTime.of(10, 0));
        reservaActualizada.setMotivoConsulta("Dolor de cabeza actualizado");
        reservaActualizada.setEstado("CONFIRMADO");
        reservaActualizada.setObservacion("Observación actualizada");

        when(reservasService.actualizar(eq(1L), any(ReservasModel.class))).thenReturn(reservaActualizada);

        String jsonRequestBody = objectMapper.writeValueAsString(reservaActualizada);

        mockMvc.perform(put("/api/v1/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idReservas").value(1))
                .andExpect(jsonPath("$.motivoConsulta").value("Dolor de cabeza actualizado"));
    }

    @Test
    @DisplayName("PUT /api/v1/reservas/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var reservaDatos = new ReservasModel();
        reservaDatos.setIdPaciente(12345678L);
        reservaDatos.setIdMedico(111L);
        reservaDatos.setIdAgenda(222L);
        reservaDatos.setFechaAtencion(LocalDate.now());
        reservaDatos.setHoraAtencion(LocalTime.of(10, 0));
        reservaDatos.setMotivoConsulta("Dolor de cabeza");
        reservaDatos.setEstado("PENDIENTE");
        reservaDatos.setObservacion("Observación");

        when(reservasService.actualizar(eq(999L), any(ReservasModel.class)))
                .thenThrow(new RuntimeException("Reserva no encontrada"));

        String jsonRequestBody = objectMapper.writeValueAsString(reservaDatos);

        mockMvc.perform(put("/api/v1/reservas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/v1/reservas/{id} ---

    @Test
    @DisplayName("DELETE /api/v1/reservas/{id} -> Retorna 200 al eliminar")
    public void eliminar_CuandoIdExiste_DeberiaRetornarOk() throws Exception {
        doNothing().when(reservasService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/v1/reservas/{id} -> Retorna 200 cuando no existe")
    public void eliminar_CuandoIdNoExiste_DeberiaRetornarOk() throws Exception {
        doNothing().when(reservasService).eliminar(999L);

        mockMvc.perform(delete("/api/v1/reservas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/reservas/detalle/{id} (Reactivo / Mono) ---

    @Test
    @DisplayName("GET /api/v1/reservas/detalle/{id} -> Retorna 200 y detalle cuando existe (Reactivo)")
    public void obtenerDetalles_CuandoIdExiste_DeberiaRetornarDetalle() throws Exception {
        var reserva = new ReservasModel();
        reserva.setIdReservas(1L);
        reserva.setIdPaciente(12345678L);
        reserva.setIdMedico(111L);
        reserva.setIdAgenda(222L);
        reserva.setFechaAtencion(LocalDate.now());
        reserva.setHoraAtencion(LocalTime.of(10, 0));
        reserva.setMotivoConsulta("Dolor de cabeza");
        reserva.setEstado("PENDIENTE");
        reserva.setObservacion("Primera consulta");

        var paciente = new PacienteDTO();
        paciente.setIdPaciente(12345678L);
        paciente.setNombrePaciente("Juan");

        var medico = new MedicoDTO();
        medico.setIdMedico(111L);
        medico.setNombreMedico("Pedro");

        var agenda = new AgendaDTO();
        agenda.setIdAgenda(222L);

        var detalle = new ReservaDetalleDTO(reserva, medico, paciente, agenda);

        when(reservasService.obtenerReservasConPacienteMedicoAgenda(1L)).thenReturn(Mono.just(detalle));

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/reservas/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/reservas/detalle/{id} -> Retorna 404 si el Mono viene vacío")
    public void obtenerDetalles_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(reservasService.obtenerReservasConPacienteMedicoAgenda(999L)).thenReturn(Mono.empty());

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/reservas/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

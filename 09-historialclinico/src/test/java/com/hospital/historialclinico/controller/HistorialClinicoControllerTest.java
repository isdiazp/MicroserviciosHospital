package com.hospital.historialclinico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.historialclinico.Dto.HCDetalleDTO;
import com.hospital.historialclinico.Dto.MedicoDTO;
import com.hospital.historialclinico.Dto.PacienteDTO;
import com.hospital.historialclinico.Dto.ReservaDTO;
import com.hospital.historialclinico.model.HCModel;
import com.hospital.historialclinico.service.HCService;
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

@WebMvcTest(HCController.class)
class HistorialClinicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HCService hcService;

    // --- TESTS PARA GET /api/historiales ---

    @Test
    @DisplayName("GET /api/historiales -> Retorna 200 y lista de historiales")
    public void listar_CuandoExistenHistoriales_DeberiaRetornarLista() throws Exception {
        var hc1 = new HCModel();
        hc1.setId(1L);
        hc1.setIdPaciente(12345678L);
        hc1.setIdMedico(111L);
        hc1.setDiagnostico("Gripe");

        var hc2 = new HCModel();
        hc2.setId(2L);
        hc2.setIdPaciente(98765432L);
        hc2.setIdMedico(222L);
        hc2.setDiagnostico("Fractura");

        List<HCModel> lista = Arrays.asList(hc1, hc2);
        when(hcService.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/historiales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].diagnostico").value("Gripe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].diagnostico").value("Fractura"));
    }

    @Test
    @DisplayName("GET /api/historiales -> Retorna 200 y lista vacía")
    public void listar_CuandoNoHayHistoriales_DeberiaRetornarListaVacia() throws Exception {
        when(hcService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/historiales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- TESTS PARA POST /api/historiales ---

    @Test
    @DisplayName("POST /api/historiales -> Retorna 201 al crear exitosamente")
    public void crear_CuandoDatosValidos_DeberiaRetornarStatusCreated() throws Exception {
        var hc = new HCModel();
        hc.setId(1L);
        hc.setIdPaciente(11111111L);
        hc.setIdMedico(222L);
        hc.setIdReserva(333L);
        hc.setFechaAtencion(LocalDate.now());
        hc.setMotivoConsulta("Dolor de cabeza");
        hc.setDiagnostico("Migraña");

        when(hcService.guardar(any(HCModel.class))).thenReturn(hc);

        String jsonRequestBody = objectMapper.writeValueAsString(hc);

        mockMvc.perform(post("/api/historiales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.diagnostico").value("Migraña"));
    }

    @Test
    @DisplayName("POST /api/historiales -> Retorna 400 cuando el Body es inválido")
    public void crear_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/historiales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/historiales/{id} ---

    @Test
    @DisplayName("GET /api/historiales/{id} -> Retorna 200 cuando existe")
    public void obtener_CuandoIdExiste_DeberiaRetornarHistorial() throws Exception {
        var hc = new HCModel();
        hc.setId(1L);
        hc.setIdPaciente(12345678L);
        hc.setIdMedico(111L);
        hc.setIdReserva(333L);
        hc.setFechaAtencion(LocalDate.now());
        hc.setDiagnostico("Hipertensión");

        when(hcService.buscarPorId(1L)).thenReturn(hc);

        mockMvc.perform(get("/api/historiales/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.diagnostico").value("Hipertensión"));
    }

    @Test
    @DisplayName("GET /api/historiales/{id} -> Retorna 200 cuando no existe (o null)")
    public void obtener_CuandoIdNoExiste_DeberiaRetornarNull() throws Exception {
        when(hcService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/historiales/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/historiales/{id} ---

    @Test
    @DisplayName("DELETE /api/historiales/{id} -> Retorna 204 al eliminar")
    public void borrar_CuandoIdExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(hcService).eliminar(1L);

        mockMvc.perform(delete("/api/historiales/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/historiales/{id} -> Retorna 204 incluso si no existe")
    public void borrar_CuandoIdNoExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(hcService).eliminar(999L);

        mockMvc.perform(delete("/api/historiales/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/historiales/{id} ---

    @Test
    @DisplayName("PUT /api/historiales/{id} -> Retorna 200 y historial actualizado")
    public void actualizar_CuandoIdExiste_DeberiaRetornarHistorialModificado() throws Exception {
        var hcActualizado = new HCModel();
        hcActualizado.setId(1L);
        hcActualizado.setIdPaciente(12345678L);
        hcActualizado.setIdMedico(111L);
        hcActualizado.setIdReserva(333L);
        hcActualizado.setFechaAtencion(LocalDate.now());
        hcActualizado.setMotivoConsulta("Dolor de cabeza");
        hcActualizado.setDiagnostico("Diagnóstico actualizado");

        when(hcService.actualizar(eq(1L), any(HCModel.class))).thenReturn(hcActualizado);

        String jsonRequestBody = objectMapper.writeValueAsString(hcActualizado);

        mockMvc.perform(put("/api/historiales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.diagnostico").value("Diagnóstico actualizado"));
    }

    @Test
    @DisplayName("PUT /api/historiales/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var hcDatos = new HCModel();
        hcDatos.setIdPaciente(12345678L);
        hcDatos.setIdMedico(111L);
        hcDatos.setIdReserva(333L);
        hcDatos.setFechaAtencion(LocalDate.now());
        hcDatos.setMotivoConsulta("Dolor de cabeza");
        hcDatos.setDiagnostico("Diagnóstico");

        when(hcService.actualizar(eq(999L), any(HCModel.class)))
                .thenThrow(new RuntimeException("Historial no encontrado"));

        String jsonRequestBody = objectMapper.writeValueAsString(hcDatos);

        mockMvc.perform(put("/api/historiales/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/historiales/detalle/{id} (Reactivo / Mono) ---

    @Test
    @DisplayName("GET /api/historiales/detalle/{id} -> Retorna 200 y detalle cuando existe (Reactivo)")
    public void detalleClinico_CuandoIdExiste_DeberiaRetornarDetalle() throws Exception {
        var hc = new HCModel();
        hc.setId(1L);
        hc.setIdPaciente(12345678L);
        hc.setIdMedico(111L);
        hc.setDiagnostico("Gripe");

        var paciente = new PacienteDTO();
        paciente.setIdPaciente(12345678L);
        paciente.setNombrePaciente("Juan");

        var medico = new MedicoDTO();
        medico.setIdMedico(111L);
        medico.setNombreMedico("Pedro");

        var reserva = new ReservaDTO();
        reserva.setIdReservas(333L);

        var detalle = new HCDetalleDTO(hc, paciente, medico, reserva);

        when(hcService.obtenerHistorialMedicoGeneral(1L)).thenReturn(Mono.just(detalle));

        MvcResult mvcResult = mockMvc.perform(get("/api/historiales/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/historiales/detalle/{id} -> Retorna 404 si el Mono viene vacío")
    public void detalleClinico_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(hcService.obtenerHistorialMedicoGeneral(999L)).thenReturn(Mono.empty());

        MvcResult mvcResult = mockMvc.perform(get("/api/historiales/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

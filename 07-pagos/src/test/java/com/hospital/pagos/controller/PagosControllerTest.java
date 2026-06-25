package com.hospital.pagos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.pagos.Dto.PagosDetalleDTO;
import com.hospital.pagos.Dto.PacienteDTO;
import com.hospital.pagos.model.Pagos;
import com.hospital.pagos.service.PagosService;
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

@WebMvcTest(PagosController.class)
class PagosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PagosService pagosService;

    // --- TESTS PARA GET /api/pagos/listar ---

    @Test
    @DisplayName("GET /api/pagos/listar -> Retorna 200 y lista de pagos")
    public void listar_CuandoExistenPagos_DeberiaRetornarLista() throws Exception {
        var pago1 = new Pagos();
        pago1.setId(1L);
        pago1.setMonto(50000.0);
        pago1.setIdPaciente(12345678L);

        var pago2 = new Pagos();
        pago2.setId(2L);
        pago2.setMonto(25000.0);
        pago2.setIdPaciente(98765432L);

        List<Pagos> lista = Arrays.asList(pago1, pago2);
        when(pagosService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/pagos/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].idPaciente").value(12345678L))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].monto").value(25000.0));
    }

    @Test
    @DisplayName("GET /api/pagos/listar -> Retorna 200 y lista vacía")
    public void listar_CuandoNoHayPagos_DeberiaRetornarListaVacia() throws Exception {
        when(pagosService.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/pagos/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- TESTS PARA POST /api/pagos/guardar ---

    @Test
    @DisplayName("POST /api/pagos/guardar -> Retorna 201 al guardar exitosamente")
    public void guardar_CuandoDatosValidos_DeberiaRetornarStatusCreated() throws Exception {
        var pago = new Pagos();
        pago.setId(1L);
        pago.setMonto(15000.0);
        pago.setIdPaciente(11111111L);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMedioPago("Tarjeta");
        pago.setEstadoPago("PENDIENTE");
        pago.setActivo(true);

        doNothing().when(pagosService).guardar(any(Pagos.class));

        String jsonRequestBody = objectMapper.writeValueAsString(pago);

        mockMvc.perform(post("/api/pagos/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("POST /api/pagos/guardar -> Retorna 400 cuando el Body es inválido o vacío")
    public void guardar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        // Enviamos un JSON vacío para forzar fallos en las anotaciones @Valid de Jakarta
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/pagos/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/pagos/eliminar/{id} ---

    @Test
    @DisplayName("DELETE /api/pagos/eliminar/{id} -> Retorna 204 al eliminar un ID existente")
    public void eliminar_CuandoIdExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(pagosService).eliminar(1L);

        mockMvc.perform(delete("/api/pagos/eliminar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/pagos/eliminar/{id} -> Retorna 204 incluso si el ID no existe")
    public void eliminar_CuandoIdNoExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(pagosService).eliminar(999L);

        mockMvc.perform(delete("/api/pagos/eliminar/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/pagos/{id} ---

    @Test
    @DisplayName("PUT /api/pagos/{id} -> Retorna 200 y el pago actualizado")
    public void actualizar_CuandoIdExiste_DeberiaRetornarPagoModificado() throws Exception {
        var pagoActualizado = new Pagos();
        pagoActualizado.setId(1L);
        pagoActualizado.setMonto(35000.0);
        pagoActualizado.setIdPaciente(12345678L);
        pagoActualizado.setFechaPago(LocalDateTime.now());
        pagoActualizado.setMedioPago("Efectivo");
        pagoActualizado.setEstadoPago("PAGADO");
        pagoActualizado.setActivo(true);

        when(pagosService.actualizar(eq(1L), any(Pagos.class))).thenReturn(pagoActualizado);

        String jsonRequestBody = objectMapper.writeValueAsString(pagoActualizado);

        mockMvc.perform(put("/api/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(35000.0))
                .andExpect(jsonPath("$.idPaciente").value(12345678L));
    }

    @Test
    @DisplayName("PUT /api/pagos/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var pagoDatos = new Pagos();
        pagoDatos.setMonto(35000.0);
        pagoDatos.setIdPaciente(12345678L);
        pagoDatos.setFechaPago(LocalDateTime.now());
        pagoDatos.setMedioPago("Transferencia");
        pagoDatos.setEstadoPago("PENDIENTE");
        pagoDatos.setActivo(true);

        when(pagosService.actualizar(eq(999L), any(Pagos.class)))
                .thenThrow(new RuntimeException("Pago no encontrado"));

        String jsonRequestBody = objectMapper.writeValueAsString(pagoDatos);

        mockMvc.perform(put("/api/pagos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/pagos/detalle/{id} (Reactivo / Mono) ---

    @Test
    @DisplayName("GET /api/pagos/detalle/{id} -> Retorna 200 y detalle cuando existe (Reactivo)")
    public void obtenerDetalle_CuandoIdExiste_DeberiaRetornarDetalle() throws Exception {
        var pago = new Pagos();
        pago.setId(1L);
        pago.setMonto(50000.0);
        pago.setIdPaciente(12345678L);

        var paciente = new PacienteDTO();
        paciente.setIdPaciente(12345678L);
        paciente.setRutPaciente("12345678-9");
        paciente.setNombrePaciente("Juan");
        paciente.setApellidoPaciente("Perez");

        var detalle = new PagosDetalleDTO(pago, paciente);

        when(pagosService.obtenerPagosConPaciente(1L)).thenReturn(Mono.just(detalle));

        // 1. Iniciamos la petición asíncrona
        MvcResult mvcResult = mockMvc.perform(get("/api/pagos/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        // 2. Despachamos el resultado asíncrono
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/pagos/detalle/{id} -> Retorna 404 si el Mono viene vacío")
    public void obtenerDetalle_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(pagosService.obtenerPagosConPaciente(999L)).thenReturn(Mono.empty());

        // 1. Iniciamos la petición asíncrona
        MvcResult mvcResult = mockMvc.perform(get("/api/pagos/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        // 2. Despachamos el resultado asíncrono esperando el 404 del defaultIfEmpty
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
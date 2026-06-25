package com.hospital.consultas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.consultas.Dto.ConsultaDetalleDTO;
import com.hospital.consultas.Dto.PacienteDTO;
import com.hospital.consultas.model.ConsultasModel;
import com.hospital.consultas.service.ConsultasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultasController.class)
class ConsultasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConsultasService consultasService;

    @Test
    @DisplayName("GET /api/consultas -> Retorna 200 y lista de consultas")
    public void listar_CuandoExistenConsultas_DeberiaRetornarLista() throws Exception {
        var consulta1 = new ConsultasModel(1L, true, "General", 2, "Box A1");
        consulta1.setId(1L);

        var consulta2 = new ConsultasModel(2L, false, "Pediatría", 3, "Box B1");
        consulta2.setId(2L);

        List<ConsultasModel> lista = Arrays.asList(consulta1, consulta2);
        when(consultasService.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/consultas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreBox").value("Box A1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombreBox").value("Box B1"));
    }

    @Test
    @DisplayName("GET /api/consultas -> Retorna 200 y lista vacía cuando no hay consultas")
    public void listar_CuandoNoHayConsultas_DeberiaRetornarListaVacia() throws Exception {
        when(consultasService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/consultas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("POST /api/consultas -> Retorna 201 y consulta creada")
    public void crear_CuandoDatosValidos_DeberiaRetornarConsultaCreada() throws Exception {
        var consultaCreada = new ConsultasModel(1L, true, "Cardiología", 1, "Box C1");
        consultaCreada.setId(1L);

        when(consultasService.guardar(any(ConsultasModel.class))).thenReturn(consultaCreada);

        String jsonRequestBody = """
                {
                    "idPaciente": 1,
                    "nombreBox": "Box C1",
                    "piso": 1,
                    "tipoBox": "Cardiología",
                    "disponible": true
                }
                """;

        mockMvc.perform(post("/api/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreBox").value("Box C1"))
                .andExpect(jsonPath("$.tipoBox").value("Cardiología"));
    }

    @Test
    @DisplayName("POST /api/consultas -> Retorna 201 y consulta con datos completos")
    public void crear_CuandoTodosLosDatosValidos_DeberiaRetornarConsulta() throws Exception {
        var consultaCreada = new ConsultasModel(3L, false, "Urgencias", 2, "Box D1");
        consultaCreada.setId(3L);

        when(consultasService.guardar(any(ConsultasModel.class))).thenReturn(consultaCreada);

        String jsonRequestBody = """
                {
                    "idPaciente": 3,
                    "nombreBox": "Box D1",
                    "piso": 2,
                    "tipoBox": "Urgencias",
                    "disponible": false
                }
                """;

        mockMvc.perform(post("/api/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.disponible").value(false));
    }

    @Test
    @DisplayName("GET /api/consultas/{id} -> Retorna 200 y consulta cuando existe")
    public void obtener_CuandoExiste_DeberiaRetornarConsulta() throws Exception {
        var consulta = new ConsultasModel(1L, true, "General", 2, "Box A1");
        consulta.setId(1L);

        when(consultasService.buscarPorId(1L)).thenReturn(consulta);

        mockMvc.perform(get("/api/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreBox").value("Box A1"));
    }

    @Test
    @DisplayName("GET /api/consultas/{id} -> Retorna 200 y no tiene contenido cuando no existe")
    public void obtener_CuandoNoExiste_DeberiaRetornarVacio() throws Exception {
        when(consultasService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/consultas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/consultas/{id} -> Retorna 204 cuando elimina exitosamente")
    public void borrar_CuandoExiste_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/consultas/{id} -> Retorna 204 cuando ID no existe")
    public void borrar_CuandoNoExiste_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/consultas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("PUT /api/consultas/{id} -> Retorna 200 y consulta actualizada")
    public void actualizar_CuandoExiste_DeberiaRetornarConsultaActualizada() throws Exception {
        var consultaActualizada = new ConsultasModel(1L, false, "Urgencias", 2, "Box A1-Actualizado");
        consultaActualizada.setId(1L);

        when(consultasService.actualizar(eq(1L), any(ConsultasModel.class))).thenReturn(consultaActualizada);

        String jsonRequestBody = """
                {
                    "idPaciente": 1,
                    "nombreBox": "Box A1-Actualizado",
                    "piso": 2,
                    "tipoBox": "Urgencias",
                    "disponible": false
                }
                """;

        mockMvc.perform(put("/api/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreBox").value("Box A1-Actualizado"))
                .andExpect(jsonPath("$.tipoBox").value("Urgencias"));
    }

    @Test
    @DisplayName("PUT /api/consultas/{id} -> Retorna 404 cuando consulta no existe")
    public void actualizar_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(consultasService.actualizar(eq(999L), any(ConsultasModel.class)))
                .thenThrow(new RuntimeException("Consulta/Box no encontrado con el ID: 999"));

        String jsonRequestBody = """
                {
                    "idPaciente": 999,
                    "nombreBox": "Box X",
                    "piso": 2,
                    "tipoBox": "Urgencias",
                    "disponible": false
                }
                """;

        // Nota: Asegúrate de tener un @ExceptionHandler o @ControllerAdvice
        // en tu aplicación que convierta RuntimeException a 404 NOT_FOUND.
        mockMvc.perform(put("/api/consultas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/consultas/detalle/{id} -> Retorna 200 y detalle cuando existe (Reactivo)")
    public void obtenerDetalle_CuandoExiste_DeberiaRetornarDetalle() throws Exception {
        var consulta = new ConsultasModel(1L, true, "General", 2, "Box A1");
        consulta.setId(1L);

        var paciente = new PacienteDTO();
        var detalle = new ConsultaDetalleDTO(consulta, paciente);

        when(consultasService.obtenerConsultaConPaciente(1L))
                .thenReturn(Mono.just(detalle));

        // 1. Inicia la petición asíncrona (Mono)
        MvcResult mvcResult = mockMvc.perform(get("/api/consultas/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        // 2. Despacha el resultado de forma no bloqueante y evalúa la respuesta final
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.consulta.id").value(1))
                .andExpect(jsonPath("$.consulta.nombreBox").value("Box A1"));
    }

    @Test
    @DisplayName("GET /api/consultas/detalle/{id} -> Retorna 404 cuando no existe (Reactivo)")
    public void obtenerDetalle_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(consultasService.obtenerConsultaConPaciente(999L))
                .thenReturn(Mono.empty());

        // 1. Inicia la petición asíncrona
        MvcResult mvcResult = mockMvc.perform(get("/api/consultas/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        // 2. Despacha el resultado
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
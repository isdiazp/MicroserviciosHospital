package com.hospital.recetas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.recetas.Dto.PacienteDTO;
import com.hospital.recetas.Dto.MedicoDTO;
import com.hospital.recetas.Dto.RecetaConMedicoDTO;
import com.hospital.recetas.Dto.RecetasDetalleDTO;
import com.hospital.recetas.Dto.ReservaDTO;
import com.hospital.recetas.model.RecetasModel;
import com.hospital.recetas.service.RecetasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(RecetasController.class)
class RecetasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecetasService recetasService;

    // --- TESTS PARA GET /api/recetas ---

    @Test
    @DisplayName("GET /api/recetas -> Retorna 200 y lista de recetas")
    public void listar_CuandoExistenRecetas_DeberiaRetornarLista() throws Exception {
        var receta1 = new RecetaConMedicoDTO();
        receta1.setId(1L);
        receta1.setIdPaciente(12345678L);
        receta1.setIdMedico(111L);
        receta1.setNombreMedico("Juan");
        receta1.setApellidoMedico("Perez");
        receta1.setMedicamentos("Paracetamol");

        var receta2 = new RecetaConMedicoDTO();
        receta2.setId(2L);
        receta2.setIdPaciente(98765432L);
        receta2.setIdMedico(222L);
        receta2.setNombreMedico("Maria");
        receta2.setApellidoMedico("Gomez");
        receta2.setMedicamentos("Ibuprofeno");

        List<RecetaConMedicoDTO> lista = Arrays.asList(receta1, receta2);
        when(recetasService.listarTodasConMedico()).thenReturn(lista);

        mockMvc.perform(get("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].medicamentos").value("Paracetamol"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].medicamentos").value("Ibuprofeno"));
    }

    @Test
    @DisplayName("GET /api/recetas -> Retorna 200 y lista vacía")
    public void listar_CuandoNoHayRecetas_DeberiaRetornarListaVacia() throws Exception {
        when(recetasService.listarTodasConMedico()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // --- TESTS PARA POST /api/recetas ---

    @Test
    @DisplayName("POST /api/recetas -> Retorna 201 al crear exitosamente")
    public void crear_CuandoDatosValidos_DeberiaRetornarStatusCreated() throws Exception {
        var receta = new RecetasModel();
        receta.setId(1L);
        receta.setIdPaciente(11111111L);
        receta.setIdMedico(222L);
        receta.setIdReserva(333L);
        receta.setFechaEmision(LocalDate.now());
        receta.setMedicamentos("Paracetamol 500mg");

        when(recetasService.guardar(any(RecetasModel.class))).thenReturn(receta);

        String jsonRequestBody = objectMapper.writeValueAsString(receta);

        mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.medicamentos").value("Paracetamol 500mg"));
    }

    @Test
    @DisplayName("POST /api/recetas -> Retorna 400 cuando el Body es inválido")
    public void crear_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/recetas/{id} ---

    @Test
    @DisplayName("GET /api/recetas/{id} -> Retorna 200 cuando existe")
    public void obtener_CuandoIdExiste_DeberiaRetornarReceta() throws Exception {
        var receta = new RecetasModel();
        receta.setId(1L);
        receta.setIdPaciente(12345678L);
        receta.setIdMedico(111L);
        receta.setIdReserva(333L);
        receta.setFechaEmision(LocalDate.now());
        receta.setMedicamentos("Amoxicilina");

        when(recetasService.buscarPorId(1L)).thenReturn(receta);

        mockMvc.perform(get("/api/recetas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.medicamentos").value("Amoxicilina"));
    }

    @Test
    @DisplayName("GET /api/recetas/{id} -> Retorna 200 cuando no existe (o null)")
    public void obtener_CuandoIdNoExiste_DeberiaRetornarNull() throws Exception {
        when(recetasService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/recetas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/recetas/{id} ---

    @Test
    @DisplayName("DELETE /api/recetas/{id} -> Retorna 204 al eliminar")
    public void borrar_CuandoIdExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(recetasService).eliminar(1L);

        mockMvc.perform(delete("/api/recetas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/recetas/{id} -> Retorna 204 incluso si no existe")
    public void borrar_CuandoIdNoExiste_DeberiaRetornarNoContent() throws Exception {
        doNothing().when(recetasService).eliminar(999L);

        mockMvc.perform(delete("/api/recetas/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/recetas/{id} ---

    @Test
    @DisplayName("PUT /api/recetas/{id} -> Retorna 200 y receta actualizada")
    public void actualizar_CuandoIdExiste_DeberiaRetornarRecetaModificada() throws Exception {
        var recetaActualizada = new RecetasModel();
        recetaActualizada.setId(1L);
        recetaActualizada.setIdPaciente(12345678L);
        recetaActualizada.setIdMedico(111L);
        recetaActualizada.setIdReserva(333L);
        recetaActualizada.setFechaEmision(LocalDate.now());
        recetaActualizada.setMedicamentos("Antibiotico actualizado");

        when(recetasService.actualizar(eq(1L), any(RecetasModel.class))).thenReturn(recetaActualizada);

        String jsonRequestBody = objectMapper.writeValueAsString(recetaActualizada);

        mockMvc.perform(put("/api/recetas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.medicamentos").value("Antibiotico actualizado"));
    }

    @Test
    @DisplayName("PUT /api/recetas/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var recetaDatos = new RecetasModel();
        recetaDatos.setIdPaciente(12345678L);
        recetaDatos.setIdMedico(111L);
        recetaDatos.setIdReserva(333L);
        recetaDatos.setFechaEmision(LocalDate.now());
        recetaDatos.setMedicamentos("Medicamento");

        when(recetasService.actualizar(eq(999L), any(RecetasModel.class)))
                .thenThrow(new RuntimeException("Receta no encontrada"));

        String jsonRequestBody = objectMapper.writeValueAsString(recetaDatos);

        mockMvc.perform(put("/api/recetas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/recetas/con-medico ---

    @Test
    @DisplayName("GET /api/recetas/con-medico -> Retorna 200 y lista")
    public void detalleMedico_CuandoExistenRecetas_DeberiaRetornarLista() throws Exception {
        var receta1 = new RecetaConMedicoDTO();
        receta1.setId(1L);
        receta1.setNombreMedico("Juan");
        receta1.setMedicamentos("Paracetamol");

        List<RecetaConMedicoDTO> lista = Arrays.asList(receta1);
        when(recetasService.listarTodasConMedico()).thenReturn(lista);

        mockMvc.perform(get("/api/recetas/con-medico")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/recetas/con-medico -> Retorna 204 cuando está vacío")
    public void detalleMedico_CuandoNoHayRecetas_DeberiaRetornarNoContent() throws Exception {
        when(recetasService.listarTodasConMedico()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/recetas/con-medico")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/recetas/medico/{id} ---

    @Test
    @DisplayName("GET /api/recetas/medico/{id} -> Retorna 200 y lista")
    public void recetasPorMedico_CuandoExistenRecetas_DeberiaRetornarLista() throws Exception {
        var receta = new RecetasModel();
        receta.setId(1L);
        receta.setIdMedico(111L);
        receta.setMedicamentos("Paracetamol");

        var paciente = new PacienteDTO();
        paciente.setIdPaciente(12345678L);
        paciente.setNombrePaciente("Juan");

        var medico = new MedicoDTO();
        medico.setIdMedico(111L);
        medico.setNombreMedico("Pedro");

        var reserva = new ReservaDTO();
        reserva.setIdReservas(333L);

        var detalle = new RecetasDetalleDTO(receta, paciente, medico, reserva);

        List<RecetasDetalleDTO> lista = Arrays.asList(detalle);
        when(recetasService.obtenerDetalleRecetasPorMedico(111L)).thenReturn(lista);

        mockMvc.perform(get("/api/recetas/medico/111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].recetas.id").value(1));
    }

    @Test
    @DisplayName("GET /api/recetas/medico/{id} -> Retorna 204 cuando no hay recetas")
    public void recetasPorMedico_CuandoNoHayRecetas_DeberiaRetornarNoContent() throws Exception {
        when(recetasService.obtenerDetalleRecetasPorMedico(999L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/recetas/medico/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}

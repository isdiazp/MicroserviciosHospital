package com.hospital.paciente.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.paciente.Model.PacienteModel;
import com.hospital.paciente.Service.PacienteService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PacienteService pacienteService;

    // --- TESTS PARA GET /api/v1/pacientes ---

    @Test
    @DisplayName("GET /api/v1/pacientes -> Retorna 200 y lista de pacientes")
    public void listar_CuandoExistenPacientes_DeberiaRetornarLista() throws Exception {
        var paciente1 = new PacienteModel();
        paciente1.setIdPaciente(1L);
        paciente1.setRutPaciente("18.456.789-5");
        paciente1.setNombrePaciente("Amalia");
        paciente1.setApellidoPaciente("Muñoz");
        paciente1.setFechaNacimiento(LocalDate.of(2005, 1, 11));
        paciente1.setSexoPaciente("Femenino");
        paciente1.setCorreoPaciente("amalia@gmail.com");
        paciente1.setTelefonoPaciente("912345678");
        paciente1.setDireccionPaciente("Puerto Montt");

        var paciente2 = new PacienteModel();
        paciente2.setIdPaciente(2L);
        paciente2.setRutPaciente("17.639.800-0");
        paciente2.setNombrePaciente("Francisco");
        paciente2.setApellidoPaciente("Fuentes");
        paciente2.setFechaNacimiento(LocalDate.of(2004, 5, 20));
        paciente2.setSexoPaciente("Masculino");
        paciente2.setCorreoPaciente("francisco@gmail.com");
        paciente2.setTelefonoPaciente("987654321");
        paciente2.setDireccionPaciente("Calbuco");

        List<PacienteModel> lista = Arrays.asList(paciente1, paciente2);

        when(pacienteService.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].idPaciente").value(1))
                .andExpect(jsonPath("$[0].rutPaciente").value("18.456.789-5"))
                .andExpect(jsonPath("$[0].nombrePaciente").value("Amalia"))
                .andExpect(jsonPath("$[0].apellidoPaciente").value("Muñoz"))
                .andExpect(jsonPath("$[1].idPaciente").value(2))
                .andExpect(jsonPath("$[1].rutPaciente").value("17.639.800-0"))
                .andExpect(jsonPath("$[1].nombrePaciente").value("Francisco"))
                .andExpect(jsonPath("$[1].apellidoPaciente").value("Fuentes"));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes -> Retorna 204 cuando no hay pacientes")
    public void listar_CuandoNoHayPacientes_DeberiaRetornarNoContent() throws Exception {
        when(pacienteService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA POST /api/v1/pacientes ---

    @Test
    @DisplayName("POST /api/v1/pacientes -> Retorna 201 al crear exitosamente")
    public void guardar_CuandoDatosValidos_DeberiaRetornarCreated() throws Exception {
        var paciente = new PacienteModel();
        paciente.setIdPaciente(1L);
        paciente.setRutPaciente("18.456.789-5");
        paciente.setNombrePaciente("Amalia");
        paciente.setApellidoPaciente("Muñoz");
        paciente.setFechaNacimiento(LocalDate.of(2005, 1, 11));
        paciente.setSexoPaciente("Femenino");
        paciente.setCorreoPaciente("amalia@gmail.com");
        paciente.setTelefonoPaciente("912345678");
        paciente.setDireccionPaciente("Puerto Montt");

        when(pacienteService.guardar(any(PacienteModel.class))).thenReturn(paciente);

        String jsonRequestBody = objectMapper.writeValueAsString(paciente);

        mockMvc.perform(post("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.idPaciente").value(1))
                .andExpect(jsonPath("$.rutPaciente").value("18.456.789-5"))
                .andExpect(jsonPath("$.nombrePaciente").value("Amalia"))
                .andExpect(jsonPath("$.apellidoPaciente").value("Muñoz"))
                .andExpect(jsonPath("$.sexoPaciente").value("Femenino"))
                .andExpect(jsonPath("$.correoPaciente").value("amalia@gmail.com"))
                .andExpect(jsonPath("$.telefonoPaciente").value("912345678"))
                .andExpect(jsonPath("$.direccionPaciente").value("Puerto Montt"));
    }

    @Test
    @DisplayName("POST /api/v1/pacientes -> Retorna 400 cuando el body es inválido")
    public void guardar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/pacientes/{id} ---

    @Test
    @DisplayName("GET /api/v1/pacientes/{id} -> Retorna 200 cuando existe")
    public void buscar_CuandoIdExiste_DeberiaRetornarPaciente() throws Exception {
        var paciente = new PacienteModel();
        paciente.setIdPaciente(1L);
        paciente.setRutPaciente("18.456.789-5");
        paciente.setNombrePaciente("Amalia");
        paciente.setApellidoPaciente("Muñoz");
        paciente.setFechaNacimiento(LocalDate.of(2005, 1, 11));
        paciente.setSexoPaciente("Femenino");
        paciente.setCorreoPaciente("amalia@gmail.com");
        paciente.setTelefonoPaciente("912345678");
        paciente.setDireccionPaciente("Puerto Montt");

        when(pacienteService.buscarPorId(1L)).thenReturn(paciente);

        mockMvc.perform(get("/api/v1/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idPaciente").value(1))
                .andExpect(jsonPath("$.rutPaciente").value("18.456.789-5"))
                .andExpect(jsonPath("$.nombrePaciente").value("Amalia"))
                .andExpect(jsonPath("$.apellidoPaciente").value("Muñoz"));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/{id} -> Retorna 404 cuando no existe")
    public void buscar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(pacienteService.buscarPorId(999L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        mockMvc.perform(get("/api/v1/pacientes/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/v1/pacientes/{id} ---

    @Test
    @DisplayName("PUT /api/v1/pacientes/{id} -> Retorna 200 y paciente actualizado")
    public void actualizar_CuandoIdExiste_DeberiaRetornarPacienteActualizado() throws Exception {
        var pacienteActualizado = new PacienteModel();
        pacienteActualizado.setIdPaciente(1L);
        pacienteActualizado.setRutPaciente("18.456.789-5");
        pacienteActualizado.setNombrePaciente("Amalia");
        pacienteActualizado.setApellidoPaciente("Muñoz Actualizado");
        pacienteActualizado.setFechaNacimiento(LocalDate.of(2005, 1, 11));
        pacienteActualizado.setSexoPaciente("Femenino");
        pacienteActualizado.setCorreoPaciente("amalia.actualizada@gmail.com");
        pacienteActualizado.setTelefonoPaciente("912345678");
        pacienteActualizado.setDireccionPaciente("Puerto Montt");

        when(pacienteService.actualizar(eq(1L), any(PacienteModel.class)))
                .thenReturn(pacienteActualizado);

        String jsonRequestBody = objectMapper.writeValueAsString(pacienteActualizado);

        mockMvc.perform(put("/api/v1/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idPaciente").value(1))
                .andExpect(jsonPath("$.rutPaciente").value("18.456.789-5"))
                .andExpect(jsonPath("$.nombrePaciente").value("Amalia"))
                .andExpect(jsonPath("$.apellidoPaciente").value("Muñoz Actualizado"))
                .andExpect(jsonPath("$.correoPaciente").value("amalia.actualizada@gmail.com"));
    }

    @Test
    @DisplayName("PUT /api/v1/pacientes/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        var pacienteDatos = new PacienteModel();
        pacienteDatos.setRutPaciente("18.456.789-5");
        pacienteDatos.setNombrePaciente("Amalia");
        pacienteDatos.setApellidoPaciente("Muñoz");
        pacienteDatos.setFechaNacimiento(LocalDate.of(2005, 1, 11));
        pacienteDatos.setSexoPaciente("Femenino");
        pacienteDatos.setCorreoPaciente("amalia@gmail.com");
        pacienteDatos.setTelefonoPaciente("912345678");
        pacienteDatos.setDireccionPaciente("Puerto Montt");

        when(pacienteService.actualizar(eq(999L), any(PacienteModel.class)))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        String jsonRequestBody = objectMapper.writeValueAsString(pacienteDatos);

        mockMvc.perform(put("/api/v1/pacientes/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("PUT /api/v1/pacientes/{id} -> Retorna 400 cuando el body es inválido")
    public void actualizar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(put("/api/v1/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/v1/pacientes/{id} ---

    @Test
    @DisplayName("DELETE /api/v1/pacientes/{id} -> Retorna 200 al eliminar")
    public void eliminar_CuandoIdExiste_DeberiaRetornarOk() throws Exception {
        doNothing().when(pacienteService).delete(1L);

        mockMvc.perform(delete("/api/v1/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/v1/pacientes/{id} -> Retorna 404 cuando no existe")
    public void eliminar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        doThrow(new RuntimeException("Paciente no encontrado"))
                .when(pacienteService).delete(999L);

        mockMvc.perform(delete("/api/v1/pacientes/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}

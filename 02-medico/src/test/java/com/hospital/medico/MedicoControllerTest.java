package com.hospital.medico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.medico.Dto.EspecialidadDTO;
import com.hospital.medico.Dto.MedicoDetalleDTO;
import com.hospital.medico.Model.MedicoModel;
import com.hospital.medico.Service.MedicoService;
import com.hospital.medico.Controller.MedicoController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicoController.class)
public class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedicoService medicoService;

    // --- TESTS PARA GET /api/v1/medicos ---

    @Test
    @DisplayName("GET /api/v1/medicos -> Retorna 200 y lista de médicos")
    public void listar_CuandoExistenMedicos_DeberiaRetornarLista() throws Exception {
        MedicoModel medico1 = new MedicoModel();
        medico1.setIdMedico(1L);
        medico1.setRutMedico("18.456.789-5");
        medico1.setNombreMedico("Juan");
        medico1.setApellidoMedico("Soto");
        medico1.setTelefonoMedico("912345678");
        medico1.setCorreoMedico("juan.soto@gmail.com");
        medico1.setIdEspecialidad(1L);
        medico1.setAnniosExperiencia(10);

        MedicoModel medico2 = new MedicoModel();
        medico2.setIdMedico(2L);
        medico2.setRutMedico("17.639.800-0");
        medico2.setNombreMedico("Pedro");
        medico2.setApellidoMedico("Suazo");
        medico2.setTelefonoMedico("987654321");
        medico2.setCorreoMedico("pedro.suazo@gmail.com");
        medico2.setIdEspecialidad(2L);
        medico2.setAnniosExperiencia(8);

        List<MedicoModel> lista = Arrays.asList(medico1, medico2);

        when(medicoService.listarTodo()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].idMedico").value(1))
                .andExpect(jsonPath("$[0].rutMedico").value("18.456.789-5"))
                .andExpect(jsonPath("$[0].nombreMedico").value("Juan"))
                .andExpect(jsonPath("$[0].apellidoMedico").value("Soto"))
                .andExpect(jsonPath("$[0].telefonoMedico").value("912345678"))
                .andExpect(jsonPath("$[0].correoMedico").value("juan.soto@gmail.com"))
                .andExpect(jsonPath("$[0].idEspecialidad").value(1))
                .andExpect(jsonPath("$[0].anniosExperiencia").value(10))
                .andExpect(jsonPath("$[1].idMedico").value(2))
                .andExpect(jsonPath("$[1].rutMedico").value("17.639.800-0"))
                .andExpect(jsonPath("$[1].nombreMedico").value("Pedro"))
                .andExpect(jsonPath("$[1].apellidoMedico").value("Suazo"));
    }

    @Test
    @DisplayName("GET /api/v1/medicos -> Retorna 204 cuando no hay médicos")
    public void listar_CuandoNoHayMedicos_DeberiaRetornarNoContent() throws Exception {
        when(medicoService.listarTodo()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    // --- TESTS PARA POST /api/v1/medicos ---

    @Test
    @DisplayName("POST /api/v1/medicos -> Retorna 201 al crear exitosamente")
    public void guardar_CuandoDatosValidos_DeberiaRetornarCreated() throws Exception {
        MedicoModel medico = new MedicoModel();
        medico.setIdMedico(1L);
        medico.setRutMedico("18.456.789-5");
        medico.setNombreMedico("Juan");
        medico.setApellidoMedico("Soto");
        medico.setTelefonoMedico("912345678");
        medico.setCorreoMedico("juan.soto@gmail.com");
        medico.setIdEspecialidad(1L);
        medico.setAnniosExperiencia(10);

        when(medicoService.guardar(any(MedicoModel.class))).thenReturn(medico);

        String jsonRequestBody = objectMapper.writeValueAsString(medico);

        mockMvc.perform(post("/api/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.idMedico").value(1))
                .andExpect(jsonPath("$.rutMedico").value("18.456.789-5"))
                .andExpect(jsonPath("$.nombreMedico").value("Juan"))
                .andExpect(jsonPath("$.apellidoMedico").value("Soto"))
                .andExpect(jsonPath("$.telefonoMedico").value("912345678"))
                .andExpect(jsonPath("$.correoMedico").value("juan.soto@gmail.com"))
                .andExpect(jsonPath("$.idEspecialidad").value(1))
                .andExpect(jsonPath("$.anniosExperiencia").value(10));
    }

    @Test
    @DisplayName("POST /api/v1/medicos -> Retorna 400 cuando el body es inválido")
    public void guardar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/medicos/{id} ---

    @Test
    @DisplayName("GET /api/v1/medicos/{id} -> Retorna 200 cuando existe")
    public void buscar_CuandoIdExiste_DeberiaRetornarMedico() throws Exception {
        MedicoModel medico = new MedicoModel();
        medico.setIdMedico(1L);
        medico.setRutMedico("18.456.789-5");
        medico.setNombreMedico("Juan");
        medico.setApellidoMedico("Soto");
        medico.setTelefonoMedico("912345678");
        medico.setCorreoMedico("juan.soto@gmail.com");
        medico.setIdEspecialidad(1L);
        medico.setAnniosExperiencia(10);

        when(medicoService.buscarId(1L)).thenReturn(medico);

        mockMvc.perform(get("/api/v1/medicos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idMedico").value(1))
                .andExpect(jsonPath("$.rutMedico").value("18.456.789-5"))
                .andExpect(jsonPath("$.nombreMedico").value("Juan"))
                .andExpect(jsonPath("$.apellidoMedico").value("Soto"));
    }

    @Test
    @DisplayName("GET /api/v1/medicos/{id} -> Retorna 404 cuando no existe")
    public void buscar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(medicoService.buscarId(999L))
                .thenThrow(new RuntimeException("Medico no encontrado"));

        mockMvc.perform(get("/api/v1/medicos/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/v1/medicos/{id} ---

    @Test
    @DisplayName("PUT /api/v1/medicos/{id} -> Retorna 200 y médico actualizado")
    public void actualizar_CuandoIdExiste_DeberiaRetornarMedicoActualizado() throws Exception {
        MedicoModel medicoActualizado = new MedicoModel();
        medicoActualizado.setIdMedico(1L);
        medicoActualizado.setRutMedico("18.456.789-5");
        medicoActualizado.setNombreMedico("Juan");
        medicoActualizado.setApellidoMedico("Soto Actualizado");
        medicoActualizado.setTelefonoMedico("912345678");
        medicoActualizado.setCorreoMedico("juan.actualizado@gmail.com");
        medicoActualizado.setIdEspecialidad(1L);
        medicoActualizado.setAnniosExperiencia(12);

        when(medicoService.actualizar(eq(1L), any(MedicoModel.class)))
                .thenReturn(medicoActualizado);

        String jsonRequestBody = objectMapper.writeValueAsString(medicoActualizado);

        mockMvc.perform(put("/api/v1/medicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idMedico").value(1))
                .andExpect(jsonPath("$.rutMedico").value("18.456.789-5"))
                .andExpect(jsonPath("$.nombreMedico").value("Juan"))
                .andExpect(jsonPath("$.apellidoMedico").value("Soto Actualizado"))
                .andExpect(jsonPath("$.correoMedico").value("juan.actualizado@gmail.com"))
                .andExpect(jsonPath("$.anniosExperiencia").value(12));
    }

    @Test
    @DisplayName("PUT /api/v1/medicos/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        MedicoModel medicoDatos = new MedicoModel();
        medicoDatos.setRutMedico("18.456.789-5");
        medicoDatos.setNombreMedico("Juan");
        medicoDatos.setApellidoMedico("Soto");
        medicoDatos.setTelefonoMedico("912345678");
        medicoDatos.setCorreoMedico("juan.soto@gmail.com");
        medicoDatos.setIdEspecialidad(1L);
        medicoDatos.setAnniosExperiencia(10);

        when(medicoService.actualizar(eq(999L), any(MedicoModel.class)))
                .thenThrow(new RuntimeException("Médico no encontrado"));

        String jsonRequestBody = objectMapper.writeValueAsString(medicoDatos);

        mockMvc.perform(put("/api/v1/medicos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("PUT /api/v1/medicos/{id} -> Retorna 400 cuando el body es inválido")
    public void actualizar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(put("/api/v1/medicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/v1/medicos/{id} ---

    @Test
    @DisplayName("DELETE /api/v1/medicos/{id} -> Retorna 200 al eliminar")
    public void eliminar_CuandoIdExiste_DeberiaRetornarOk() throws Exception {
        doNothing().when(medicoService).delete(1L);

        mockMvc.perform(delete("/api/v1/medicos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/v1/medicos/{id} -> Retorna 404 cuando no existe")
    public void eliminar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        doThrow(new RuntimeException("Medico no encontrado"))
                .when(medicoService).delete(999L);

        mockMvc.perform(delete("/api/v1/medicos/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/medicos/detalle/{id} ---

    @Test
    @DisplayName("GET /api/v1/medicos/detalle/{id} -> Retorna 200 con médico y especialidad")
    public void obtenerDetalle_CuandoIdExiste_DeberiaRetornarMedicoConEspecialidad() throws Exception {
        MedicoModel medico = new MedicoModel();
        medico.setIdMedico(1L);
        medico.setRutMedico("18.456.789-5");
        medico.setNombreMedico("Juan");
        medico.setApellidoMedico("Soto");
        medico.setTelefonoMedico("912345678");
        medico.setCorreoMedico("juan.soto@gmail.com");
        medico.setIdEspecialidad(1L);
        medico.setAnniosExperiencia(10);

        EspecialidadDTO especialidad = new EspecialidadDTO();
        especialidad.setIdEspecialidad(1L);
        especialidad.setNombreEspecialidad("Cardiología");

        MedicoDetalleDTO detalle = new MedicoDetalleDTO(medico, especialidad);

        when(medicoService.obtenerMedicoConEspecialidad(1L))
                .thenReturn(Mono.just(detalle));

        MvcResult result = mockMvc.perform(get("/api/v1/medicos/detalle/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.medico.idMedico").value(1))
                .andExpect(jsonPath("$.medico.nombreMedico").value("Juan"))
                .andExpect(jsonPath("$.medico.apellidoMedico").value("Soto"))
                .andExpect(jsonPath("$.especialidad.idEspecialidad").value(1))
                .andExpect(jsonPath("$.especialidad.nombreEspecialidad").value("Cardiología"));
    }

    @Test
    @DisplayName("GET /api/v1/medicos/detalle/{id} -> Retorna 404 cuando no existe detalle")
    public void obtenerDetalle_CuandoIdNoExiste_DeberiaRetornarNotFound() throws Exception {
        when(medicoService.obtenerMedicoConEspecialidad(999L))
                .thenReturn(Mono.empty());

        MvcResult result = mockMvc.perform(get("/api/v1/medicos/detalle/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}

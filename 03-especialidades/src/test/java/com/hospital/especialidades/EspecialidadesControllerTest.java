package com.hospital.especialidades;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.especialidades.Controller.EspecialidadesController;
import com.hospital.especialidades.Model.EspecialidadesModel;
import com.hospital.especialidades.Service.EspecialidadesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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


@WebMvcTest(EspecialidadesController.class)
public class EspecialidadesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EspecialidadesService especialidadesService;


    // --- TESTS PARA GET /api/v1/especialidades ---

    @Test
    @DisplayName("GET /api/v1/especialidades -> Retorna 200 y lista de especialidades")
    public void listar_CuandoExistenEspecialidades_DeberiaRetornarLista() throws Exception {
        var especialidades1 = new EspecialidadesModel();
        especialidades1.setIdEspecialidad(1L);
        especialidades1.setNombreEspecialidad("Cardiologia");
        especialidades1.setDescripcionEspecialidad("Diagnostico y enfermedades del corazon");
        especialidades1.setAreaMedica("Medicina Interna");


        var especialidades2 = new EspecialidadesModel();
        especialidades2.setIdEspecialidad(2L);
        especialidades2.setNombreEspecialidad("Traumatologia");
        especialidades2.setDescripcionEspecialidad("Tratamiento de lesiones oseas y musculares");
        especialidades2.setAreaMedica("Cirugia");

        List<EspecialidadesModel> lista = Arrays.asList(especialidades1, especialidades2);
        when(especialidadesService.listarTodo()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/especialidades")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].idEspecialidad").value(1L))
                .andExpect(jsonPath("$[0].nombreEspecialidad").value("Cardiologia"))
                .andExpect(jsonPath("$[0].descripcionEspecialidad").value("Diagnostico y enfermedades del corazon"))
                .andExpect(jsonPath("$[0].areaMedica").value("Medicina Interna"))
                .andExpect(jsonPath("$[1].idEspecialidad").value(2L))
                .andExpect(jsonPath("$[1].nombreEspecialidad").value("Traumatologia"))
                .andExpect(jsonPath("$[1].descripcionEspecialidad").value("Tratamiento de lesiones oseas y musculares"))
                .andExpect(jsonPath("$[1].areaMedica").value("Cirugia"));

    }


    @Test
    @DisplayName("GET /api/v1/especialidades -> Retorna 404 cuando no hay especialidades")
    public void listar_CuandoNoHayEspecialidades_DeberiaRetornaNotFound() throws Exception {
        when(especialidadesService.listarTodo()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/especialidades")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA POST /api/v1/especialidades ---

    @Test
    @DisplayName("POST /api/v1/especialidades -> Retorna 200 al crear exitosamente")
    public void guardar_DatosValidos_DeberiaRetornarOk() throws Exception {
        var especialidades = new EspecialidadesModel();
        especialidades.setIdEspecialidad(1L);
        especialidades.setNombreEspecialidad("Cardiologia");
        especialidades.setDescripcionEspecialidad("Diagnostico y enfermedades");
        especialidades.setAreaMedica("Medicina Interna");

        when(especialidadesService.guardar(any(EspecialidadesModel.class))).thenReturn(especialidades);

        String jsonRequestBody = objectMapper.writeValueAsString(especialidades);

        mockMvc.perform(post("/api/v1/especialidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idEspecialidad").value(1L))
                .andExpect(jsonPath("$.nombreEspecialidad").value("Cardiologia"))
                .andExpect(jsonPath("$.descripcionEspecialidad").value("Diagnostico y enfermedades"))
                .andExpect(jsonPath("$.areaMedica").value("Medicina Interna"));

    }

    @Test
    @DisplayName("POST /api/v1/especialidades -> Retorna 400 cuando el body es invalido")
    public void guardar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws Exception {

        String jsonRequestBody = "{}";

        mockMvc.perform(post("/api/v1/especialidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA GET /api/v1/especialidades/{id} ---


    @Test
    @DisplayName("GET /api/v1/especialidades/{id} -> Retorna 200 cuando existe")
    public void buscar_CuandoIdExiste_DeberiaRetornarEspecialidad()  throws Exception {
        var especialidades = new EspecialidadesModel();
        especialidades.setIdEspecialidad(1L);
        especialidades.setNombreEspecialidad("Cardiologia");
        especialidades.setDescripcionEspecialidad("Diagnostico y enfermedades");
        especialidades.setAreaMedica("Medicina Interna");

        when(especialidadesService.buscarId(1L)).thenReturn(especialidades);

        mockMvc.perform(get("/api/v1/especialidades/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idEspecialidad").value(1L))
                .andExpect(jsonPath("$.nombreEspecialidad").value("Cardiologia"))
                .andExpect(jsonPath("$.descripcionEspecialidad").value("Diagnostico y enfermedades"))
                .andExpect(jsonPath("$.areaMedica").value("Medicina Interna"));
    }

    @Test
    @DisplayName("GET /api/v1/especialidades/{id} -> Retorna 404 cuando no existe")
    public void buscar_CuandoIdNoExiste_DeberiaRetornarNotFound()  throws Exception {
        when(especialidadesService.buscarId(99L)).
                thenThrow(new RuntimeException("Especialidad no encontrada"));

        mockMvc.perform(get("/api/v1/especialidades/99").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // --- TESTS PARA PUT /api/v1/especialidades/{id} ---

    @Test
    @DisplayName("PUT  /api/v1/especialidades/{id} -> Retorna 200 y actualiza especialidad")
    public void actualizar_CuandoIdExiste_DeberiaRetornarEspecialidadActualizado()  throws Exception {
        var especialidadesAct = new EspecialidadesModel();
        especialidadesAct.setIdEspecialidad(1L);
        especialidadesAct.setNombreEspecialidad("Cardiologia");
        especialidadesAct.setDescripcionEspecialidad("Diagnostico y enfermedades para el tratamiento del corazon(actualizado)");
        especialidadesAct.setAreaMedica("Medicina Interna");

        when(especialidadesService.actualizar(eq(1L), any(EspecialidadesModel.class)))
                .thenReturn(especialidadesAct);

        String jsonRequestBody = objectMapper.writeValueAsString(especialidadesAct);

        mockMvc.perform(put("/api/v1/especialidades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idEspecialidad").value(1L))
                .andExpect(jsonPath("$.nombreEspecialidad").value("Cardiologia"))
                .andExpect(jsonPath("$.descripcionEspecialidad").value("Diagnostico y enfermedades para el tratamiento del corazon(actualizado)"))
                .andExpect(jsonPath("$.areaMedica").value("Medicina Interna"));

    }

    @Test
    @DisplayName("PUT /api/v1/especialidades/{id} -> Retorna 404 cuando el ID no existe")
    public void actualizar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws  Exception {
        var especialidadesDatos= new EspecialidadesModel();
        especialidadesDatos.setIdEspecialidad(1L);
        especialidadesDatos.setNombreEspecialidad("Cardiologia");
        especialidadesDatos.setDescripcionEspecialidad("Diagnostico y enfermedades");
        especialidadesDatos.setAreaMedica("Medicina Interna");

        when(especialidadesService.actualizar(eq(1L), any(EspecialidadesModel.class)))
                    .thenThrow(new RuntimeException("Especialidad no encontrada"));

        String jsonRequestBody = objectMapper.writeValueAsString(especialidadesDatos);

        mockMvc.perform(put("/api/v1/especialidades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("PUT /api/v1/especialidades/{id} -> Retorna 400 cuando el body es invalido")
    public void actualizar_CuandoBodyInvalido_DeberiaRetornarBadRequest() throws  Exception {
        String jsonRequestBody = "{}";

        mockMvc.perform(put("/api/v1/especialidades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // --- TESTS PARA DELETE /api/v1/especialidades/{id} ---

    @Test
    @DisplayName("DELETE /api/v1/especialidades/{id} -> Retorna 200 al eliminar")
    public void eliminar_CuandoIdExiste_DeberiaRetornarOk() throws  Exception {

        doNothing().when(especialidadesService).delete(1L);

        mockMvc.perform(delete("/api/v1/especialidades/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("DELETE /api/v1/especialidades/{id} -> Retorna 404 cuando no existe")
    public void eliminar_CuandoIdNoExiste_DeberiaRetornarNotFound() throws  Exception {
        doThrow(new RuntimeException("Especialidad no encontrada"))
                .when(especialidadesService).delete(999L);

        mockMvc.perform(delete("/api/v1/especialidades/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}

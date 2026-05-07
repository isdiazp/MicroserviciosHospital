INSERT INTO historiales_clinicos (paciente_id, medico_id, reserva_id, fecha_atencion, motivo_consulta, diagnostico, tratamiento, observaciones, alergias, antecedentes_medicos)
VALUES
    (1, 10, 100, CURDATE(), 'Control anual', 'Paciente sano', 'Sin tratamiento activo', 'Se recomienda ejercicio', 'Ninguna', 'Sin cirugías previas'),
    (2, 12, 105, CURDATE(), 'Dolor abdominal', 'Gastritis aguda', 'Dieta blanda y antiácidos', 'Volver en 7 días', 'Penicilina', 'Diabetes tipo 2'),
    (5, 10, 110, CURDATE(), 'Fiebre persistente', 'Resfriado común', 'Paracetamol', 'Reposo absoluto', 'Ninguna', 'Asma bronquial');
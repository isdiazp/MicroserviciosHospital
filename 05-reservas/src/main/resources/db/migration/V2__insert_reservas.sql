INSERT INTO reservas(id_paciente, id_medico,id_agenda, fecha_atencion, hora_atencion
,motivo_consulta, estado, observacion)
VALUES
    (1, 1, 1, '2023-11-01', '08:00:00', 'Control general', 'Confirmada', 'Paciente llega en ayunas'),
    (2, 2, 3, '2023-11-01', '10:00:00', 'Dolor de espalda', 'Confirmada', 'Requiere camilla'),
    (3, 5, 4, '2023-11-02', '15:00:00', 'Revisión de exámenes', 'Pendiente', 'Traer resultados de laboratorio'),
    (4, 3, 1, '2023-11-05', '09:00:00', 'Gripe fuerte', 'Cancelada', 'Paciente reagendó por teléfono'),
    (5, 4, 2, '2023-11-01', '09:00:00', 'Consulta pediátrica', 'Confirmada', 'Acompañado por tutor legal');
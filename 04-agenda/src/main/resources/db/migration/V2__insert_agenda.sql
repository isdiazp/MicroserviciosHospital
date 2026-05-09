INSERT INTO agenda(id_medico, fecha, fecha_inicio, hora_fin,
duracion_minutos, cupos_disponibles, estado, activo)
VALUES
    (1, '2023-11-01', '08:00:00', '08:20:00', 20, 1, 'Disponible', TRUE),
    (2, '2023-11-01', '09:00:00', '09:30:00', 30, 0, 'Ocupado', FALSE),
    (1, '2023-11-01', '10:00:00', '10:45:00', 45, 1, 'Disponible', TRUE),
    (3, '2023-11-02', '15:00:00', '15:30:00', 30, 2, 'Disponible', TRUE),
    (2, '2023-11-02', '16:00:00', '16:15:00', 15, 1, 'Cancelado', FALSE);



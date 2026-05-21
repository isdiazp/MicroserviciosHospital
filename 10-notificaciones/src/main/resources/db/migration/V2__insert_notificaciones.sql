INSERT INTO notificaciones (paciente_id, mensaje, tipo, estado, fecha_envio)
VALUES
    (1, 'Su reserva para el día 20/05 ha sido confirmada.', 'EMAIL', 'ENVIADA', NOW()),
    (2, 'Recordatorio: Mañana tiene una cita médica a las 10:00 AM.', 'SMS', 'PENDIENTE', NULL),
    (3, 'Su receta médica ya está disponible en el portal.', 'WHATSAPP', 'ENVIADA', NOW());

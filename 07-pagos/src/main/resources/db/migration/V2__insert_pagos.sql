INSERT INTO pagos (paciente_id, monto, fecha_pago, medio_pago, estado_pago, activo)
VALUES
    (1, 25000.0, NOW(), 'Tarjeta de Débito', 'Completado', TRUE),
    (2, 15500.5, NOW(), 'Efectivo', 'Completado', TRUE),
    (3, 40000.0, NOW(), 'Transferencia', 'Pendiente', TRUE);

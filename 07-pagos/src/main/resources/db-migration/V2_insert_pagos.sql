INSERT INTO pagos (rut_paciente, monto, fecha_pago, medio_pago, estado_pago, activo)
VALUES
    ('12345678-9', 25000.0, NOW(), 'Tarjeta de Débito', 'Completado', TRUE),
    ('11222333-4', 15500.5, NOW(), 'Efectivo', 'Completado', TRUE),
    ('98765432-1', 40000.0, NOW(), 'Transferencia', 'Pendiente', TRUE);
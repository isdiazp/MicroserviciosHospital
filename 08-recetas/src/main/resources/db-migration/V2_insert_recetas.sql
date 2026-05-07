INSERT INTO recetas (paciente_id, medico_id, reserva_id, fecha_emision, diagnostico, medicamentos, indicaciones, vigencia_dias)
VALUES
    (1, 10, 100, CURDATE(), 'Gripe común', 'Paracetamol 500mg, cada 8 horas', 'Reposo por 3 días y mucha hidratación', 5),
    (2, 15, 101, CURDATE(), 'Infección urinaria', 'Antibiótico 800mg, cada 12 horas', 'Tomar después de las comidas', 7),
    (3, 10, 102, CURDATE(), 'Dolor muscular', 'Ibuprofeno 400mg, cada 12 horas', 'Aplicar compresas frías en la zona', 3);
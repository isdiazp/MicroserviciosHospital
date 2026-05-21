INSERT INTO recetas
(paciente_id, medico_id, reserva_id, fecha_emision, diagnostico, medicamentos, indicaciones, vigencia_dias)
VALUES
    (1, 1, 1, CURDATE(), 'Gripe común','Paracetamol 500mg, cada 8 horas','Reposo por 3 días y mucha hidratación', 5),
    (2, 2, 2, CURDATE(), 'Infección urinaria','Antibiótico 800mg, cada 12 horas','Tomar después de las comidas', 7),
    (3, 3, 3, CURDATE(), 'Dolor muscular','Ibuprofeno 400mg, cada 12 horas','Aplicar compresas frías en la zona', 3),
    (4, 4, 4, CURDATE(), 'Hipertensión leve','Losartán 50mg, una vez al día','Control semanal de presión arterial', 30),
    (5, 5, 5, CURDATE(), 'Alergia estacional','Loratadina 10mg, una vez al día','Evitar exposición al polvo y polen', 10);
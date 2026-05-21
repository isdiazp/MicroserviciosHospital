INSERT INTO historiales_clinicos
(paciente_id, medico_id, reserva_id, fecha_atencion, motivo_consulta,
 diagnostico, tratamiento, observaciones, alergias, antecedentes_medicos)
VALUES

(1, 1, 1, CURDATE(),'Fiebre y congestión nasal','Gripe común','Paracetamol 500mg cada 8 horas por 5 días','Paciente estable, signos vitales normales','Ninguna conocida','Sin antecedentes relevantes'),
(2, 2, 2, CURDATE(),'Dolor abdominal y ardor al orinar','Infección urinaria','Antibiótico 800mg cada 12 horas por 7 días','Buena respuesta inicial al tratamiento','Alergia leve a la penicilina','Infecciones urinarias recurrentes'),
(3, 3, 3, CURDATE(),'Dolor muscular en extremidades','Contractura muscular','Ibuprofeno 400mg cada 12 horas por 3 días','Se recomienda fisioterapia si persiste','Ninguna conocida','Historial de sobrecarga física laboral'),
(4, 4, 4, CURDATE(),'Presión arterial elevada','Hipertensión leve','Losartán 50mg diario','Control en 30 días','Ninguna conocida','Antecedentes familiares de hipertensión'),
(5, 5, 5, CURDATE(),'Estornudos frecuentes y picazón nasal','Alergia estacional','Loratadina 10mg diaria por 10 días','Evitar exposición a alérgenos','Alergia al polvo','Rinitis alérgica desde la infancia');
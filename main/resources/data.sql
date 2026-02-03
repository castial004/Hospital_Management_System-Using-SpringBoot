INSERT INTO patient (name, email, birth_date, blood_group) VALUES
('Alice Johnson', 'alice.johnson@gmail.com', '1991-03-14', 'A_POSITIVE'),
('Brian Miller', 'brian.miller@gmail.com', '1985-06-21', 'O_NEGATIVE'),
('Catherine Lee', 'catherine.lee@gmail.com', '1993-12-02', 'B_POSITIVE'),
('David Clark', 'david.clark@gmail.com', '1979-08-09', 'AB_NEGATIVE');
--('Emma Thompson', 'emma.thompson@gmail.com', '2001-01-17', 'O_POSITIVE'),
--('Frank Harris', 'frank.harris@gmail.com', '1987-04-25', 'A_NEGATIVE'),
--('Grace Walker', 'grace.walker@gmail.com', '1996-10-11', 'B_NEGATIVE'),
--('Henry Young', 'henry.young@gmail.com', '1982-02-03', 'AB_POSITIVE'),
--('Isabella Hall', 'isabella.hall@gmail.com', '1999-09-29', 'O_POSITIVE'),
--('Jack Allen', 'jack.allen@gmail.com', '1990-07-08', 'A_POSITIVE'),

--('Karen Wright', 'karen.wright@gmail.com', '1984-11-16', 'B_POSITIVE'),
--('Liam Scott', 'liam.scott@gmail.com', '2002-05-05', 'O_NEGATIVE'),
--('Mia Green', 'mia.green@gmail.com', '1997-06-19', 'AB_POSITIVE'),
--('Noah Adams', 'noah.adams@gmail.com', '1989-01-27', 'A_NEGATIVE'),
--('Olivia Baker', 'olivia.baker@gmail.com', '1994-12-13', 'B_NEGATIVE'),
--('Paul Nelson', 'paul.nelson@gmail.com', '1975-03-31', 'O_POSITIVE'),
--('Quinn Carter', 'quinn.carter@gmail.com', '2000-08-22', 'A_POSITIVE'),
--('Rachel Perez', 'rachel.perez@gmail.com', '1992-04-07', 'AB_NEGATIVE'),
--('Samuel Rivera', 'samuel.rivera@gmail.com', '1986-09-14', 'B_POSITIVE'),
--('Tina Brooks', 'tina.brooks@gmail.com', '1998-02-26', 'O_NEGATIVE');

INSERT INTO doctor (name, email, specialization) VALUES
('Dr. John Smith', 'john.smith@example.com', 'Cardiology'),
('Dr. Emily Johnson', 'emily.johnson@example.com', 'Dermatology'),
('Dr. Michael Brown', 'michael.brown@example.com', 'Neurology'),
('Dr. Sarah Davis', 'sarah.davis@example.com', 'Pediatrics'),
('Dr. Robert Wilson', 'robert.wilson@example.com', 'Orthopedics');

INSERT INTO appointment (reason, appointment_time, patient_id, doctor_id) VALUES
('Routine check-up', '2026-02-05 09:00:00', 1, 1),
('Skin rash consultation', '2026-02-05 10:30:00', 2, 2),
('Migraine evaluation', '2026-02-06 11:00:00', 3, 3);
--('Pediatric vaccine', '2026-02-06 14:00:00', 4, 4),
--('Knee pain assessment', '2026-02-07 09:30:00', 5, 5),
--('Heart palpitations', '2026-02-07 10:00:00', 6, 1),
--('Acne treatment', '2026-02-08 09:15:00', 7, 2),
--('Memory loss check-up', '2026-02-08 13:45:00', 8, 3),
--('Flu symptoms', '2026-02-09 10:30:00', 9, 4),
--('Back pain consultation', '2026-02-09 11:15:00', 10, 5),
--
--('Blood pressure follow-up', '2026-02-10 09:00:00', 11, 1),
--('Skin allergy test', '2026-02-10 10:30:00', 12, 2),
--('Neurological exam', '2026-02-11 14:00:00', 13, 3),
--('Child growth assessment', '2026-02-11 15:00:00', 14, 4),
--('Fracture review', '2026-02-12 09:45:00', 15, 5),
--('Cardiac stress test', '2026-02-12 11:00:00', 16, 1),
--('Dermatitis consultation', '2026-02-13 10:00:00', 17, 2),
--('Seizure follow-up', '2026-02-13 11:30:00', 18, 3),
--('Cold and fever', '2026-02-14 09:00:00', 19, 4),
--('Sports injury', '2026-02-14 10:30:00', 20, 5);

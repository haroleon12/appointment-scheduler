CREATE TABLE IF NOT EXISTS appointments (
                                            id BIGSERIAL PRIMARY KEY,
                                            customer_name VARCHAR(255) NOT NULL,
    service_type VARCHAR(255) NOT NULL,
    appointment_datetime TIMESTAMP NOT NULL UNIQUE,
    status VARCHAR(255) NOT NULL
    );

INSERT INTO appointments (customer_name, service_type, appointment_datetime, status)
VALUES
    ('Leon Lawrence', 'Diagnostic', '2026-04-10 14:30:00', 'BOOKED'),
    ('Sarah Smith', 'Brake Replacement', '2026-04-11 10:00:00', 'BOOKED'),
    ('Michael Brown', 'Oil Change', '2026-04-12 09:15:00', 'COMPLETED');
INSERT INTO users (first_name, last_name, email, password_hash, role, created_at, updated_at)
VALUES ('Admin', 'User', 'admin@foodorder.com',
        '$2b$10$Erdxe.jCS6Lj14gMRXq0ZuOBVSPkiCU7u/yRSxPuUiyLJQPvke5ui',
        'ADMIN', now(), now());

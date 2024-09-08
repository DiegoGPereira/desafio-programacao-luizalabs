-- usuários
INSERT INTO users (id, username, password, active) VALUES
(random_uuid(), 'user1', '$2a$12$74qc9RB/33wyGlvnic3bu.5SYYTnKnFpDf.lC7ZRwaHOAAnuLui7K', true),
(random_uuid(), 'admin', '$2a$12$74qc9RB/33wyGlvnic3bu.5SYYTnKnFpDf.lC7ZRwaHOAAnuLui7K', true);

-- roles
INSERT INTO user_authorities (user_id, authority)
SELECT id, 'ROLE_USER'
FROM users
WHERE username = 'user1';

INSERT INTO user_authorities (user_id, authority)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE username = 'admin';

INSERT INTO user_authorities (user_id, authority)
SELECT id, 'ROLE_USER'
FROM users
WHERE username = 'admin';

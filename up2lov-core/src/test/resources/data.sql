CREATE TABLE IF NOT EXISTS tb_systems (
  sys_id INT NOT NULL,
  sys_code VARCHAR(3) NOT NULL,
  sys_label VARCHAR(63) NOT NULL,
  sys_comment VARCHAR(1023),
  CONSTRAINT pk_system PRIMARY KEY (sys_id)--,
  --CONSTRAINT uk_system UNIQUE (sys_code)
);

INSERT INTO tb_systems (sys_id, sys_code, sys_label, sys_comment) VALUES
    (1, 'PL', 'Personal loans', 'Personal loans'),
    (2, 'AB', 'Auto and Boat loans', 'Auto and Boat loans'),
    (3, 'HL', 'Home loans', 'Home loans'),
    (4, 'BL', 'Business loans', 'Home loans'),
    (5, 'BL', 'Business loans', 'Home loans')
;
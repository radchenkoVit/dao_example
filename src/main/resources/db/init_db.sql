DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS users_teams;
DROP TABLE IF EXISTS users_roles;

create table teams (
  id         BIGINT,
  name        VARCHAR(255) NOT NULL,
  CONSTRAINT teams_PK PRIMARY KEY (id)
);

create table roles (
  id          BIGINT,
  name        VARCHAR(255) NOT NULL,
  CONSTRAINT roles_PK PRIMARY KEY (id)
);

create table users (
  id                BIGINT,
  first_name        VARCHAR(255) NOT NULL,
  last_name         VARCHAR(255) NOT NULL,
  CONSTRAINT users_PK PRIMARY KEY (id)
);

create table images (
  user_id         BIGINT,
  description     VARCHAR(255) NOT NULL,
  CONSTRAINT images_PK PRIMARY KEY (user_id),
  CONSTRAINT images_users_FK FOREIGN KEY (user_id) REFERENCES users
);

create table users_roles (
  user_id     BIGINT,
  role_id     BIGINT,
  CONSTRAINT users_roles_PK PRIMARY KEY (user_id, role_id),
  CONSTRAINT users_roles_users_FK FOREIGN KEY (user_id) REFERENCES users,
  CONSTRAINT users_roles_roles_FK FOREIGN KEY (role_id) REFERENCES roles
);

create table users_teams (
  user_id       BIGINT,
  team_id       BIGINT,
  CONSTRAINT users_teams_PK PRIMARY KEY (user_id, team_id),
  CONSTRAINT users_teams_users_FK FOREIGN KEY (user_id) REFERENCES users,
  CONSTRAINT users_teams_teams_FK FOREIGN KEY (team_id) REFERENCES teams
);
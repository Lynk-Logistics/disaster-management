CREATE TABLE volunteers (
  id uuid primary key,
  first_name varchar not null,
  last_name varchar not null,
  gender varchar not null,
  email varchar not null,
  phone_number varchar not null
);
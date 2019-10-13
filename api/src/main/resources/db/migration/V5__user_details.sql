CREATE TABLE users (
  volunteer_id uuid primary key references volunteers(id),
  email varchar not null,
  password varchar not null
);
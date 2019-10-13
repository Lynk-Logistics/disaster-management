CREATE TABLE tickets (
  id uuid primary key,
  issue_type varchar not null,
  message varchar not null,
  status varchar not null,
  address varchar not null,
  phone_number varchar not null,
  created_timestamp date,
  dispatched_timestamp date,
  resolved_timestamp date
);
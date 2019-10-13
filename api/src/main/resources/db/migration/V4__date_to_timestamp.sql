ALTER TABLE tickets
ALTER COLUMN created_timestamp TYPE timestamp,
ALTER COLUMN dispatched_timestamp TYPE timestamp,
ALTER COLUMN resolved_timestamp TYPE timestamp;
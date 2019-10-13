CREATE TABLE assigned_tickets (
  ticket_id uuid REFERENCES tickets(id),
  volunteer_id uuid REFERENCES volunteers(id)
);
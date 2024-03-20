-- Insert sample data into the Fare table
SET FOREIGN_KEY_CHECKS=0;

INSERT INTO Fare (amount, currency)
VALUES 
    (8000.00, 'INR'), -- INR - Indian Rupees
    (8500.00, 'INR'),
    (9000.00, 'INR'),
    (12000.00, 'INR'), 
    (18500.00, 'INR'),
    (6000.00, 'INR'), 
    (7500.00, 'INR'),
    (6550.00, 'INR');

-- Insert sample data into the Flight table
INSERT INTO Flight (flightNumber, departureAirport, destinationAirport, departureTime, arrivalTime, availableSeats, fare_id, type)
VALUES 
    ('AI101', 'DEL', 'BOM', '2024-03-01 08:00:00', '2024-03-01 11:00:00', 150, 1, 'DOMESTIC'),
    ('AI102', 'BOM', 'DEL', '2024-03-02 10:00:00', '2024-03-02 12:00:00', 200, 2, 'DOMESTIC'),
    ('AI103', 'DEL', 'BLR', '2024-03-03 12:00:00', '2024-03-03 15:00:00', 180, 3, 'INTERNATIONAL'),
    ('AI104', 'BLR', 'DEL', '2024-03-04 08:00:00', '2024-03-04 11:00:00', 150, 4, 'INTERNATIONAL'),
    ('AI105', 'DEL', 'MAA', '2024-03-05 10:00:00', '2024-03-05 12:00:00', 200, 5, 'DOMESTIC'),
    ('AI106', 'MAA', 'DEL', '2024-03-06 12:00:00', '2024-03-06 15:00:00', 180, 6, 'DOMESTIC'),
    ('AI107', 'DEL', 'HYD', '2024-03-07 08:00:00', '2024-03-07 11:00:00', 150, 7, 'INTERNATIONAL'),
    ('AI108', 'HYD', 'DEL', '2024-03-08 10:00:00', '2024-03-08 12:00:00', 200, 8, 'INTERNATIONAL');
SET FOREIGN_KEY_CHECKS=1;    
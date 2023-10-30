INSERT INTO owners (name, phone, email) VALUES
('John Doe', '123-456-7890', 'john.doe@example.com'),
('Jane Smith', '987-654-3210', 'jane.smith@example.com'),
('Bob Johnson', '555-123-4567', 'bob.johnson@example.com'),
('Alice Brown', '999-888-7777', 'alice.brown@example.com'),
('Charlie Wilson', '333-222-1111', 'charlie.wilson@example.com');

INSERT INTO shelters (name, phone, email) VALUES
('Happy Paws Shelter', '111-222-3333', 'info@happypaws.com'),
('Sunshine Shelter', '444-555-6666', 'info@sunshineshelter.com'),
('Loving Hearts Shelter', '777-888-9999', 'info@lovinghearts.com'),
('Safe Haven Shelter', '123-456-7890', 'info@safehaven.com'),
('Forever Friends Shelter', '555-444-3333', 'info@foreverfriends.com');


INSERT INTO pets (type, name, breed, age, color, weight, shelter_id, is_adopted) VALUES
('CAT', 'Whiskers', 'Siamese', 3, 'White', 8.5, 1, FALSE),
('DOG', 'Buddy', 'Golden Retriever', 2, 'Golden', 30.0, 2, FALSE),
('CAT', 'Mittens', 'Persian', 4, 'Gray', 10.0, 3, FALSE),
('DOG', 'Max', 'Labrador Retriever', 1, 'Black', 25.0, 4, FALSE),
('CAT', 'Oreo', 'Domestic Shorthair', 2, 'Black and White', 7.0, 5, FALSE);

INSERT INTO adoptions (date, owner_id, pet_id, motive, status, status_justification) VALUES
(NOW(), 1, 1, 'Loves cats', 'APPROVED', NULL),
(NOW(), 2, 2, 'Family pet', 'WAITING_EVALUATION', NULL),
(NOW(), 3, 3, 'Companion for loneliness', 'WAITING_EVALUATION', NULL),
(NOW(), 4, 4, 'Active lifestyle', 'WAITING_EVALUATION', NULL),
(NOW(), 5, 5, 'Friendly and playful', 'WAITING_EVALUATION', NULL);

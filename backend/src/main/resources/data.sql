-- Dummy data for category table
DELETE FROM category;
INSERT INTO category (id, name, description) VALUES
                                                 (1, 'Neck', 'Pijn in de nek door slechte houding of langdurig schermgebruik.'),
                                                 (2, 'Shoulder', 'Schouderpijn door slechte ergonomie of langdurig zitten.'),
                                                 (3, 'Upper Back', 'Pijn in de bovenrug door slechte houding of langdurig zitten.'),
                                                 (4, 'Lower Back', 'Pijn in de onderrug door slechte houding of langdurig zitten.'),
                                                 (5, 'Wrist', 'Polspijn vaak veroorzaakt door herhaalde bewegingen of onjuiste ergonomie.'),
                                                 (6, 'Elbow', 'Elleboogpijn, vaak bekend als tenniselleboog, door herhaalde belasting.'),
                                                 (7, 'Hand', 'Handpijn of ongemak door overmatig typen of muisgebruik.'),
                                                 (8, 'Hip', 'Heuppijn door langdurig zitten zonder goede ondersteuning.');

-- Dummy data for exercise table
DELETE FROM exercise;
INSERT INTO exercise (id, name, description, image_url, video_url, category_id, time_required) VALUES
                                                                                                   (1, 'Neck Stretch', 'A simple neck stretch to relieve tension.', 'https://example.com/images/neck_stretch.jpg', 'https://example.com/videos/neck_stretch.mp4', 1, 5),
                                                                                                   (2, 'Shoulder Roll', 'Roll your shoulders to reduce stiffness.', 'https://example.com/images/shoulder_roll.jpg', 'https://example.com/videos/shoulder_roll.mp4', 2, 5),
                                                                                                   (3, 'Upper Back Stretch', 'Stretch your upper back to improve posture.', 'https://example.com/images/upper_back_stretch.jpg', 'https://example.com/videos/upper_back_stretch.mp4', 3, 7),
                                                                                                   (4, 'Lower Back Twist', 'A twisting exercise to relieve lower back pain.', 'https://example.com/images/lower_back_twist.jpg', 'https://example.com/videos/lower_back_twist.mp4', 4, 10),
                                                                                                   (5, 'Wrist Flexor Stretch', 'Stretch your wrist flexors to reduce tension.', 'https://example.com/images/wrist_flexor_stretch.jpg', 'https://example.com/videos/wrist_flexor_stretch.mp4', 5, 3),
                                                                                                   (6, 'Elbow Extension', 'An exercise to improve elbow mobility.', 'https://example.com/images/elbow_extension.jpg', 'https://example.com/videos/elbow_extension.mp4', 6, 4),
                                                                                                   (7, 'Finger Stretch', 'Stretch your fingers to reduce stiffness.', 'https://example.com/images/finger_stretch.jpg', 'https://example.com/videos/finger_stretch.mp4', 7, 2),
                                                                                                   (8, 'Hip Flexor Stretch', 'Stretch your hip flexors to improve flexibility.', 'https://example.com/images/hip_flexor_stretch.jpg', 'https://example.com/videos/hip_flexor_stretch.mp4', 8, 8),
                                                                                                   (10, 'Neck Fold', 'A simple neck Fold to relieve tension.', 'https://example.com/images/neck_stretch.jpg', 'https://example.com/videos/neck_stretch.mp4', 1, 10),
                                                                                                   (11, 'Neck Curl', 'A simple neck Curl to relieve tension.', 'https://example.com/images/neck_stretch.jpg', 'https://example.com/videos/neck_stretch.mp4', 1, 5);



-- Dummy data for preferences table
DELETE FROM preferences;
INSERT INTO preferences (id, user_id, goal_category_id, time_per_day, frequency) VALUES
                                                                                     (1, 101, 1, 10, 3),
                                                                                     (2, 102, 2, 15, 4),
                                                                                     (3, 103, 3, 20, 5),
                                                                                     (4, 104, 4, 25, 2),
                                                                                     (5, 105, 5, 30, 1);

-- Dummy data for exercise_schedule table
DELETE FROM exercise_schedule;
INSERT INTO exercise_schedule (id, user_id, exercise_id, time) VALUES
                                                                   (1, 101, 1, '2023-11-01 08:00:00'),
                                                                   (2, 102, 2, '2023-11-01 09:00:00'),
                                                                   (3, 103, 3, '2023-11-01 10:00:00'),
                                                                   (4, 104, 4, '2023-11-01 11:00:00'),
                                                                   (5, 105, 5, '2023-11-01 12:00:00');
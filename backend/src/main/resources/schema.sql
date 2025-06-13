DROP TABLE IF EXISTS exercise_schedule;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS preferences;
DROP TABLE IF EXISTS category;

CREATE TABLE IF NOT EXISTS category (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL UNIQUE,
                                        description VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS exercise (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        description VARCHAR(1000),
                                        image_url VARCHAR(255) NOT NULL,
                                        video_url VARCHAR(255) NOT NULL,
                                        category_id BIGINT NOT NULL,
                                        time_required INT NOT NULL,
                                        CONSTRAINT fk_category
                                            FOREIGN KEY (category_id)
                                                REFERENCES category (id)
                                                ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exercise_schedule (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 user_id BIGINT NOT NULL,
                                                 exercise_id BIGINT NOT NULL,
                                                 time TIME NOT NULL,
                                                 created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                                 CONSTRAINT fk_exercise
                                                     FOREIGN KEY (exercise_id)
                                                         REFERENCES exercise (id)
                                                         ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS preferences (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL,
                                           goal_category_id BIGINT NOT NULL,
                                           time_per_day INT NOT NULL,
                                           frequency INT NOT NULL,
                                           CONSTRAINT fk_goal_category
                                               FOREIGN KEY (goal_category_id)
                                                   REFERENCES category (id)
                                                   ON DELETE CASCADE
);
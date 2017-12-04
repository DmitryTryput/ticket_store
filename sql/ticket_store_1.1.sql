CREATE DATABASE ticket_store;

USE ticket_store;



CREATE TABLE countries (id INT AUTO_INCREMENT, country_name VARCHAR(40) UNIQUE NOT NULL, PRIMARY KEY(id));

  
CREATE TABLE genres (id INT AUTO_INCREMENT, genres_name VARCHAR(40) UNIQUE NOT NULL, PRIMARY KEY(id));

CREATE TABLE cinemas (id INT AUTO_INCREMENT, title VARCHAR(40) UNIQUE NOT NULL, PRIMARY KEY(id));

CREATE TABLE persons (id INT AUTO_INCREMENT, first_name VARCHAR(40) NOT NULL, last_name VARCHAR(40) NOT NULL, 
country_id INT, date_of_birth DATE, PRIMARY KEY(id),
UNIQUE(first_name, last_name),
FOREIGN KEY (country_id) REFERENCES countries(id));

CREATE TABLE films (id INT AUTO_INCREMENT, title VARCHAR(40)  UNIQUE NOT NULL, create_date INT not null, 
country_id INT not null, PRIMARY KEY(id),
FOREIGN KEY (country_id) REFERENCES countries(id));

CREATE TABLE films_genres (film_id INT, genre_id INT, PRIMARY KEY(film_id, genre_id),
						FOREIGN KEY (film_id) REFERENCES films(id), 
                        FOREIGN KEY (genre_id) REFERENCES genres(id));
                        
CREATE TABLE films_actors (film_id INT, person_id INT, PRIMARY KEY(film_id, person_id),
						FOREIGN KEY (film_id) REFERENCES films(id), 
                        FOREIGN KEY (person_id) REFERENCES persons(id));

CREATE TABLE films_directors (film_id INT, person_id INT, PRIMARY KEY(film_id, person_id),
						FOREIGN KEY (film_id) REFERENCES films(id), 
                        FOREIGN KEY (person_id) REFERENCES persons(id));
                        


CREATE TABLE cinema_halls (id INT AUTO_INCREMENT, title VARCHAR(40) UNIQUE NOT NULL,
                        hall_rows INT NOT NULL, row_seats INT NOT NULL,
                        cinema_id INT NOT NULL, PRIMARY KEY(id),
                        FOREIGN KEY (cinema_id) REFERENCES cinemas(id));
                        
CREATE TABLE users (id INT AUTO_INCREMENT, email VARCHAR(40) UNIQUE NOT NULL, first_name VARCHAR(40) NOT NULL, 
						last_name VARCHAR(40), user_password VARCHAR(40),
                        user_value DECIMAL(10,2), PRIMARY KEY(id));
                    
                   
                    
CREATE TABLE reviews (id INT AUTO_INCREMENT, text VARCHAR(255), film_id INT, user_id INT, PRIMARY KEY(id), 
                        FOREIGN KEY (film_id) REFERENCES films(id), 
                        FOREIGN KEY (user_id) REFERENCES users(id));
                        
CREATE TABLE seances (id INT AUTO_INCREMENT, film_id INT NOT NULL, cinema_hall_id INT NOT NULL, 
						seance_date DATE NOT NULL, seance_time TIME NOT NULL,
						price DECIMAL(10,2) NOT NULL, PRIMARY KEY(id),
                        FOREIGN KEY (film_id) REFERENCES films(id), 
                        FOREIGN KEY (cinema_hall_id) REFERENCES cinema_halls(id));
                        
CREATE TABLE tickets (id INT AUTO_INCREMENT, seance_id INT NOT NULL, row INT, seat INT, is_purchased TINYINT(1) DEFAULT 0,
						user_id INT, PRIMARY KEY(id),
						FOREIGN KEY (seance_id) REFERENCES seances(id), 
                        FOREIGN KEY (user_id) REFERENCES users(id));
      


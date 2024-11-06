CREATE TABLE IF NOT EXISTS patient(
    id uuid PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL ,
    first_name VARCHAR(43) NOT NULL,
    middle_name VARCHAR(110),
    gender VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    oms_number VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS diseases(
    id uuid PRIMARY KEY,
    start_date_of_disease DATE NOT NULL,
    end_date_of_disease DATE,
    prescription VARCHAR(1024) NOT NULL ,
    patients_id uuid REFERENCES patient(id)
);

CREATE TABLE IF NOT EXISTS mkb10_dictionary(
    code VARCHAR PRIMARY KEY,
    disease_name VARCHAR(1000) NOT NULL
);

ALTER TABLE diseases
ADD COLUMN mkb10_code VARCHAR(10) NOT NULL,
DROP COLUMN patients_id,
ADD COLUMN patients_id uuid REFERENCES patients(id) ON UPDATE CASCADE


ALTER TABLE diseases
ADD patients_id uuid REFERENCES patients(id)


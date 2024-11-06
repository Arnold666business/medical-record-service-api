ALTER TABLE patient RENAME TO patients;

ALTER TABLE diseases
DROP COLUMN patients_id;


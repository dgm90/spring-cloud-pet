CREATE DATABASE overtimes;

CREATE TABLE employee (
	"id" serial PRIMARY KEY,
    "first_name" varchar NOT NULL,
    "last_name" varchar NOT NULL,
    "created_at" timestamptz NOT NULL DEFAULT NOW()
);

CREATE TABLE project (
	"id" serial PRIMARY KEY,
	"name" varchar NOT NULL,
    "created_at" timestamptz NOT NULL DEFAULT NOW()
);

CREATE TABLE overtime (
	"id" serial PRIMARY KEY,
	"employee_id" int NOT NULL REFERENCES employee (id),
	"project_id" int NOT NULL REFERENCES project (id),
	"start_date" date NOT NULL,
	"hours" float NOT NULL,
    "created_at" timestamptz NOT NULL DEFAULT NOW()
);
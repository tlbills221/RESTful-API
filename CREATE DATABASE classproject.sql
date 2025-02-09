CREATE DATABASE classproject;


CREATE TABLE Patient (
    address varchar(100),
    provider_id varchar(100),
    patient_id varchar(100) NOT NULL,
    SSN varchar(100),
    FOREIGN KEY (address) REFERENCES Location(address) ON DELETE CASCADE,
    FOREIGN KEY (provider_id) REFERENCES Provider(provider_id) ON DELETE CASCADE,
    PRIMARY KEY (patient_id),
    UNIQUE (SSN)
);

CREATE TABLE Provider (
    department_id varchar(100),
    provider_id varchar(100) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id) ON DELETE CASCADE,
    PRIMARY KEY (provider_id)
);

CREATE TABLE Departments (
    taxid varchar(100),
    department_id varchar(100) NOT NULL,
    FOREIGN KEY (taxid) REFERENCES Institution(taxid) ON DELETE CASCADE,
    PRIMARY KEY (department_id)
);

CREATE TABLE Institution (
    taxid varchar(100) NOT NULL,
    PRIMARY KEY (taxid)
);

CREATE TABLE Service (
    address varchar(100),
    department_id varchar(100),
    service_id varchar(100) NOT NULL,
    taxid varchar(100),
    FOREIGN KEY (address) REFERENCES Location(address) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id) ON DELETE CASCADE,
    PRIMARY KEY (service_id),
    FOREIGN KEY (taxid) REFERENCES Institution(taxid) ON DELETE CASCADE
);

CREATE TABLE Location (
    address varchar(100) NOT NULL,
    taxid varchar(100),
    PRIMARY KEY (address),
    FOREIGN KEY (taxid) REFERENCES Institution(taxid) ON DELETE CASCADE
);

CREATE TABLE Data_Sources (
    data  varchar(100),
    patient_id varchar(100),
    service_id varchar(100),
    provider_id varchar(100),
    id varchar(100) NOT NULL,
    ts datetime NOT NULL DEFAULT(GETDATE()),
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES Service(service_id) ON DELETE CASCADE,
    FOREIGN KEY (provider_id) REFERENCES Provider(provider_id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Device (
    ID BIGINT AUTO_INCREMENT NOT NULL,
    Name VARCHAR NOT NULL,
    Quantity INT NOT NULL DEFAULT 1,
    Value DOUBLE NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Student (
    ID BIGINT AUTO_INCREMENT NOT NULL,
    FirstName VARCHAR NOT NULL,
    SecondName VARCHAR NOT NULL,
    Email VARCHAR NOT NULL,
    Class VARCHAR NOT NULL,
    DemeritPoints INT NOT NULL DEFAULT 0,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Booking (
    ID BIGINT AUTO_INCREMENT NOT NULL,
    StudentID BIGINT NOT NULL,
    DeviceID BIGINT NOT NULL,
    Quantity INT NOT NULL DEFAULT 1,
    LentOn DATE NOT NULL,
    DueOn DATE NOT NULL,
    Returned BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (ID),
    FOREIGN KEY (StudentID) REFERENCES Student(ID),
    FOREIGN KEY (DeviceID) REFERENCES Device(ID)
);

CREATE TABLE IF NOT EXISTS TICKET (
    BookingID BIGINT NOT NULL,
    DateReturned DATE NOT NULL,
    DemeritValue INT NOT NULL,
    PRIMARY KEY (BookingID),
    FOREIGN KEY (BookingID) REFERENCES Booking(ID)
);

CREATE TABLE IF NOT EXISTS StudentWish (
    ID BIGINT AUTO_INCREMENT NOT NULL,
    StudentID BIGINT,
    DeviceID BIGINT,
    PRIMARY KEY (ID),
    FOREIGN KEY (StudentID) REFERENCES Student(ID),
    FOREIGN KEY (DeviceID) REFERENCES Device(ID)
);

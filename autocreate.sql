CREATE TABLE Device (
    ID INT,
    Name VARCHAR,
    Quantity INT,
    Value DOUBLE,
    PRIMARY KEY (ID)
);

CREATE TABLE Student (
    ID INT,
    FirstName VARCHAR,
    SecondName VARCHAR,
    Email VARCHAR,
    Class VARCHAR,
    DemeritPoints INT,
    PRIMARY KEY (ID)
);

CREATE TABLE Booking (
    ID INT,
    StudentID INT,
    DeviceID INT,
    Quantity INT,
    LentOn DATE,
    DueOn DATE,
    Returned BOOLEAN,
    PRIMARY KEY (ID),
    FOREIGN KEY (StudentID) REFERENCES Student(ID),
    FOREIGN KEY (DeviceID) REFERENCES Device(ID)
);

CREATE TABLE StudentWish (
    BookingID INT,
    DateReturned DATE,
    DemeritValue INT,
    PRIMARY KEY (BookingID),
    FOREIGN KEY (BookingID) REFERENCES Booking(ID)
);

CREATE TABLE StudentWish (
    ID INT,
    StudentID INT,
    DeviceID INT,
    PRIMARY KEY (ID),
    FOREIGN KEY (StudentID) REFERENCES Student(ID),
    FOREIGN KEY (DeviceID) REFERENCES Device(ID)
);<Paste>

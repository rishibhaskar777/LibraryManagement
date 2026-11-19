CREATE TABLE IF NOT EXISTS book (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(150) NOT NULL,
    author            VARCHAR(100) NOT NULL,
    category          VARCHAR(50)  NOT NULL,
    available_copies  INT          NOT NULL
);

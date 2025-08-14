-- Initial MySQL setup for ForoHub
CREATE DATABASE IF NOT EXISTS forohub;
USE forohub;

-- Grant privileges to forohub user
GRANT ALL PRIVILEGES ON forohub.* TO 'forohub'@'%';
FLUSH PRIVILEGES;
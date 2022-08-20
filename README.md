# OAES-login
### Configuration
* OS : Ubuntu 22.04
* Java: java 1.8
* Database: MySQL

### Setup
1. Install and setup MySQL from [here](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-20-04, "MySQL setup").
2. Download MySQL Connector from [here](https://mvnrepository.com/artifact/mysql/mysql-connector-java, "MySQL Connector").
3. Add MySQL Connector jar file to the project.
4. Update dbURL, dbUname, dbPass variables accordingly in OAES.java file.

### DB Schema
#### studentInfo
|Field|Type|
|-----|----|
|email|VARCHAR(255)|
|mobile|VARCHAR(10)|
|password|VARCHAR(255)|
|aadhar|VARCHAR(12)|

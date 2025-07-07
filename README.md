# 📁 Digital Locker Management System

This repository contains the complete source code and setup instructions for the **Digital Locker Management System** — a Java-based desktop application for securely managing personal locker items with authentication and password protection.

---

## 📦 Source Code Location

👉 All Java files and SQL scripts are located inside the [`sourcecode/`](./sourcecode) folder.

Please navigate there for:

- `.java` files
- `locker_system_db_setup.sql`
- Run instructions (Windows CMD)
- MySQL configuration details

---

## 🚀 Quick Start (Windows CMD)

```bash
cd sourcecode
javac -d bin -cp .;mysql-connector-j-9.3.0.jar *.java
java -cp bin;mysql-connector-j-9.3.0.jar LockerManagementSystem
```

## 📌 Make sure to:

Install MySQL Connector/J

Set up your local MySQL database as per the SQL script

## 👨‍💻 Author : [akshat-mittal1](https://github.com/akshat-mittal1)

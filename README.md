# Elektromart

Online store for electronics

---

# Instructions to Run Elektromart Web Application

## Command Line Instructions

### 1. **Clone the Repository**

```bash
git clone https://github.com/AT-GUPTA/Elektromart.git
```

### 2. **UI Module**

- **Install npm** (Ensure you have Node.js installed; npm comes bundled with it)
  ```bash
  https://nodejs.org/
  ```

- **Navigate to UI Directory and Install Dependencies**
  ```bash
  cd Elektromart\source\ui
  npm install
  ```

- **Start the UI**
  ```bash
  npm start
  ```

Note: UI will be accessible at `localhost:3000`

### 3. **Setup DB**

- **Edit application.properties**
  ```bash
  cd ..\api\Elektromart\src\main\resources\
  notepad application.properties
  ```

Replace `elektromart.dp.path` with the absolute path of `source\db\elektromart\elektromart` (without .mv.db extension).

### 4. **API Module**

- **Install Tomcat9 version 9.0.82** (Download from the Apache Tomcat official website and extract to a suitable
  location)

- **Compile the project to get the WAR file**
  ```bash
  cd ..\..\..\..
  mvn clean package
  ```

- **Deploy to Tomcat**
  ```bash
  copy target\Elektromart_war.war \path\to\tomcat9\webapps\
  ```

Restart Tomcat after copying.

Note: API endpoints will be accessible at `localhost:8080/Elektromart_war/api-endpoints`

## IntelliJ IDEA Instructions

1. **Open IntelliJ IDEA** and choose *Open*.
2. Navigate to the cloned directory `Elektromart` and open it.
3. **For UI**: Right-click on `package.json` in the UI module and select *Show npm Scripts*. Run the `install`
   and `start` scripts.
4. **For DB**: Edit the `application.properties` file as mentioned in the command line instructions.
5. **For API**: Use the Maven tool window to run `clean` and then `package`. Deploy the resulting WAR file to the
   configured Tomcat server in IntelliJ.

---
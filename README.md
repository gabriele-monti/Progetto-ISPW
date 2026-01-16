# Progetto ISPW - FoodMood

## Compilazione ed Esecuzione

Il progetto può essere eseguito in quattro modalità:

* ```gui demo```
* ```gui full```
* ```cli demo```
* ```cli demo```

## Con Maven

### Compilazione

```bash
mvn clean compile
```

### Esecuzione

Avviare il software in modalità GUI e persistenza MYSQL

```bash
mvn clean compile exec:java -Dexec.args="gui full"
```

Avviare il software in modalità CLI e persistenza MYSQL

```bash
mvn clean compile exec:java -Dexec.args="cli full"
```

Avviare il software e scegliere manualmente interfaccia grafica e modalità di persistenza

```bash
mvn compile exec:java -Dexec.mainClass="it.foodmood.Main"
```

```bash
mvn javafx:run
```

Altri esempi

```bash
mvn clean compile exec:java -Dexec.args="gui demo"
mvn clean compile exec:java -Dexec.args="cli full"
mvn clean compile exec:java -Dexec.args="cli demo"
```

## Pulizia della compilazione

Per eliminare i file generati dalla compilazione precedente ed effettuare una compilazione pulita, esegui:

```bash
mvn clean
```

Questo comando rimuove la cartella `target` e tutti i file compilati.

---

## Avvio tramite JAR
### Generazione del JAR

```bash
mvn clean package
```

### Esecuzione

```bash
java -jar target/foodmood.jar gui full
```

# Requisiti

## Configurazione Database
   ```properties
   db.url=jdbc:mysql://localhost:3306/foodmood
   db.user=tuo_utente
   db.password=tua_password
   ```

---

# SonarQube

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=gabriele-monti_Progetto-ISPW)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gabriele-monti_Progetto-ISPW&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=gabriele-monti_Progetto-ISPW)

[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=gabriele-monti_Progetto-ISPW&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=gabriele-monti_Progetto-ISPW)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=gabriele-monti_Progetto-ISPW&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=gabriele-monti_Progetto-ISPW)


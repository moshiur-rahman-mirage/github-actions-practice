# Spring Boot CI/CD learning project

A small Java 21 and Spring Boot app for practicing Maven, GitHub Actions, and Jenkins.

## Run locally

Requirements: JDK 21 or newer and Maven 3.6.3 or newer.

```shell
mvn spring-boot:run
```

Open <http://localhost:8080/api/hello>. The endpoint returns:

```json
{"message":"Hello, world!"}
```

Run the test and create the executable JAR with:

```shell
mvn clean verify
```

The packaged app is written to `target/github-actions-learning-1.0.0-SNAPSHOT.jar`.
Run it with:

```shell
java -jar target/github-actions-learning-1.0.0-SNAPSHOT.jar
```

## GitHub Actions

`.github/workflows/ci.yml` runs on pushes and pull requests targeting `main`.
It installs Java 21, caches Maven dependencies, then runs `mvn clean verify`.

## Jenkins

`Jenkinsfile` runs the same Maven verification and archives the packaged JAR.
Create a Pipeline or Multibranch Pipeline job pointing to this repository and
select **Pipeline script from SCM** with `Jenkinsfile` as the script path.
The Jenkins agent needs Git, JDK 21+, and Maven 3.6.3+ available on `PATH`.

## Project layout

- `src/main/java` — Spring Boot application and `/api/hello` endpoint
- `src/test/java` — HTTP endpoint integration test
- `.github/workflows/ci.yml` — GitHub Actions build and test workflow
- `Jenkinsfile` — Jenkins build and artifact-archive pipeline

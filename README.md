# GitHub Actions CI/CD Learning Project

This repository is a hands-on learning project for **GitHub Actions, CI/CD, and
workflow design**. A small Spring Boot application provides something real to
build and test; the main subject is how workflow events, jobs, reusable
workflows, and composite actions fit together.

> This project currently demonstrates continuous integration (CI): it checks
> out code, sets up Java, runs Maven tests, and invokes a composite action. It
> does **not** deploy the application. Deployment is a natural next exercise.

## What you can learn here

- Run workflows on pushes, pull requests, and manual dispatch.
- Filter push runs by branch and changed file paths.
- Configure least-privilege workflow permissions.
- Pass inputs to and call a reusable workflow.
- Separate jobs and make one job depend on another with `needs`.
- Create and invoke a local composite action with an input.
- Set up a Java toolchain, cache Maven dependencies, and run a Maven build.
- Inspect workflow runs and troubleshoot failed checks before merging a PR.

## Current workflow

The entry point is [`.github/workflows/ci.yml`](.github/workflows/ci.yml),
named **Step 16 Practice**.

| Event | When it runs |
| --- | --- |
| Push | On `main` or `feature/**`, when a file under `src/**`, `pom.xml`, or `.github/**` changes |
| Pull request | For pull requests targeting `main` |
| Manual | When started with **Run workflow** in the Actions tab |

The workflow has two jobs:

1. **`reusable-ci`** calls
   [`.github/workflows/shared-ci.yml`](.github/workflows/shared-ci.yml).
   The called workflow accepts a `java-version` input (default `21`), checks
   out the repository, sets up Temurin Java with Maven dependency caching, and
   runs `mvn --batch-mode test`.
2. **`composite-action`** runs only after `reusable-ci` succeeds. It checks out
   the repository, sets up Java, and invokes
   [`.github/actions/hello/action.yml`](.github/actions/hello/action.yml),
   passing the application name. The action prints the name and Java version.

The called workflow and the composite action are different GitHub Actions
features:

- A **reusable workflow** is a workflow with a `workflow_call` trigger. It can
  define jobs and is called at the job level with `uses`.
- A **composite action** bundles steps into an action. It is called from
  `steps` and can accept inputs.

The current workflow uses `contents: read`. Note that the `paths` filter is
under the `push` trigger only; the pull-request trigger is not path-filtered.

## Try it

### Requirements

- JDK 21 or newer
- Maven 3.6.3 or newer

### Run the app and test locally

Start the Spring Boot app:

```shell
mvn spring-boot:run
```

Request `http://localhost:8080/api/hello`. The endpoint responds:

```json
{"message":"Hello, world!"}
```

Run the same test phase used by the reusable workflow:

```shell
mvn --batch-mode test
```

Build, run all verification checks, and package the executable JAR:

```shell
mvn --batch-mode clean verify
```

Run the packaged app:

```shell
java -jar target/github-actions-learning-1.0.0-SNAPSHOT.jar
```

### Practice the GitHub flow

1. Create a branch matching `feature/<topic>`.
2. Make a change to application code, tests, or workflow files.
3. Push the branch and inspect its run in the [Actions tab](../../actions).
   For a push-triggered run, at least one path in the workflow's push filter
   must match.
4. Open a pull request targeting `main` and wait for its workflow check.
5. Review the job logs if a check fails, fix the issue, and push again.
6. After merging, inspect the run triggered on `main`.

To practice manual dispatch, open **Actions → Step 16 Practice → Run
workflow**.

## What changed over the project history

The Git history shows the project evolving through workflow exercises:

1. A basic Spring Boot app and an initial GitHub Actions workflow.
2. A multi-job workflow separating tests from build/package work and uploading
   a JAR artifact.
3. Branch and path filters, pull-request triggers, and manual dispatch.
4. A reusable Java CI workflow and a local composite action.

The latest workflow-related push on the feature branch completed successfully.
An earlier pull-request run failed while the called workflow was invalid; a
later run on `main` after the merge succeeded. Use the live
[Actions run history](../../actions) for current statuses and logs.

## Repository layout

```text
.github/
  actions/hello/action.yml        # Local composite action
  workflows/
    ci.yml                        # Main workflow and triggers
    shared-ci.yml                 # Reusable Java/Maven workflow
src/
  main/java/com/learning/         # Spring Boot app and greeting endpoint
  test/java/com/learning/         # Endpoint integration test
pom.xml                           # Java version, dependencies, Maven build
```

## Next CI/CD exercises

- Add a workflow input for the app version or test selection.
- Upload the JAR from a workflow run and download it from the Actions tab.
- Add a separate packaging job that depends on tests.
- Add a deployment job that runs only after successful checks on `main`.
- Store deployment credentials as repository or environment secrets; never
  commit credentials to the repository.

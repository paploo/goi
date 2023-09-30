# Goi
**語彙**
**Vocabulary**

## Overview

This is a **personal project** to build tooling around the 日本語 I've been learning. Specifically, it:
- Makes management of vocabulary and grammar lessons easy.
- Supports managing each in a convenient place (e.g. a google sheet or json file).
- Supports exporting the data to multiple places and formats (e.g. anki flashcards and databases)
- Uses a pipeline architecture to be independent of specific import/export locations.
- Is expandable for other use cases (e.g. specialized counter flashcards, or even things that don't result in flashcards at all.

# Usage

> ⚠️ See setup section for setting up your database before running.

First, due to the multi-language history of this app, to run the kotlin code, first enter the kotlin directory:
```shell
cd $PROJECT_ROOT/kotlin/goi
```
This will make the gradle wrapper available for running further commands.

At it's root, the main application runs using subcommands. Example runs are below:
- `./gradlew pipeline vocabulary`
- `./gradlew pipeline vocabulary grammar`

For the pipeline commands, it is expected that the following two files exist in the `$PROJECT_ROOT/files` directory:
- `日本語 Vocab - Vocab.csv` - the exported CSV from google sheets.
- `日本語 Vocab - Grammar.json` - the JSON file containing grammar lessons.
Note that I commit my latest versions.

The output products are in the `files` directory or in the database.

## Technology

Technology-wise:
- I started with Ruby, but moved to Kotlin ~1 year later.
- The Ruby code is no longer supported, but still exists in the repo.

*Specifically, I've sectioned off each language/tech choice into its own directory.*

## Setup

### Postgres

The application writes into a local postgres database. You can do this in any way you want, but
I do it with Docker:
```
docker run --name goi-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```

### Building

This is a basic kotlin gralde project; you can build and run tests in one motion with:
```shell
./gradlew clean build test
```
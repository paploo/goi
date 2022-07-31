# Goi
**語彙**
**Vocabulary**


## Overview

Tooling around the 日本語 I have been learning, such as:
- Database for holding all the vocabulary I know.
- Tools for exporting to Anki flashcards.

## Technology

Technology-wise:
- I started  with Ruby/Postgres to spin things up quickly
- however I might use this as a future vehicle for learning new things, so I've
  kept the layout open to the possibility of having a mixed technoloy set.

*Specifically, I've sectioned off each tech into its own directory.*
### Setup

####

The Ruby parts of this project were set-up as follows:
1. Use rbenv to install the required ruby version.
2. `cd goi/ruby`
2. Use `bundle init` to get a default Gemfile.

To install/maintain gems, use `bundle install` and `bundle update`

Postgres can be installed in many ways, but a Docker VM is a handy way to manage installation in a contained way.
```
docker run --name goi-postgres -p {PORT}:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```
Where `{PORT}` should be the local port to connect to in the app.

## Build/Run

TODO
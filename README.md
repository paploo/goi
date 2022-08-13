# Goi
**語彙**
**Vocabulary**


## Overview

Tooling around the 日本語 I have been learning, such as:
- Database for holding all the vocabulary I know.
- Tools for exporting to Anki flashcards.

# Usage

Typically I use `transcode_bulk.rb` to read in an exported Google Sheet CSV
and export a variety of files that are each used for a different purpose:
- `日本語 Vocab - Vocab.csv`: The import file from Google sheets; new records can have empty IDs.
- `google_sheet.csv`: The same as the import file, except new row have IDs and the data has been cleaned.
- `anki.csv`: The file that can be imported into Anki; be sure to use the ID column for merging notes.
- `data.sql`: A SQL file to laod into a clean database by hand (see `db/scripts/clear_vocab.sql` to make clean).

1. Export the vocab CSV from Google Sheets.
2. Move the file to `files/日本語 Vocab - Vocab.csv`.
3. `cd goi/ruby`
4. `ruby transcode_bulk.rb`
5. The `files` directory is populated with outputs.

Note that I version control these to be able to diff them and catch errors.

TODO: Eventually sync directly to a SQL database, however for now I don't need that.

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
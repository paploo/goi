> ⚠️ **DEPRECATION NOTICE** This is the original ruby code. Ruby is great for rapid development, but has less powerful
> refactoring tools than  strongly typed languages. As time went on, it became clearer that a switch to a more strongly
> typed language would facilitate better long term development.
> 
> A little over a year with the ruby code, this has been replaced with kotlin.
> Below are the conetnts of hte original README, which was focused on running in ruby.

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

TODO: Eventually sync directly to a SQL database, however for my needs

## Technology

Technology-wise:
- I started  with Ruby/Postgres to spin things up quickly
- I ended up not needing Postgres for MVP, which drove Anki best directly off a Google Spreadsheet.
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

For playing around, I recommend using `ruby/bin/transcode`, however the
best script to run is `transcode_bulk`, which loads a google sheets export
and produces all the necessary output text files.

Right now I have NOT wired up command line arguments (because that is a waste of
time for my use case; I can always add `optparse` later if needed), so you need to:
1. Export the google sheet in the required format and place it in `goi/files/日本語 Vocab - Vocab.csv`.
2. `cd goi/ruby` to run the right ruby version from rbenv.
3. run the script with `ruby bin/transcode_bulk`.
4. Pick-up the updated files from the `goi/files` directory.

Note that since this is all just for me, I've version controlled these files so that
I can diff them to catch mistakes before importing into Anki.

## Tests

1. `cd goi/ruby` to get the right ruby version.
2. `bundle exec rspec spec`
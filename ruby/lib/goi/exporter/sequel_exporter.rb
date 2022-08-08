# TODO: Write to the DB directly using Sequel.
#
# This is the hardest one because we either have to work out a sync
# strategy, or delete whole words and re-add them in a transaction.


# class Synchronizer
#
#   # To sync we must:
#   # - Identify records to insert,
#   # - Identify records to remove,
#   # - Identify records to update.
#   #
#   # * For the vocabulary, definition, spellings and links, we assume ID idempotency based on relationship.
#   # * For the references we must go through the same sync actions.
#   def initialize(db:)
#     @db = db
#   end
#
#   def sync(record_groups)
#     # First determine what to insert, update, and remove.
#     db.transaction do
#       library = Library.new(db:).load_all
#       insert, update, delete = calculate_diff(library.vocabulary_ids, record_groups.map { |g| g[:vocabulary][:id ]})
#     end
#     raise NotImplementedError
#   end
#
#   private
#
#   attr_reader :db
#
#   def calculate_diff(old_values, new_values)
#     [
#       #insert
#       new_values - old_values,
#       #update
#       new_values.intersection(old_values),
#       #delete
#       old_values - new_values
#     ]
#   end
#
#   class Library
#
#     def initialize(db:)
#       @db = db
#     end
#
#     attr_reader :vocabulary_ids
#     attr_reader :reference_codes
#
#     def load_all
#       load_vocabulary
#       load_references
#
#       self
#     end
#
#     private
#
#     def load_vocabulary
#       @vocabulary_ids = db[Sequel[:vocabulary][:vocabulary]]
#                           .select(:id)
#                           .all
#                           .map { |r| r[:id] }
#     end
#
#     def load_references
#       @references = db[Sequel[:vocabulary][:reference]]
#                       .all
#                       .group_by { |r| r[:vocabulary_id] }
#                       .transform_values { |v| v.map { |r| r[:lesson_code] } }
#     end
#
#   end
#
#   attr_reader :db
#
# end

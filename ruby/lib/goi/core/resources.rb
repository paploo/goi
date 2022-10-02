module Goi
  module Core
    module Resources

      GOI_DIR = Pathname(__FILE__).expand_path.join('..', '..', '..', '..', '..')

      RESOURCES_DIR = GOI_DIR + 'resources'

      FILES_DIR = GOI_DIR + 'files'

      def self.at_path(*path) = path.inject(RESOURCES_DIR) { |pn, s| pn + s }

    end
  end
end

package com.disl.dbfile.constants;

public final class AppTables {

    private AppTables() {}

    public static final class FileObjectTable {
        private FileObjectTable() {}

        public static final String TABLE_NAME = "file_objects";

        public static final String FILE_NAME = "file_name";

        public static final String FILE_TYPE = "file_type";

        public static final String FILE_EXTENSION = "file_extension";

        public static final String MIME_TYPE = "mime_type";

        public static final String FILE_KEY = "file_key";

        public static final String FILE_SIZE = "file_size";

        public static final String EXTERNAL_ID = "external_id";
    }
}

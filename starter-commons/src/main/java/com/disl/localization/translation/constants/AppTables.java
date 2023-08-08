package com.disl.localization.translation.constants;

public final class AppTables {

    private AppTables() {}

    public static final class TranslationTable {
        private TranslationTable() {}

        public static final String TABLE_NAME = "translations";

        public static final String LANGUAGE_ID = "language_id";

        public static final String LANGUAGE_CODE = "language_code";

        public static final String TRANSLATED_TEXT = "translated_text";

        public static final String LOCALIZED_TEXT_ID = "localized_text_id";
    }
}

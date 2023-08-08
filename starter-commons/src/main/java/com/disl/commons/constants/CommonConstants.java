package com.disl.commons.constants;

public final class CommonConstants {

	private CommonConstants() {}

	public enum environment {
		development,
		staging,
		production
	}

	public static String DEFAULT_DATE_FORMAT = "dd-MMM-yy";

	public static final String getFakeFileKey() {
		return "1627377451_nature-1600x900.jpg";
	}

	public static final String DEFAULT_LARGE_TEXT_DATA_TYPE = "TEXT";
	//public static final String DEFAULT_LARGE_TEXT_DATA_TYPE = "varchar2(3999 char)";

	public static final String PAGE_NO = "pageNo";
	public static final String PAGE_SIZE = "pageSize";
	public static final String SORT_BY = "sortBy";
	public static final String ASC_OR_DESC = "ascOrDesc";

	public static final String DEFAULT_PAGE_NO = "0";
	public static final String DEFAULT_PAGE_SIZE = "20";
	public static final String DEFAULT_SORT_BY = "creationDate";
	public static final String DEFAULT_ASC_OR_DESC = "asc";

	public static final String KEYWORD = "keyword";
	public static final String PARAMETERS = "parameters";
	public static final String LANGUAGE_CODE = "languageCode";

	public static final String DEFAULT_LANGUAGE_NAME = "English";
	public static final String DEFAULT_LANGUAGE_CODE = "en";

	public static final String BANGLA_LANGUAGE_NAME = "Bangla";
	public static final String BANGLA_LANGUAGE_CODE = "bn";

	public static final String LOAD_ORIGINAL_TEXT = "loadOriginalText";

	public static final String LIMIT = "limit";
	public static final String DEFAULT_LIMIT = "10";
}

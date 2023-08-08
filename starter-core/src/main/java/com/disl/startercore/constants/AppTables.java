package com.disl.startercore.constants;

public final class AppTables {
	private AppTables() {}

	public static final String USER_NAME = "users";
	public static final String ROLE_NAME = "roles";
	public static final String USER_ROLE_NAME = "user_roles";
	public static final String PRIVILEGE_NAME = "privileges";
	public static final String NOTIFICATION_NAME = "notifications";
	public static final String REFRESH_TOKEN_NAME = "refresh_tokens";
	public static final String ROLE_PRIVILEGE_NAME =  "role_privileges";

	public static final class AuditModelTable {
		public static final String ID = "id";
		public static final String CREATED_BY = "created_by";
		public static final String CREATION_DATE = "creation_date";
		public static final String LAST_MODIFIED_BY = "last_modified_by";
		public static final String LAST_MODIFIED_DATE = "last_modified_date";
	}

	public static final class UserTable {
		public static final String EMAIL = "email";
		public static final String NAME = "name";
		public static final String BANNED = "banned";
		public static final String ROLE_ID = "role_id";
		public static final String USER_ID = "user_id";
		public static final String VERIFIED = "verified";
		public static final String password = "password";
		public static final String PASSWORD_RESET_TOKEN = "password_reset_token";
	}

	public static final class RefreshTokenTable {
		public static final String USER_ID = "user_id";
		public static final String TOKEN = "token";
		public static final String EXPIRY_TIME = "image_url";
	}

	public static final class RoleTable {
		public static final String ROLE_ID = "role_id";
		public static final String PRIVILEGE_ID = "privilege_id";
		public static final String ROLE_TYPE = "role_type";
		public static final String DESCRIPTION = "description";
		public static final String IMAGE_URL = "image_url";
		public static final String ROLE_NAME = "role_name";
	}

	public static final class PrivilegeTable {
		public static final String NAME = "name";
		public static final String DESC_NAME = "desc_name";
	}

	public static final class PageTable {
		private PageTable() {}

		public static final String TAG = "tag";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
	}

	public static final class SectionTable {
		private SectionTable() {}

		public static final String TITLE = "title";
		public static final String ACTIVE = "active";
		public static final String PAGE_ID = "page_id";
		public static final String CONTENT = "content";
		public static final String DESCRIPTION = "description";
		public static final String IMAGE_FILE_ID = "image_file_id";
		public static final String EXTERNAL_LINK = "external_link";
	}

	public static final class DbFileTable {
		private DbFileTable() {}

		public static final String FILE_KEY = "file_key";
		public static final String FILE_NAME = "file_name";
		public static final String MIME_TYPE = "mime_type";
		public static final String FILE_TYPE = "file_type";
		public static final String UPLOAD_TYPE = "upload_type";
		public static final String FILE_EXTENSION = "file_extension";
	}

	public static final class NotificationTable {
		private NotificationTable() {}

		public static final String TYPE = "type";
		public static final String TITLE = "title";
		public static final String IS_READ = "is_read";
		public static final String TYPE_ID = "type_id";
		public static final String MESSAGE = "message";
		public static final String SENDER_ID = "sender_id";
		public static final String RECIPIENT_ID = "recipient_id";
	}
}
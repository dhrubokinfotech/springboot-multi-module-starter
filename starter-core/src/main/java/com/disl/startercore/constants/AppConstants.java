package com.disl.startercore.constants;

import java.util.HashMap;

public final class AppConstants {
	private AppConstants() {}

	public static String INITIAL_USERNAME = "super_admin@disl.com";
	public static String INITIAL_PASSWORD = "123456";
	
	public static String INITIAL_ROLE = "SUPER ADMIN";
	public static String USER_ROLE = "USER";

	public static String CONSUMER_PERMISSION = "USER";
	public static String CONSUMER_PERMISSION_DESC = "User Generalized Permission";

	public static String RESET_PASSWORD_SUBURL = "/resetpassword?passResetToken=";
	public static String VERIFICATION_SUBURL = "/verify?verificationToken=";

	public static String DB_FILE_BASE_UR = "";
	
	public static String BANNED_MESSAGE = "You are banned by admin. If you think it is a mistake then please contact with us";

	public final static String INVITATION_SUBJECT = "BoilerPlate User Verification";
	public final static String INVITATION_TEXT = "Please follow this link to verify your email for BoilerPlate. \n \t";
	public final static String FORGET_PASSWORD_SUBJECT = "BoilerPlate Password Reset Link";
	public final static String FORGET_PASSWORD_TEXT = " To reset your password in BoilerPlate please click on the following url \n \t" ;
	
	public static HashMap<String, String> PERMISSIONS = new HashMap<>() {
		{
			put("GENERAL", "GENERAL CONSUMER");

			put("USER_CREATE", "USER CREATE");
			put("USER_READ", "USER READ");
			put("USER_UPDATE", "USER UPDATE");
			put("USER_DELETE", "USER DELETE");

			put("ROLE_CREATE", "ROLE CREATE");
			put("ROLE_READ", "ROLE READ");
			put("ROLE_UPDATE", "ROLE UPDATE");
			put("ROLE_DELETE", "ROLE DELETE");

			put("NOTIFICATION_SEND", "NOTIFICATION SEND");
			put("USER_UPDATE_FROM_ADMIN", "USER UPDATE FROM ADMIN");

			put("LANGUAGE_CREATE", "LANGUAGE CREATE");
			put("LANGUAGE_READ", "LANGUAGE_READ");
		}
	};
}


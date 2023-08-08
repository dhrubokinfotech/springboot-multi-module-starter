package com.disl.startercore.security;

import com.disl.startercore.enums.SocialAuthType;
import com.disl.startercore.models.FacebookOAuth2UserInfo;
import com.disl.startercore.models.GoogleOAuth2UserInfo;
import com.disl.startercore.models.OAuth2UserInfo;
import com.disl.startercore.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(SocialAuthType authProvider, Map<String, Object> attributes) {
        if(authProvider == SocialAuthType.GOOGLE) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (authProvider == SocialAuthType.FACEBOOK) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + authProvider + " is not supported yet.");
        }
    }
}

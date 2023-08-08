package com.disl.commons.constants;

import com.disl.commons.enums.AscOrDescType;
import com.disl.commons.payloads.PaginationArgs;
import com.disl.localization.localized_text.entities.LocalizedText;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.beans.FeatureDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class CommonUtils {

	public static boolean isTrue (Boolean value) {
		return value != null && value;
	}

	public static boolean isNotNullOrEmpty (String str) {
		return str != null && !str.trim().isEmpty();
	}

	public static boolean isNullOrEmpty (String str) {
		return str == null || str.trim().isEmpty();
	}

	public static boolean isNullOrEmptyList (List<?> values) {
		return values == null || values.isEmpty();
	}

	public static boolean isNotNullOrEmptyList (List<?> values) {
		return values != null && !values.isEmpty();
	}

	public static boolean isNotNullAndGreaterZero (Long id) {
		return id != null && id > 0;
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		//String regex = "^[0-9]*$";
		String regex = "^[0][1-9]\\d{9}$|^[1-9]\\d{9}$";
		return phoneNumber.matches(regex);
	}

	public static boolean isValidMail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static String getInvalidPasswordMessage(String password) {
		if(password == null || password.isEmpty()) {
			return "Password can't be empty";
		}

		if(password.contains(" ")) {
			return "Password shouldn't contains white spaces";
		}

		if(password.length() <= 7) {
			return "Password must have 8 or more characters long";
		}

		Pattern specialAndDigitPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_]).{3,}$");
		boolean isMatched = specialAndDigitPattern.matcher(password).matches();

		if(!isMatched) {
			return "Password must have minimum a digit, a special character and an alphabet";
		}

		return null;
	}

	public static boolean isThisDateValid(String dateToValidate){
		if(dateToValidate == null){
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DEFAULT_DATE_FORMAT);
		sdf.setLenient(false);

		try {
			sdf.parse(dateToValidate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static int getRandomNumberInts(int min, int max){
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}

	public static String generateRandomString (int length) {
		RandomStringGenerator randomStringGenerator =
				new RandomStringGenerator.Builder()
						.withinRange('0', 'z')
						.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
						.build();
		return randomStringGenerator.generate(length);
	}

	public static String generateSlugFromString(String value){
		Pattern nonLatin = Pattern.compile("[^\\w-]");
		Pattern specialCharacters = Pattern.compile("[^a-zA-Z0-9 ]");
		Pattern whiteSpace = Pattern.compile("[\\s]");
		Pattern others = Pattern.compile("(^-|-$)");
		Pattern multiSpace = Pattern.compile("\\s{2,}");

		value = value.trim();

		Matcher matcher = multiSpace.matcher(value);

		value = specialCharacters.matcher(value).replaceAll("");
		value = multiSpace.matcher(value).replaceAll(" ");
		value = whiteSpace.matcher(value).replaceAll("-");
		value = Normalizer.normalize(value, Normalizer.Form.NFD);
		value = nonLatin.matcher(value).replaceAll("");
		value = others.matcher(value).replaceAll("");

		return value.toLowerCase(Locale.ENGLISH);
	}

	public static String makeSlug(String slug){
		char ch = '-';
		slug = slug.replace(' ',ch);
		return slug;
	}

	public static File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();

		return convertedFile;
	}

	public static Pair<String, String> getFileExtensionAndMimeType(MultipartFile file) throws IOException, MimeTypeException {
		Tika tika = new Tika();
		String detectedType = tika.detect(file.getBytes());
		TikaConfig config = TikaConfig.getDefaultConfig();
		MimeType mimeType = config.getMimeRepository().forName(detectedType);
		return new ImmutablePair<>(mimeType.getExtension(), detectedType);
	}
	
	public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
	            .toArray(String[]::new);
	}

	public static Pageable getPageable(PaginationArgs paginationArgs) {
		Pageable pageable;
		String sortBy = paginationArgs.getSortBy();
		int pageNo = paginationArgs.getPageNo();
		int pageSize = paginationArgs.getPageSize();

		if(sortBy != null && sortBy.length() > 0) {
			if (paginationArgs.getAscOrDesc().equals(AscOrDescType.asc)) {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
			} else {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			}
		} else {
			pageable = PageRequest.of(pageNo, pageSize);
		}

		return pageable;
	}

	public static Pageable getPageable(int pageNo, int pageSize, String sortBy, AscOrDescType ascOrDesc) {
		Pageable pageable;

		if(sortBy != null && sortBy.trim().length() > 0) {
			if (ascOrDesc.equals(AscOrDescType.asc)) {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
			} else {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			}
		} else {
			pageable = PageRequest.of(pageNo, pageSize);
		}

		return pageable;
	}

	public static Pageable getLocalizedPageable(PaginationArgs paginationArgs, Class<?> className) {
		Pageable pageable;
		String sortBy = paginationArgs.getSortBy();
		int pageNo = paginationArgs.getPageNo();
		int pageSize = paginationArgs.getPageSize();

		try {
			Field field = className.getDeclaredField(sortBy);
			if (field.getType() == LocalizedText.class) {
				sortBy = sortBy+".translations.translatedText";
			}
		} catch (Exception ex) {
			sortBy = sortBy;
		}

		if (sortBy != null && sortBy.length() > 0) {
			if (paginationArgs.getAscOrDesc().equals(AscOrDescType.asc)) {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
			} else {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			}
		} else {
			pageable = PageRequest.of(pageNo, pageSize);
		}

		return pageable;
	}

	public static Map<String, Object> getParameters(Map<String, Object> parameters) {
		parameters.remove(CommonConstants.PAGE_NO);
		parameters.remove(CommonConstants.PAGE_SIZE);
		parameters.remove(CommonConstants.SORT_BY);
		parameters.remove(CommonConstants.ASC_OR_DESC);
		parameters.remove(CommonConstants.ASC_OR_DESC);
		parameters.remove(CommonConstants.PARAMETERS);
		parameters.remove(CommonConstants.LANGUAGE_CODE);
		parameters.remove(CommonConstants.LOAD_ORIGINAL_TEXT);
		return parameters;
	}

	public static <D, E> D mapEntityToDto(ModelMapper modelMapper, E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

	public static <E, D> Page<D> mapEntityPageToDtoPage(ModelMapper modelMapper, Page<E> entities, Class<D> dtoClass) {
		return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
	}
}

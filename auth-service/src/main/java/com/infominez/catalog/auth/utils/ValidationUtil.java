package com.infominez.catalog.auth.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
	
	
	  
	
	public static boolean isValidMobileNo(String mobileNumber)  {
		//(0/91): number starts with (0/91)  
		//[7-9]: starting of the number may contain a digit between 0 to 9  
		//[0-9]: then contains digits 0 to 9  
		Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");	
		//the matcher() method creates a matcher that will match the given input against this pattern  
		Matcher match = ptrn.matcher(mobileNumber);  
		//returns a boolean value  
		return (match.find() && match.group().equals(mobileNumber));  
	}

	public static boolean isImageValid(MultipartFile file) {

		String originalFilename = file.getOriginalFilename();

		int idx = originalFilename.lastIndexOf(".");
		String fileNameWithoutExt = idx >= 0 ? originalFilename.substring(0, idx) : originalFilename;

		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).trim();

		long fileSize = file.getSize();

		double fileSizeKb = fileSize / 1024;
		double fileSizeMb = fileSizeKb / 1024;

		String imageExtensionFormatList = "jpg,png,jpeg,heic,webp";
		long maxImageSize = Long.parseLong("2");

		List<String> imageExtensionFormats = new ArrayList<>(Arrays.asList(imageExtensionFormatList.split(",")));


		if (imageExtensionFormats.contains(fileExtension.toLowerCase()) && fileSizeMb <= maxImageSize
				&& fileNameWithoutExt.matches("^[a-zA-Z0-9_]+$")) {
			return true;
		} else {
			return false;
		}

	}

}

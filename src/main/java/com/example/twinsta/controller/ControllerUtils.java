package com.example.twinsta.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

public class ControllerUtils {

	public static Map<String, String> getErrors(BindingResult bindingResult) {
		Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
				fieldError -> fieldError.getField() + "Error", FieldError::getDefaultMessage);
		return bindingResult.getFieldErrors().stream().collect(collector);
	}

	public static String setUploadedFile(@RequestParam("file") MultipartFile file, String uploadPath) throws IOException {
		if (nonNull(file) && isFalse(file.getOriginalFilename().isEmpty())) {
			File uploadDir = new File(uploadPath);
			if (isFalse(uploadDir.exists())) {
				uploadDir.mkdir();
			}
			String uuidFile = randomUUID().toString();
			String resultFilename = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFilename));
			return resultFilename;
		}
		return null;
	}
}

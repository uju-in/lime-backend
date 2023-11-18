package com.programmers.bucketback.global.validator;

import java.util.Arrays;

import com.programmers.bucketback.global.annotation.Enum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, java.lang.Enum> {
	@Override
	public boolean isValid(
		final java.lang.Enum value,
		final ConstraintValidatorContext context
	) {
		if (value == null)
			return false;
		Class<?> reflectionEnumClass = value.getDeclaringClass();

		return Arrays.asList(reflectionEnumClass.getEnumConstants()).contains(value);
	}
}

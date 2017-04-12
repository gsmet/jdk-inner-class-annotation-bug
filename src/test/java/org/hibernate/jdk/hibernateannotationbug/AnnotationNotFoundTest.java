package org.hibernate.jdk.hibernateannotationbug;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;

import org.junit.Assert;
import org.junit.Test;

public class AnnotationNotFoundTest {

	@Test
	public void testAnnotationFoundInExternalClass() {
		checkAnnotationIsPresent( ExternalClassValueExtractor.class );
	}

	@Test
	public void testAnnotationFoundInInnerClass() {
		checkAnnotationIsPresent( InnerClassValueExtractor.class );
	}

	private void checkAnnotationIsPresent(Class<?> clazz) {
		AnnotatedType[] annotatedInterfaces = clazz.getAnnotatedInterfaces();
		AnnotatedParameterizedType valueExtractorType = (AnnotatedParameterizedType) annotatedInterfaces[0];

		AnnotatedType[] valueExtractorTypeParameters = valueExtractorType.getAnnotatedActualTypeArguments();
		AnnotatedParameterizedType extractedEntityType = (AnnotatedParameterizedType) valueExtractorTypeParameters[0];

		AnnotatedType[] extractedEntityTypeParameters = extractedEntityType.getAnnotatedActualTypeArguments();
		Assert.assertTrue( extractedEntityTypeParameters[0].isAnnotationPresent( ExtractedValue.class ) );
	}

	private static class InnerClass<T> {
	}

	private static class ExternalClassValueExtractor implements ValueExtractor<ExternalClass<@ExtractedValue ?>> {
	}

	private static class InnerClassValueExtractor implements ValueExtractor<InnerClass<@ExtractedValue ?>> {
	}

}

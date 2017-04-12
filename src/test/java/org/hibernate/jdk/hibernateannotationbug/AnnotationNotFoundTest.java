package org.hibernate.jdk.hibernateannotationbug;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AnnotationNotFoundTest {

	private final List<InnerClass<@MyAnno String>> listOfGenericInnerType = null;
	private final List<ExternalClass<@MyAnno String>> listOfGenericExternalType = null;

	@Test
	public void testAnnotationFoundInExternalClass() throws Exception {
		checkAnnotationIsPresent( "listOfGenericExternalType" );
	}

	@Test
	public void testAnnotationFoundInInnerClass() throws Exception {
		checkAnnotationIsPresent( "listOfGenericInnerType" );
	}

	private void checkAnnotationIsPresent(String fieldName) throws Exception {
		Field field = getClass().getDeclaredField( fieldName );
		AnnotatedParameterizedType fieldType = (AnnotatedParameterizedType) field.getAnnotatedType();

		AnnotatedParameterizedType listElementType = (AnnotatedParameterizedType) fieldType.getAnnotatedActualTypeArguments()[0];
		AnnotatedType[] elementTypeTypeArguments = listElementType.getAnnotatedActualTypeArguments();

		Assert.assertTrue( elementTypeTypeArguments[0].isAnnotationPresent( MyAnno.class ) );
	}

	private static class InnerClass<T> {
	}
}

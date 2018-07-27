package com.honeyedoak.ppksecuredws.processor;

import com.honeyedoak.cryptoutils.AsymmetricCryptoService;
import com.honeyedoak.cryptoutils.SymmetricCryptoService;
import com.honeyedoak.ppksecuredws.GenericSecureJsonConverter;
import com.honeyedoak.ppksecuredws.GenericSecureJsonConverterImpl;
import com.honeyedoak.ppksecuredws.annotation.SecureJsonConverter;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

public class SecureJsonConverterAnnotatedClass {

	private TypeElement typeElement;

	private String targetPackage;

	private String charsetPropertyName;

	private String charsetDefaultValue;

	private String oneTimePasswordLengthPropertyName;

	private int oneTimePasswordLengthDefaultValue;

	private String keystoreLocationPropertyName;

	private String keystorePasswordPropertyName;

	public SecureJsonConverterAnnotatedClass(TypeElement typeElement) {
		this.typeElement = typeElement;

		SecureJsonConverter annotation = typeElement.getAnnotation(SecureJsonConverter.class);

		targetPackage = annotation.targetPackage();
		charsetPropertyName = annotation.charsetPropertyName();
		charsetDefaultValue = annotation.charsetDefaultValue();
		oneTimePasswordLengthPropertyName = annotation.oneTimePasswordLengthPropertyName();
		oneTimePasswordLengthDefaultValue = annotation.oneTimePasswordLengthDefaultValue();
		keystoreLocationPropertyName = annotation.keystoreLocationPropertyName();
		keystorePasswordPropertyName = annotation.keystorePasswordPropertyName();

		if (StringUtils.isEmpty(targetPackage)) {
			throw new IllegalArgumentException(
					String.format("targetPackage() in @%s for class %s is null or empty! that's not allowed",
							SecureJsonConverter.class.getSimpleName(), typeElement.getQualifiedName().toString()));
		}

		if (StringUtils.isEmpty(charsetPropertyName)) {
			throw new IllegalArgumentException(
					String.format("charsetPropertyName() in @%s for class %s is null or empty! that's not allowed",
							SecureJsonConverter.class.getSimpleName(), typeElement.getQualifiedName().toString()));
		}

		if (StringUtils.isEmpty(oneTimePasswordLengthPropertyName)) {
			throw new IllegalArgumentException(
					String.format("oneTimePasswordLengthPropertyName() in @%s for class %s is null or empty! that's not allowed",
							SecureJsonConverter.class.getSimpleName(), typeElement.getQualifiedName().toString()));
		}

		if (StringUtils.isEmpty(keystoreLocationPropertyName)) {
			throw new IllegalArgumentException(
					String.format("keystoreLocationPropertyName() in @%s for class %s is null or empty! that's not allowed",
							SecureJsonConverter.class.getSimpleName(), typeElement.getQualifiedName().toString()));
		}

		if (StringUtils.isEmpty(keystorePasswordPropertyName)) {
			throw new IllegalArgumentException(
					String.format("keystorePasswordPropertyName() in @%s for class %s is null or empty! that's not allowed",
							SecureJsonConverter.class.getSimpleName(), typeElement.getQualifiedName().toString()));
		}
	}

	public TypeElement getTypeElement() {
		return typeElement;
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public String getCharsetPropertyName() {
		return charsetPropertyName;
	}

	public String getCharsetDefaultValue() {
		return charsetDefaultValue;
	}

	public String getOneTimePasswordLengthPropertyName() {
		return oneTimePasswordLengthPropertyName;
	}

	public int getOneTimePasswordLengthDefaultValue() {
		return oneTimePasswordLengthDefaultValue;
	}

	public String getKeystoreLocationPropertyName() {
		return keystoreLocationPropertyName;
	}

	public String getKeystorePasswordPropertyName() {
		return keystorePasswordPropertyName;
	}

	public void generateCode(Filer filer) throws IOException {

		String charsetParamName = "charset";
		String oneTimePasswordLengthParamName = "oneTimePasswordLength";
		String keystoreLocationParamName = "keystoreLocation";
		String keystorePasswordParamName = "keystorePassword";
		String symmetricCryptoServiceParamName = "symmetricCryptoService";
		String asymmetricCryptoServiceParamName = "asymmetricCryptoService";

		AnnotationSpec annotationSpec = AnnotationSpec.builder(org.springframework.beans.factory.annotation.Value.class)
				.addMember("value", "$S", String.format("${%s:%s}", charsetPropertyName, charsetDefaultValue)).build();

		ParameterSpec charsetParam = ParameterSpec.builder(String.class, charsetParamName)
				.addAnnotation(AnnotationSpec.builder(org.springframework.beans.factory.annotation.Value.class)
						.addMember("value", "$S", String.format("${%s:%s}",charsetPropertyName, charsetDefaultValue)).build())
				.build();

		ParameterSpec oneTimePasswordLengthParam = ParameterSpec.builder(int.class, oneTimePasswordLengthParamName)
				.addAnnotation(AnnotationSpec.builder(org.springframework.beans.factory.annotation.Value.class)
						.addMember("value", "$S", String.format("${%s:%d}", oneTimePasswordLengthPropertyName, oneTimePasswordLengthDefaultValue)).build())
				.build();

		ParameterSpec keystoreLocationParam = ParameterSpec.builder(String.class, keystoreLocationParamName)
				.addAnnotation(AnnotationSpec.builder(org.springframework.beans.factory.annotation.Value.class)
						.addMember("value", "$S", String.format("${%s}", keystoreLocationPropertyName)).build())
				.build();

		ParameterSpec keystorePasswordParam = ParameterSpec.builder(String.class, keystorePasswordParamName)
				.addAnnotation(AnnotationSpec.builder(org.springframework.beans.factory.annotation.Value.class)
						.addMember("value", "$S", String.format("${%s}", keystorePasswordPropertyName)).build())
				.build();

		ParameterSpec symmetricCryptoServiceParam = ParameterSpec.builder(SymmetricCryptoService.class, symmetricCryptoServiceParamName)
				.build();

		ParameterSpec asymmetricCryptoServiceParam = ParameterSpec.builder(AsymmetricCryptoService.class, asymmetricCryptoServiceParamName)
				.build();



		MethodSpec constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addParameter(charsetParam)
				.addParameter(oneTimePasswordLengthParam)
				.addParameter(keystoreLocationParam)
				.addParameter(keystorePasswordParam)
				.addParameter(symmetricCryptoServiceParam)
				.addParameter(asymmetricCryptoServiceParam)
				.addAnnotation(org.springframework.beans.factory.annotation.Autowired.class)
				.addCode("super($L, $L, $L, $L, $L, $L);", charsetParamName, oneTimePasswordLengthParamName, keystoreLocationParamName, keystorePasswordParamName, symmetricCryptoServiceParamName, asymmetricCryptoServiceParamName)
				.build();

		AnnotationSpec generated = AnnotationSpec.builder(Generated.class)
				.addMember("value", "$S", SecureJsonConverterProcessor.class.getName())
				.build();

		TypeSpec secureJsonConverter = TypeSpec.classBuilder("Secured" + typeElement.getSimpleName() + "JsonConverter")
				.addAnnotation(generated)
				.addModifiers(Modifier.PUBLIC)
				.superclass(ParameterizedTypeName.get(ClassName.get(GenericSecureJsonConverterImpl.class), ClassName.get(typeElement)))
				.addAnnotation(Service.class)
				.addMethod(constructor)
				.build();

		JavaFile.builder(targetPackage, secureJsonConverter)
				.build()
				.writeTo(filer);
	}
}

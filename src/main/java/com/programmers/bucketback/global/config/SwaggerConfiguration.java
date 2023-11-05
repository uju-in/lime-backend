package com.programmers.bucketback.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI(ServletContext servletContext) {
		String contextPath = servletContext.getContextPath();
		Server server = new Server().url(contextPath);

		// Security 스키마 설정
		SecurityScheme bearerAuth = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("Authorization")
			.in(SecurityScheme.In.HEADER)
			.name(HttpHeaders.AUTHORIZATION);

		// Security 요청 설정
		SecurityRequirement addSecurityItem = new SecurityRequirement();
		addSecurityItem.addList("Authorization");

		return new OpenAPI()
			// Security 인증 컴포넌트 설정
			.components(new Components().addSecuritySchemes("Authorization", bearerAuth))
			// API 마다 Security 인증 컴포넌트 설정
			.addSecurityItem(addSecurityItem)
			.servers(List.of(server))
			.info(swaggerInfo());
	}

	private Info swaggerInfo() {
		License license = new License();
		license.setUrl("https://github.com/bucket-back/bucket-back-backend");
		license.setName("버킷백");

		return new Info()
			.version("v0.0.1")
			.title("\"버킷백 서버 API문서\"")
			.description("버킷백 서버의 API 문서 입니다.")
			.license(license);
	}

	// @Bean
	// public OperationCustomizer customize() {
	// 	return (Operation operation, HandlerMethod handlerMethod) -> {
	// 		ApiErrorCodeExample apiErrorCodeExample =
	// 			handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
	// 		// ApiErrorCodeExample 어노테이션 단 메소드 적용
	// 		if (apiErrorCodeExample != null) {
	// 			generateErrorCodeResponseExample(operation, apiErrorCodeExample.value(), apiErrorCodeExample.domain());
	// 		}
	// 		return operation;
	// 	};
	// }
	//
	// private void generateErrorCodeResponseExample(
	// 	Operation operation, Class<ErrorCode> type, String domain) {
	// 	ApiResponses responses = operation.getResponses();
	// 	// 해당 이넘에 선언된 에러코드들의 목록을 가져옵니다.
	// 	ErrorCode[] errorCodes = type.getEnumConstants();
	// 	// 400, 401, 404 등 에러코드의 상태코드들로 리스트로 모읍니다.
	// 	// 400 같은 상태코드에 여러 에러코드들이 있을 수 있습니다.
	// 	Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
	// 		Arrays.stream(errorCodes)
	// 			.filter(errorCode -> errorCode.getCode().substring(0, 1).equals(domain.substring(0, 1)))
	// 			.map(
	// 				errorCode -> {
	// 					return ExampleHolder.builder()
	// 						.statusCode(404)
	// 						.holder(getSwaggerExample(errorCode))
	// 						.errorCode((errorCode.getCode()))
	// 						.errorMessage(errorCode.getErrorMessage())
	// 						.build();
	// 				})
	// 			.collect(groupingBy(ExampleHolder::getStatusCode));
	// 	// response 객체들을 responses 에 넣습니다.
	// 	addExamplesToResponses(responses, statusWithExampleHolders);
	// }
	//
	// private Example getSwaggerExample(ErrorCode errorCode) {
	// 	//ErrorResponse 는 클라이언트한 실제 응답하는 공통 에러 응답 객체입니다.
	// 	Example example = new Example();
	// 	example.description(errorCode.getErrorMessage());
	// 	//example.setValue(errorCode.getCode());
	// 	example.setValue(
	// 		ResponseFactory.getFailResult(errorCode.getCode(),
	// 			errorCode.getErrorMessage())
	// 	);
	//
	// 	return example;
	// }
	//
	// private void addExamplesToResponses(
	// 	ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
	// 	statusWithExampleHolders.forEach(
	// 		(status, v) -> {
	// 			Content message = new Content();
	// 			MediaType mediaType = new MediaType();
	// 			// 상태 코드마다 ApiResponse을 생성합니다.
	// 			ApiResponse apiResponse = new ApiResponse();
	// 			//  List<ExampleHolder> 를 순회하며, mediaType 객체에 예시값을 추가합니다.
	// 			v.forEach(
	// 				exampleHolder -> mediaType.addExamples(
	// 					exampleHolder.getErrorCode(), exampleHolder.getHolder()));
	// 			// ApiResponse 의 message 에 mediaType을 추가합니다.
	// 			message.addMediaType("application/json", mediaType);
	// 			apiResponse.setContent(message);
	// 			// 상태코드를 key 값으로 responses 에 추가합니다.
	// 			responses.addApiResponse(status.toString(), apiResponse);
	// 		});
	// }

}

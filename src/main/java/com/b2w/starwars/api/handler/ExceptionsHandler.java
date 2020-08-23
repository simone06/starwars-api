package com.b2w.starwars.api.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.b2w.starwars.api.exceptions.PlanetaConflictException;
import com.b2w.starwars.api.exceptions.PlanetaNotFoundException;
import com.b2w.starwars.api.exceptions.SwApiPlanetaResponseNotFoundException;
import com.b2w.starwars.api.handler.tratarErro.ErrorApi;
import com.b2w.starwars.api.handler.tratarErro.TiposError;
import com.b2w.starwars.api.util.Constantes;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;


//Classe criada para personalizar as respostas do ResponseEntity  e para tratar excptions do spring;  
//Extende da classe "ResponseEntityExceptionHandler" para fazer o tratamento das exceptions internas do spring 
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PlanetaNotFoundException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(PlanetaNotFoundException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(SwApiPlanetaResponseNotFoundException.class)
	public ResponseEntity<?> tratarEntidadeSwapiException(SwApiPlanetaResponseNotFoundException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(PlanetaConflictException.class)
	public ResponseEntity<?> tratarEntidadeJaExistenteException(PlanetaConflictException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@Autowired
	private MessageSource messageSource;

	// tratamento de erros para Content type não aceitos
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		TiposError problemType = TiposError.ERRO_DE_SISTEMA;
		String detail = "Tipo de conteúdo informado não suportado";

		ErrorApi problem = createErrorApiBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

	    return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request, BindingResult bindingResult) {
		TiposError problemType = TiposError.DADOS_INVALIDOS;
		String detail = "Preencha os campos corretamente e tente novamente.";

		List<ErrorApi.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return ErrorApi.Object.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		ErrorApi problem = createErrorApiBuilder(status, problemType, detail).objects(problemObjects).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// Trata exceptions inesperadas
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		TiposError problemType = TiposError.ERRO_DE_SISTEMA;
		String detail = Constantes.MSG_ERRO_GENERICA_USUARIO_FINAL;

		ErrorApi problem = createErrorApiBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}


	// É chamado quando é passado valores incompativeis com o tipo do atributo.
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}

		// se nao for do tipo "PropertyBindingException", lançara um erro genérico
		TiposError problemType = TiposError.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		ErrorApi problem = createErrorApiBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	// pega as exceptions do tipo "PropertyBindingException", que são lançadas
	// quando o usuario passa uma propriedade inesitente
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		TiposError problemType = TiposError.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);

		ErrorApi problem = createErrorApiBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		// verificando se a resposta está vazia para colocar um "resumo" do erro
		if (body == null) {
			body = ErrorApi.builder().title(status.getReasonPhrase()).status(status.value()).build();
			
			// verivicando se a resposta no corpo é uma string, para não sobrecrever a mensagem passada
		} else if (body instanceof String) {
			body = ErrorApi.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ErrorApi.ErrorApiBuilder createErrorApiBuilder(HttpStatus status, TiposError problemType, String detail) {

		return ErrorApi.builder().status(status.value()).title(problemType.getTitle()).detail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}
}

package com.b2w.starwars.api.handler.tratarErro;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ErrorApi {

	/*
	 * O padrão RFC 7807, que é uma especificação que visa padronizar os formatos de
	 * mensagens de erro em APIs HTTP, para assim evitar que novos formatos sejam
	 * criados.
	 */

	/*
	 * O status HTTP gerado pelo servidor de origem. Normalmente deve ser o mesmo
	 * status HTTP da resposta, e pode servir de referência para casos onde um
	 * servidor proxy altera o status da resposta;
	 */
	private Integer status;

	

	/*
	 * uma URL para um documento que descreva o tipo do problema;
	 */
	private String type;

	/*
	 * um breve resumo do tipo de problema. Não deve mudar para ocorrências do mesmo
	 * tipo, exceto para fins de localização;
	 */
	private String title;

	/*
	 * descrição detalhada do problema;
	 */
	private String detail;

	
	/*
	 * propriedade opcional, com um URI exclusivo para o erro específico, que
	 * geralmente aponta para um log de erros para essa resposta.
	 * 
	 */
	private String instance;
	
	
	private List<Object> objects;

	
	//Criado para pegar as propriedades, no caso de erros.
	@Getter
	@Builder
	public static class Object {

		private String name;

		private String userMessage;

	}

}

package com.jorocha.coopervote.config;

import java.io.IOException;
import java.util.Base64;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 
 * @author https://www.gov.br/conecta/catalogo/apis/cadastro-base-do-cidadao-cbc-cpf/swagger_view
 *
 */
public class ClienteApiCpfLight {

	private static final String CONECTAGOV_URL = "https://h-apigateway.conectagov.estaleiro.serpro.gov.br";
	private static final String TOKEN_REQUEST_URL = CONECTAGOV_URL + "/oauth2/jwt-token";
	private static final String CLIENT_ID = "8ddc46f2-f6a3-4077-9e04-74b55de934a5";
	private static final String CLIENT_SECRET = "06d4aaac-1412-45f6-bd7c-38b2bef0d706";
	private static final String CONSULTA_CPF_URL = CONECTAGOV_URL + "/api-cpf-light/v2/consulta/cpf";

	/*
	 * Método para executar a consulta à API CPF Light. Formato: METHOD: POST
	 * HEADERS: Content-Type: application/json x-cpf-usuario: CPF do usuário que
	 * está executando a consulta. Authorization: "Bearer " + o token de acesso
	 * gerado de acordo com o método getToken BODY: { "listaCpf": [ "cpf1", "cpf2",
	 * "cpf3" (No Máximo 50 CPF's) ] }
	 * 
	 */
	public static String consultaCpf(String cpf)
			throws OAuthSystemException, OAuthProblemException, IOException {
		
		OAuthJSONAccessTokenResponse token = getToken();
		
		HttpPost post = new HttpPost(CONSULTA_CPF_URL);
		post.addHeader("Content-Type", "application/json");
		post.addHeader("x-cpf-usuario", "00000000191");
		post.addHeader("Authorization", "Bearer " + token.getAccessToken());

		String body = "{\"listaCpf\": [\""+cpf+"\"]}";
		post.setEntity(new StringEntity(body));

		String resultado = "";
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			resultado = EntityUtils.toString(response.getEntity());
		}

		return resultado;
	}

	/*
	 * Método para geração do token de acesso para executar as consultas às API's do
	 * Conectagov. Formato: METHOD: POST HEADERS: Content-Type:
	 * application/x-www-form-urlencoded Authorization: client_id + ":" +
	 * client_secret codificado em Base64 BODY:
	 * "grant_type=client_credentials&scope=" + o escopo da aplicação para acesso à api
	 * 
	 */
	private static OAuthJSONAccessTokenResponse getToken() throws OAuthSystemException, OAuthProblemException {
		OAuthClient client = new OAuthClient(new URLConnectionClient());

		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TOKEN_REQUEST_URL).buildQueryMessage();
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		request.addHeader("Authorization", base64EncodedBasicAuthentication());
		request.setBody("grant_type=client_credentials");

		return client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class);
	}

	private static String base64EncodedBasicAuthentication() {
		String authorization = new StringBuilder(CLIENT_ID).append(":").append(CLIENT_SECRET).toString();
		authorization = Base64.getEncoder().encodeToString(authorization.getBytes()).toString();
		return new StringBuffer("Basic ").append(authorization).toString();
	}

}
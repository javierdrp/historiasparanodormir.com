package com.historiasparanodormir.api;

import com.historiasparanodormir.api.entidad.Usuario;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
class ApiApplicationTests {

	TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:8080"));

	@Test()
	@DisplayName("POST /api/usuario/nuevo debe retornar el nuevo usuario creado")
	public void crearUsuarioTest() {
		// Given ...
		String endpoint = "/api/usuario/nuevo";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonUsuario = new JSONObject();
		try
		{
			jsonUsuario.put("email", "test@test.com");
			jsonUsuario.put("contrasena", "test");
			jsonUsuario.put("foto", "test");
		}
		catch(Exception ignored) {

		}
		// When ...
		ResponseEntity<Usuario> response = restTemplate.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(jsonUsuario.toString(), headers), Usuario.class);
		// Then ...
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals("test@test.com", response.getBody().email);
	}

	@Test()
	@DisplayName("GET /api/usuario/acceso con credenciales incorrectas debe fallar")
	public void contrasenaIncorrectaTest() {
		// Given ...
		String endpoint = "/api/usuario/acceso";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic " + new String(Base64.getEncoder().encode("jose@mail.com:contrasenaerronea".getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
		// When ...
		ResponseEntity<Usuario> response = restTemplate.exchange(endpoint, HttpMethod.GET, new HttpEntity<String>(headers), Usuario.class);
		// Then ...
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
}

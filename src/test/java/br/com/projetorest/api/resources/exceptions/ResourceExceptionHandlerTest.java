package br.com.projetorest.api.resources.exceptions;

import br.com.projetorest.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenobjectNotFoundExeptionThenReturnAnResponseEntity() {
        ResponseEntity<StandarError> response = exceptionHandler
                .objectNotFound(
                        new ObjectNotFoundException("Objeto não encontrado"),
                        new MockHttpServletRequest());



        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandarError.class, response.getBody().getClass());
        assertNotEquals("/user/2",response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(),response.getBody().getTimestemp());


        assertEquals("Objeto não encontrado",response.getBody().getError());
        assertEquals(404,response.getBody().getStatus());


    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandarError> response = exceptionHandler
                .dataIntegrityViolationException(new DataIntegrityViolationException("Email ja cadastrado"),new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandarError.class, response.getBody().getClass());

        assertEquals("Email ja cadastrado",response.getBody().getError());
        assertEquals(400,response.getBody().getStatus());
    }
}
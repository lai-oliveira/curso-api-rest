package br.com.projetorest.api.resources;

import br.com.projetorest.api.domain.Users;
import br.com.projetorest.api.domain.dto.UserDto;
import br.com.projetorest.api.services.UserService;
import br.com.projetorest.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    public static final String NAME     = "Laisa";
    public static final String EMAIL    = "laisa@gmail.com";
    public static final String PASSWORD = "123";
    public static final Integer ID = 1;
    public static final int INDEX = 0;

    private Users users;
    private UserDto userDto;

    @InjectMocks
    private UserResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        starUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(users);
        when(mapper.map(any(),any())).thenReturn(userDto );

        ResponseEntity<UserDto> response = resource.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(UserDto.class,response.getBody().getClass());

        assertEquals(ID,response.getBody().getId());
        assertEquals(NAME,response.getBody().getName());
        assertEquals(EMAIL,response.getBody().getEmail());
        assertEquals(PASSWORD,response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAnListOfUserDto() {
        when(service.findAll()).thenReturn(List.of(users));
        when(mapper.map(any(),any())).thenReturn(userDto);

        ResponseEntity<List<UserDto>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class,response.getBody().getClass());
        assertEquals(UserDto.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID,response.getBody().get(INDEX).getId());
        assertEquals(NAME,response.getBody().get(INDEX).getName());
        assertEquals(EMAIL,response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD,response.getBody().get(INDEX).getPassword());

    }

    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(users);
        ResponseEntity<UserDto> response = resource.create(userDto);

        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(userDto)).thenReturn(users);
        when(mapper.map(any(),any())).thenReturn(userDto);

        ResponseEntity<UserDto> response = resource.update(ID,userDto);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(UserDto.class,response.getBody().getClass());

        assertEquals(ID,response.getBody().getId());
        assertEquals(NAME,response.getBody().getName());
        assertEquals(EMAIL,response.getBody().getEmail());

    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDto>response = resource.delete(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(service, times(1)).delete(anyInt());

    }

    private void starUser(){
        users = new Users(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);

    }
}
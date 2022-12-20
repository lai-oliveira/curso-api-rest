package br.com.projetorest.api.services.impl;

import br.com.projetorest.api.domain.Users;
import br.com.projetorest.api.domain.dto.UserDto;
import br.com.projetorest.api.repositories.UserRepository;
import br.com.projetorest.api.services.exceptions.DataIntegratyViolationException;
import br.com.projetorest.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    public static final String NAME     = "Laisa";
    public static final String EMAIL    = "laisa@gmail.com";
    public static final String PASSWORD = "123";
    public static final Integer ID = 1;
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private Users users;

    private UserDto userDto;

    private Optional<Users>optionalUsers;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        starUser();
    }

    @Test
    void whenFindByIdThenReturAnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUsers);

        Users response = service.findById(ID);

        assertNotNull(response);
        assertEquals(Users.class,response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME,response.getName());
        assertEquals(EMAIL,response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());

        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(users));

        List<Users> response = service.findAll();
        assertNotNull(response);

        assertEquals(1,response.size());
        assertEquals(Users.class,response.get(0).getClass());

        assertEquals(ID,response.get(0).getId());
        assertEquals(NAME,response.get(0).getName());
        assertEquals(EMAIL,response.get(0).getEmail());
        assertEquals(PASSWORD,response.get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(users);

        Users response = service.create(userDto);


        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUsers);

        try {
            service.create(userDto);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class,ex.getClass());
            assertEquals("Email ja cadastrado no sisteme",ex.getMessage());
        }


    }
    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(users);

        Users response = service.update(userDto);


        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }
    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUsers);

        try {
            optionalUsers.get().setId(2);
            service.create(userDto);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class,ex.getClass());
            assertEquals("Email já cadastrado",ex.getMessage());
        }


    }


    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUsers);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository,times(1)).deleteById(anyInt());
    }
    @Test
    void deleteWithObjectNotFoundException(){
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.delete(ID);
        }catch (Exception ex){
           assertEquals(ObjectNotFoundException.class,ex.getClass());
           assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
        }
    }

    private void starUser(){

        users = new Users(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
        optionalUsers = Optional.of(new Users(ID, NAME, EMAIL, PASSWORD));
    }
}
package br.com.projetorest.api.services.impl;

import br.com.projetorest.api.domain.Users;
import br.com.projetorest.api.domain.dto.UserDto;
import br.com.projetorest.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    public static final String NAME     = "Laisa";
    public static final String EMAIL    = "laisa@gmail.com";
    public static final String PASSWORD = "123";
    public static final Integer ID = 1;
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
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void starUser(){

        users = new Users(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
        optionalUsers = Optional.of(new Users(ID, NAME, EMAIL, PASSWORD));
    }
}
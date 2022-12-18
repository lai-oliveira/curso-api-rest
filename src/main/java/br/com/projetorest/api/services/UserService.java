package br.com.projetorest.api.services;

import br.com.projetorest.api.domain.Users;
import br.com.projetorest.api.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    Users findById(Integer id);

    List<Users> findAll();

    Users create(UserDto obj);

    Users update(UserDto obj);

}

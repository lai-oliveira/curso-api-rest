package br.com.projetorest.api.services;

import br.com.projetorest.api.domain.Users;

public interface UserService {

    Users findById(Integer id);
}

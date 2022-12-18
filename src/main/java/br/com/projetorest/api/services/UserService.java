package br.com.projetorest.api.services;

import br.com.projetorest.api.domain.User;

public interface UserService {

    User findById(Integer id);
}

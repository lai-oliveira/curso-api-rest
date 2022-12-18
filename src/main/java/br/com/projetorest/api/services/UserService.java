package br.com.projetorest.api.services;

import br.com.projetorest.api.domain.Users;

import java.util.List;

public interface UserService {

    Users findById(Integer id);


  List<Users>findAll();
}

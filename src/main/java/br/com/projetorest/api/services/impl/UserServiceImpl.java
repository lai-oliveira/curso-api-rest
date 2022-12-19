package br.com.projetorest.api.services.impl;

import br.com.projetorest.api.domain.Users;
import br.com.projetorest.api.domain.dto.UserDto;
import br.com.projetorest.api.repositories.UserRepository;
import br.com.projetorest.api.services.UserService;
import br.com.projetorest.api.services.exceptions.DataIntegratyViolationException;
import br.com.projetorest.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public Users findById(Integer id) {
        Optional<Users> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Users> findAll(){
        return repository.findAll();
    }

    @Override
    public Users create(UserDto obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, Users.class));
    }

    @Override
    public Users update(UserDto obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj,Users.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UserDto obj){
        Optional<Users>user = repository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw  new DataIntegratyViolationException("Email já cadastrado");
        }

    }
}

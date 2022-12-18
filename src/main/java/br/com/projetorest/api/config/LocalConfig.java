package br.com.projetorest.api.config;

import br.com.projetorest.api.domain.Users;
import br.com.projetorest.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB(){
        Users u1 = new Users(null,"Laisa","laisa@gmail.com","123");
        Users u2 = new Users(null,"Jonas","jonas@gmail.com","123");

        repository.saveAll(List.of(u1,u2));
    }
}

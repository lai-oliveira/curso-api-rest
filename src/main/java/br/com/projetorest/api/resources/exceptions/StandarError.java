package br.com.projetorest.api.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StandarError {

    private LocalDateTime timestemp;
    private Integer status;
    private String error;
    private String path;
}

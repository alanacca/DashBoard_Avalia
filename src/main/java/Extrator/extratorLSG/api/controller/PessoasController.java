package Extrator.extratorLSG.api.controller;

import Extrator.extratorLSG.api.service.PessoasService;
import Extrator.extratorLSG.api.Request.PessoasRequest;
import Extrator.extratorLSG.api.event.RecursoCriadoEvent;
import Extrator.extratorLSG.api.model.Pessoas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins="http://localhost:8081",maxAge = 3600)
@RestController
@RequestMapping("/pessoas")
public class PessoasController {
    @Autowired
    private PessoasService pessoasService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Pessoas> novo(@Valid @RequestBody PessoasRequest pessoasRequest, HttpServletResponse response){
        Pessoas pessoasSalvo = this.pessoasService.salvarPessoaByPessoaRequest(pessoasRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this,response,pessoasSalvo.getIdPessoa()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoasSalvo);
    }
}

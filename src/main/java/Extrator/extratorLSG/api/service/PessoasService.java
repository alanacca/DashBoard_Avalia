package Extrator.extratorLSG.api.service;

import Extrator.extratorLSG.api.Request.PessoasRequest;
import Extrator.extratorLSG.api.model.Pessoas;
import Extrator.extratorLSG.api.repository.PessoasRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoasService {

    @Getter
    @Autowired
    PessoasRepository pessoasRepository;

    public Pessoas salvarPessoaByPessoaRequest(PessoasRequest pessoasRequest){
        Pessoas pessoa = new Pessoas(pessoasRequest);
        return this.pessoasRepository.save(pessoa);
    }
}

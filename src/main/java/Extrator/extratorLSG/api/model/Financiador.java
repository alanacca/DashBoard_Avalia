package Extrator.extratorLSG.api.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Financiador {
    public String SEQUENCIA_FINANCIADOR="";
    public String NOME_INSTITUICAO="";
    public String NATUREZA="";
}

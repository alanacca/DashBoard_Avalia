package Extrator.extratorLSG.api.model;

import Extrator.extratorLSG.api.Request.PessoasRequest;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "public", name="Pessoas")
public class Pessoas {
    @Id
    @Column(name="Id_pessoa")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idPessoa;

    @Column(name="Nome_Completo")
    private String nome;

    @Column(name="Id_Plataforma")
    private String idPlataforma;

    @Column(name="Plataforma")
    private String Platraforma;

    public Pessoas(){

    }

    public Pessoas(Integer id){
        this.idPessoa = id;
    }

    public Pessoas(PessoasRequest request){
        this.nome = request.nome;
        this.idPlataforma = request.idPlataforma;
        this.Platraforma = request.Plataforma;
    }
}

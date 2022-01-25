package Extrator.extratorLSG.api.Request;

public class PessoasRequest {
    public String nome;

    public String idPlataforma;

    public String Plataforma;

    @Override
    public String toString(){
        return "PessoasRequest [nome="+nome+", idPlataforma="+idPlataforma+",Plataforma="+Plataforma+"]";
    }
}

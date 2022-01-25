package Extrator.extratorLSG.api.model;

import Extrator.extratorLSG.api.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Projeto {
    public String SEQUENCIA_PROJETO;
    public String ANO_INICIO;
    public String ANO_FIM;
    public String NOME_DO_PROJETO;
    public String SITUACAO;
    public String NATUREZA;
    public String DESCRICAO_DO_PROJETO;
    public String NUMERO_GRADUACAO;
    public String NUMERO_ESPECIALIZACAO;
    public String NUMERO_MESTRADO_ACADEMICO;
    public String NUMERO_MESTRADO_PROF;
    public String NUMERO_DOUTORADO;
    public String integrantes = "";
    public Boolean isReponsavel = false;
    public ArrayList<Financiador> finaciamento = new ArrayList<>();


    public void persist(Connection connection, Curriculo cur) throws SQLException {
        if (SITUACAO.compareTo("DESATIVADO") ==0) return;

        String sql = "select id from projetos where lower(nome) = lower('" + Utils.strFormat(NOME_DO_PROJETO) + "')";
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        //checa se o projeto jÃ¡ existe
        if (!rs.next()) {

            sql = "insert into projetos("
                    + "sequencia_projeto,nome,situacao,"
                    + "ano_inicio, ano_fim, natureza, descricao,"
                    + "nro_graduacao, nro_mestrado_academico,"
                    + "nro_mestrado_profissionalizante, nro_doutorado, integrantes,created_at) values ("
                    + "'" + SEQUENCIA_PROJETO + "', "
                    + "'" + Utils.strFormat(NOME_DO_PROJETO) + "', "
                    + "'" + SITUACAO + "', "
                    + "'" + ANO_INICIO + "', "
                    + "'" + ANO_FIM + "', "
                    + "'" + NATUREZA + "', "
                    + "'" + Utils.strFormat(DESCRICAO_DO_PROJETO) + "', "
                    + "'" + NUMERO_GRADUACAO + "', "
                    + "'" + NUMERO_MESTRADO_ACADEMICO  + "', "
                    + "'" + NUMERO_MESTRADO_PROF + "', "
                    + "'" + NUMERO_DOUTORADO + "', "
                    + "'" + Utils.strFormat(integrantes) + "', now())";
            try {
                stmt.executeUpdate(sql);
            }catch(Exception e) {
                System.out.println("Erro: " + sql);
            }

            sql = "insert into integrantes_projetos(fk_curriculo,fk_projeto, is_responsavel)"
                    + "values ((select id from curriculos where nome_completo = '" + Utils.strFormat(cur.NOME_COMPLETO) + "'),"
                    + "        (select max(id) from projetos), " + isReponsavel.toString() + ")";
            stmt.executeUpdate(sql);

            for (Financiador f:finaciamento) {
                sql = "insert into financiadores_projetos (fk_curriculo, fk_projeto, nome_instituicao, natureza, created_at)"
                        + "values ("
                        + "'" + cur.NUMERO_IDENTIFICADOR + "',"
                        + "(select max(id) from projetos),"
                        + "'"+ Utils.strFormat(f.NOME_INSTITUICAO) + "',"
                        + "'"+ Utils.strFormat(f.NATUREZA) + "', now())";
                stmt.executeUpdate(sql);
            }

        }
        //checa se o projeto estÃ¡ associada ao integrante
        else {
            int id = rs.getInt("id");
            sql = "select fk_curriculo from integrantes_projetos "
                    + "where fk_projeto = '" + id + "'"
                    + "     and fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "'";
            rs = stmt.executeQuery(sql);

            //se nÃ£o estiver associada, inclui
            if (!rs.next()) {
                sql = "insert into integrantes_projetos(fk_curriculo,fk_projeto,created_at)"
                        + "values ((select id from curriculos where nome_completo = '" + Utils.strFormat(cur.NOME_COMPLETO) + "'),"
                        + "'"+ id + "', now())";
                stmt.executeUpdate(sql);

            }

        }

    }
}

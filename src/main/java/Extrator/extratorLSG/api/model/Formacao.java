package Extrator.extratorLSG.api.model;

import Extrator.extratorLSG.api.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Formacao {
    public String tipo_formacao;
    public String titulo_trabalho;
    public String titulo_residencia_medica;
    public String nome_orientador;
    public String nome_co_orientador;
    public String nome_instituicao;
    public String nome_orgao;
    public String nome_curso;
    public String ano_inicio;
    public String ano_conclusao;
    public String ano_obtencao_titulo;
    public String carga_horaria;

    public Curriculo cur;


    public void persist(Connection connection, Curriculo cur) throws SQLException {
        String sql = "select id from formacoes where fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "' "
                + "and titulo_trabalho = '" + Utils.strFormat(titulo_trabalho) + "'";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //checa se a produÃ§Ã£o jÃ¡ existe
        if (rs.next()) {

            sql = "delete from formacoes where fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "' "
                    + "and titulo_trabalho = '" + Utils.strFormat(titulo_trabalho) + "'";
            stmt.executeUpdate(sql);
        }


        sql = "insert into formacoes(fk_curriculo,tipo_formacao,titulo_trabalho,titulo_residencia_medica,"
                + "nome_orientador,nome_co_orientador,nome_instituicao,nome_orgao,nome_curso,ano_inicio,ano_conclusao,"
                + "ano_obtencao_titulo,carga_horaria)"
                + " values ("
                + " (select id from curriculos where nome_completo = '" + Utils.strFormat(cur.NOME_COMPLETO) + "'),"
                + "'" + tipo_formacao + "',"
                + "'" + Utils.strFormat(titulo_trabalho) + "',"
                + "'" + Utils.strFormat(titulo_residencia_medica) + "',"
                + "'" + Utils.strFormat(nome_orientador) + "',"
                + "'" + Utils.strFormat(nome_co_orientador) + "',"
                + "'" + Utils.strFormat(nome_instituicao) + "',"
                + "'" + Utils.strFormat(nome_orgao) + "',"
                + "'" + Utils.strFormat(nome_curso) + "',"
                + "'" + ano_inicio + "',"
                + "'" + ano_conclusao + "',"
                + "'" + ano_obtencao_titulo + "',"
                + "'" + carga_horaria + "')";
        stmt.executeUpdate(sql);
    }
}

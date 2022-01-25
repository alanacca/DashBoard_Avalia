package Extrator.extratorLSG.api.model;

import Extrator.extratorLSG.api.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Orientacao {
    public String sequencia_orientacao;
    public String natureza;
    public String tipo;
    public String titulo;
    public String ano;
    public String doi;
    public String tipo_orientacao;
    public String nome_orientando;
    public String nome_instituicao;
    public String nome_orgao;
    public String nome_curso;
    public Boolean is_finalizado=false;

    public void persist(Connection connection, Curriculo cur) throws SQLException {
        String sql = "select id from orientacoes where fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "' "
                + "and titulo = '" + Utils.strFormat(titulo) + "'";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //checa se a produÃ§Ã£o jÃ¡ existe
        if (rs.next()) {

            sql = "delete from orientacoes where fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "' "
                    + "and titulo = '" + Utils.strFormat(titulo) + "'";
            stmt.executeUpdate(sql);
        }

        sql = "insert into orientacoes(fk_curriculo,sequencia_orientacao, natureza, tipo, titulo, "
                + "ano, tipo_orientacao, nome_orientando, nome_instituicao, nome_curso, "
                + "is_finalizado,created_at) "
                + " values ("
                + " (select id from curriculos where nome_completo = '" + Utils.strFormat(cur.NOME_COMPLETO) + "'),"
                + "'" + sequencia_orientacao + "',"
                + "'" + natureza + "',"
                + "'" + tipo + "',"
                + "'" + Utils.strFormat(titulo) + "',"
                + "'" + ano + "',"
                + "'" + Utils.strFormat(tipo_orientacao) + "',"
                + "'" + Utils.strFormat(nome_orientando) + "',"
                + "'" + Utils.strFormat(nome_instituicao) + "',"
                + "'" + Utils.strFormat(nome_curso) + "',"
                + "'" + is_finalizado + "',now())";

        stmt.executeUpdate(sql);

    }
}

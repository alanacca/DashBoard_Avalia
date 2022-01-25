package Extrator.extratorLSG.api.model;

import Extrator.extratorLSG.api.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tecnica {
    public String sequencia_producao;
    public String tipo;
    public String ano;
    public String titulo;
    public String financiadora="";
    public String outras_informacoes="";
    public String autores = "";

    public void persist(Connection connection, Curriculo cur) throws SQLException {
        String sql = "select id from producoes_tecnicas where lower(titulo) = lower('" + Utils.strFormat(titulo) + "')";
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        //checa se a produÃ§Ã£o jÃ¡ existe
        if (!rs.next()) {
            sql = "insert into producoes_tecnicas("
                    + "sequencia_producao, tipo, ano, titulo, outras_informacoes, financiadora, autores,created_at"
                    + ") values ("
                    + "'" + sequencia_producao + "', "
                    + "'" + tipo + "', "
                    + "'" + ano +"', "
                    + "'" + Utils.strFormat(titulo) + "', "
                    + "'" + Utils.strFormat(outras_informacoes) + "', "
                    + "'" + Utils.strFormat(financiadora) + "', "
                    + "'" + Utils.strFormat(autores) + "', now())";
            try {
                stmt.executeUpdate(sql);
            }catch(Exception e)
            {
                System.out.println("erro:" + sql);
            }
            sql = "insert into autores_prod_tecnicas(fk_curriculo,fk_producao_tecnica)"
                    + "values ('" + cur.NUMERO_IDENTIFICADOR + "',"
                    + "        (select max(id) from producoes_tecnicas) )";
            stmt.executeUpdate(sql);

        }
        //checa se a produÃ§Ã£o estÃ¡ associada ao autor
        else {

            int id = rs.getInt("id");
            sql= "update producoes_tecnicas set "
                    + "ano='" + ano +"', "
                    + "titulo='" + Utils.strFormat(titulo) + "', "
                    + "outras_informacoes='" + Utils.strFormat(outras_informacoes) + "', "
                    + "financiadora='" + Utils.strFormat(financiadora) + "', "
                    + "autores='" + Utils.strFormat(autores) + "',"
                    + "updated_at =now()"
                    + "   where id = " + id;

            stmt.executeUpdate(sql);

            sql = "select fk_curriculo from autores_prod_tecnicas "
                    + "where fk_producao_tecnica = '" + id + "'"
                    + "     and fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "'";
            rs = stmt.executeQuery(sql);



            //se nÃ£o estiver associada, inclui
            if (!rs.next()) {
                sql = "insert into autores_prod_tecnicas(fk_curriculo,fk_producao_tecnica,created_at)"
                        + "values ('" + cur.NUMERO_IDENTIFICADOR + "',"
                        + "'"+ id + "',now())";
                stmt.executeUpdate(sql);
            }

        }

    }
}

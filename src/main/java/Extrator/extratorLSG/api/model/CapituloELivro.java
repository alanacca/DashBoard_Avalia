package Extrator.extratorLSG.api.model;

import Extrator.extratorLSG.api.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CapituloELivro {
    public String SEQUENCIA_PRODUCAO;
    public String TIPO;
    public String TITULO_DO_CAPITULO_DO_LIVRO;
    public String ANO;
    public String PAIS_DE_PUBLICACAO;
    public String DOI;
    public String TITULO_DO_LIVRO;
    public String NUMERO_DE_VOLUMES;
    public String PAGINA_INICIAL;
    public String PAGINA_FINAL;
    public String ISBN;
    public String ORGANIZADORES;
    public String NUMERO_DA_EDICAO_REVISAO;
    public String NUMERO_DA_SERIE;
    public String CIDADE_DA_EDITORA;
    public String NOME_DA_EDITORA;
    public String AUTORES = "";

    public void persist(Connection connection, Curriculo cur) throws SQLException {
        String sql = "select id from capitulos where lower(titulo) = lower('" + Utils.strFormat(TITULO_DO_CAPITULO_DO_LIVRO) + "')";
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        //checa se a produÃ§Ã£o jÃ¡ existe
        if (!rs.next()) {
            sql = "insert into capitulos("
                    + "tipo_producao,sequencia_producao,doi,capitulo_tipo,titulo,"
                    + "ano_trabalho,livro_nro_volumes,livro_nro_serie,livro_titulo,"
                    + "isbn,autores,pagina_inicio,pagina_fim,nome_editora,created_at"
                    + ") values ("
                    + "'CAPITULO-DE-LIVRO',"
                    + "'" + SEQUENCIA_PRODUCAO + "', "
                    + "'" + DOI + "', "
                    + "'" + TIPO + "', "
                    + "'" + Utils.strFormat(TITULO_DO_CAPITULO_DO_LIVRO) + "', "
                    + "'" + ANO + "', "
                    + "'" + Utils.strFormat(NUMERO_DE_VOLUMES) + "', "
                    + "'" + Utils.strFormat(NUMERO_DA_SERIE) + "', "
                    + "'" + Utils.strFormat(TITULO_DO_LIVRO) + "', "
                    + "'" + ISBN + "', "
                    + "'" + Utils.strFormat(AUTORES) + "', "
                    + "'" + PAGINA_INICIAL + "', "
                    + "'" + PAGINA_FINAL + "',"
                    + "'" + Utils.strFormat(NOME_DA_EDITORA) + "', now())";
            stmt.executeUpdate(sql);
            sql = "insert into capitulos_autores(fk_curriculo,fk_capitulo)"
                    + "values ((select id from curriculos where nome_completo = '" + Utils.strFormat(cur.NOME_COMPLETO) + "'),"
                    + "        (select max(id) from capitulos) )";
            stmt.executeUpdate(sql);

        }
        //checa se a produÃ§Ã£o estÃ¡ associada ao autor
        else {
            int id = rs.getInt("id");
            sql = "update capitulos set "
                    + "doi='" + DOI + "', "
                    + "capitulo_tipo='" + TIPO + "', "
                    + "titulo='" + Utils.strFormat(TITULO_DO_CAPITULO_DO_LIVRO) + "', "
                    + "ano_trabalho='" + ANO + "', "
                    + "livro_nro_volumes='" + Utils.strFormat(NUMERO_DE_VOLUMES) + "', "
                    + "livro_nro_serie='" + Utils.strFormat(NUMERO_DA_SERIE) + "', "
                    + "livro_titulo='" + Utils.strFormat(TITULO_DO_LIVRO) + "', "
                    + "isbn='" + ISBN + "', "
                    + "autores='" + Utils.strFormat(AUTORES) + "', "
                    + "pagina_inicio='" + PAGINA_INICIAL + "', "
                    + "pagina_fim='" + PAGINA_FINAL + "',"
                    + "nome_editora='" + Utils.strFormat(NOME_DA_EDITORA) + "',"
                    + "updated_at=now()"
                    + "   where id = " + id;

            stmt.executeUpdate(sql);

            sql = "select fk_curriculo from capitulos_autores "
                    + "where fk_capitulo = '" + id + "'"
                    + "     and fk_curriculo = '" + cur.NUMERO_IDENTIFICADOR + "'";
            rs = stmt.executeQuery(sql);



            //se nÃ£o estiver associada, inclui
            if (!rs.next()) {
                sql = "insert into capitulos_autores(fk_curriculo,fk_capitulo)"
                        + "values ((select id from curriculos where nome_completo = '" + Utils.strFormat(cur.NOME_COMPLETO) + "'),"
                        + "'"+ id + "')";
                stmt.executeUpdate(sql);
            }

        }

    }
}

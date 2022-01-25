package Extrator.extratorLSG.api.model;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Data
@Entity
public class Setor {
    public String nome;

    public void persist(Connection connection, Curriculo cur, boolean principal) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "delete from setor_curriculo where fk_curriculo = " + cur.NUMERO_IDENTIFICADOR;
        stmt.executeUpdate(sql);

        sql = "select id from setores where '" + nome + "' = nome";
        ResultSet rs = stmt.executeQuery(sql);

        //checa se o setor jÃ¡ existe
        if (!rs.next()) {
            sql = "insert into setores (nome) "
                    + "values ('" + nome + "')";
            stmt.executeUpdate(sql);
            sql = "select id from setores where '" + nome + "' = nome";
            rs = stmt.executeQuery(sql);
            rs.next();

        }
        sql = "insert into setor_curriculo (fk_curriculo, fk_setor, is_principal) "
                + "values (" + cur.NUMERO_IDENTIFICADOR + ", "
                + "" + rs.getInt(1) + ","
                + "" + principal + ")";
        stmt.executeUpdate(sql);
    }

}

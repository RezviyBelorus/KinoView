package dao;

import util.JDBCFactory;

import java.sql.Connection;

/**
 * Created by alexfomin on 30.06.17.
 */
public class AbstractDAO {
    protected Connection connection = JDBCFactory.createConnection();
}

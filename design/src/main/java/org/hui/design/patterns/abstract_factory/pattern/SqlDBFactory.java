package org.hui.design.patterns.abstract_factory.pattern;

// Sql Server
public class SqlDBFactory implements DBFactory {
    @Override
    public DBConnection creatDBConnection() {
        return new SqlConnection();
    }

    @Override
    public DBCommand createDBCommand() {
        return new SqlCommand();
    }

    @Override
    public DBDataReader createDBDataReader() {
        return new SqlDataReader();
    }
}

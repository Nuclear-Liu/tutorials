package org.hui.design.patterns.abstract_factory.pattern;

// Sql Server
public class OracleDBFactory implements DBFactory {
    @Override
    public DBConnection creatDBConnection() {
        return new OracleConnection();
    }

    @Override
    public DBCommand createDBCommand() {
        return new OracleCommand();
    }

    @Override
    public DBDataReader createDBDataReader() {
        return new OracleDataReader();
    }
}

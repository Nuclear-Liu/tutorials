package org.hui.design.patterns.abstract_factory.pattern;

public interface DBFactory {
    DBConnection creatDBConnection();
    DBCommand createDBCommand();
    DBDataReader createDBDataReader();
}

package org.hui.design.patterns.abstract_factory.pattern;

import java.util.List;

public class EmployeeDAO {
    private DBFactory dbFactory;
    public EmployeeDAO(DBFactory dbFactory) {
        this.dbFactory = dbFactory;
    }
    public List<String> getEmployees() {
        DBConnection connection = dbFactory.creatDBConnection();
//        connection.connectionString("...");
        DBCommand command = dbFactory.createDBCommand();
//        command.commandText("...");
//        command.setConnection(connection); // 相互关联
//        DBDataReader reader = command.executeReader(); // 相互关联
        /*while (reader.read()) {
            // ...
        }*/
        return null;
    }
}

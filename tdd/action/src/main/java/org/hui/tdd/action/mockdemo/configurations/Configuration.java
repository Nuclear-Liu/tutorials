package org.hui.tdd.action.mockdemo.configurations;

/**
 * We add the configuration interface as part of refactoring process.
 * @author Hui.Liu
 * @since 2021-12-05 16:09
 */
public interface Configuration {

    /**
     * Getter method to get the SQL query to execute.
     * @param sqlString
     * @return
     */
    String getSQL(String sqlString);
}

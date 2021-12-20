package org.hui.tdd.action.mockdemo.configurations;

/**
 * Mock implementation fo the configuration interface.
 * @author Hui.Liu
 * @since 2021-12-05 16:47
 */
public class MockConfiguration implements Configuration {

    /**
     * Sets the sql query.
     * @param sqlString
     */
    public void setSQL(String sqlString) {
    }

    /**
     * Gets the sql query.
     * @param sqlString
     * @return
     */
    @Override
    public String getSQL(String sqlString) {
        return null;
    }
}

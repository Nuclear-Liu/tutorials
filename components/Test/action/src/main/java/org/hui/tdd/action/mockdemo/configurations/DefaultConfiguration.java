package org.hui.tdd.action.mockdemo.configurations;

/**
 * @author Hui.Liu
 * @since 2021-12-05 16:14
 */
public class DefaultConfiguration implements Configuration {

    /**
     * Constructor.
     * @param configurationName
     */
    public DefaultConfiguration(String configurationName) {

    }

    /**
     * Getter method to get the sql that we want to execute.
     * @param sqlString
     * @return
     */
    @Override
    public String getSQL(String sqlString) {
        return null;
    }
}

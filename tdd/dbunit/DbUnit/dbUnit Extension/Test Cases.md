## Overview

dbUnit has multiple base test case classes - use these classes as the basis for your tests. All extend DBTestCase and the top level DatabaseTestCase.

Use the submenus to learn about each of them.

## General Notes

1. For additional examples, refer to the ITs for the specific test case.
2. To change the setup or teardown operation (e.g. change the teardown to org.dbunit.operation.DatabaseOperation.DELETE_ALL), set the setUpOperation or tearDownOperation property on the databaseTester.
3. To set DatabaseConfig features/properties, one way is to extend the test case class and override the setUpDatabaseConfig(DatabaseConfig config) method from DatabaseTestCase.

## ValueComparer Comparison

### Overview

Introduced in [Release 2.6.0](), the [ValueComparer]() is a strategy interface enabling any type of comparison, not only equality.

It enables comparing difficult-to-compare column values such as:

* auto-incrementing ids
* timestamps

It enables comparisons such as:

* greater-than
* less-than
* contains
* complex multi-column-based
* dynamically choosing the ValueComparer based on criteria

### Usage

This flexibility and power has slightly more complex usage than equality comparison.

To use [ValueComparer]() comparison, the dbUnit dataset files do not change. Instead, the tests use dbUnit assert methods that work with [ValueComparer]()s.

#### Core ValueComparer Classes

Package [`org.dbunit.assertion.comparer.value`]() contains the [ValueComparer]() implementations and related interfaces/classes.

The ValueComparers class has singleton instances of the ones provided by dbUnit plus pre-configured variances (e.g. isActualWithinOneMinuteNewerOfExpectedTimestamp). 
Start with these as they provide for most comparison needs.

It is easy to add your own implementations of the ValueComparer interface, either directly or using base classes such as ValueComparerBase and ValueComparerTemplateBase.

Please let us know of comparison needs not handled by the ValueComparer implementations and ValueComparers instances as maybe we should add it to dbUnit.

#### Assert with ValueComparer Classes

The Assertion and DbUnitValueComparerAssert classes have assertWithValueComparer() methods for the ValueComparer comparisons. See DbUnitValueComparerAssertIT for examples using DbUnitValueComparerAssert.

#### TestCase with ValueComparer Classes

PrepAndExpectedTestCase directly supports ValueComparer in addition to equality comparison via VerifyTableDefinition. See PrepAndExpectedTestCase overview for examples with it.

### Example

This example shows defining the value comparers for comparing the expected table to the actual table. In addition to the expected and actual table parameters, Assertion.assertWithValueComparer() uses a default value comparer (used for columns not listed in the columns map) and the column value comparers map for comparing specified columns.

It uses the value comparer instances defined on the [ValueComparers]() class.

```
@Test
public void testExample() throws Exception
{
    ITable expectedTable = ...; // declare the expected table
    ITable actualTable = ...;  // declare the actual table
    ValueComparer defaultValueComparer = ValueComparers.isActualEqualToExpected;
    Map<String, ValueComparer> columnValueComparers =
            new ColumnValueComparerMapBuilder()
                    .add("COLUMN1", ValueComparers.isActualGreaterThanExpected)
                    .add("COLUMN2", ValueComparers.isActualLessOrEqualToThanExpected)
                    .build();

    Assertion.assertWithValueComparer(expectedTable, actualTable, defaultValueComparer, columnValueComparers);
}
```

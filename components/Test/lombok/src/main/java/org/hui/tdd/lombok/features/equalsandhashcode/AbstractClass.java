package org.hui.tdd.lombok.features.equalsandhashcode;

import lombok.EqualsAndHashCode;

/**
 * @author Hui.Liu
 * @since 2021-12-30 12:09
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AbstractClass {
    @EqualsAndHashCode.Include
    private int includeVal;
//    @EqualsAndHashCode.Exclude
    private int excludeVal;
}

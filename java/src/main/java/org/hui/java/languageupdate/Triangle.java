package org.hui.java.languageupdate;

/**
 * @author Hui.Liu
 * @since 2021-12-20 19:25
 */
public record Triangle<C extends Coordinate>(C top, C left, C right) {
}

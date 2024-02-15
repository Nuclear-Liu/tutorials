package org.hui.service.composite.product;

/**
 * service addresses.
 *
 * @param cmp composite address
 * @param pro product address
 * @param rev review address
 * @param rec recommendation address
 */
public record ServiceAddresses(
        String cmp,
        String pro,
        String rev,
        String rec) {
}

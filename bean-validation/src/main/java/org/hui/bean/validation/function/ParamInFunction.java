package org.hui.bean.validation.function;

import jakarta.validation.Valid;
import org.hui.bean.validation.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ParamInFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamInFunction.class);

    private ParamInFunction() {}

    static void inParam(@Valid User user) {
        LOGGER.info("call function inParam.");
    }
}

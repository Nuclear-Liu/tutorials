package org.hui.bean.validation.function;

import org.hui.bean.validation.domain.User;
import org.junit.jupiter.api.Test;

import static org.hui.bean.validation.function.ParamInFunction.inParam;
import static org.junit.jupiter.api.Assertions.*;

class ParamInFunctionTest {

    @Test
    void testInParam() {
        User u = new User();
        inParam(u);
    }
}
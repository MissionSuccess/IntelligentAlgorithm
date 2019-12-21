package com.gx.intelligentalgorithm.knightproblem;

import org.junit.jupiter.api.Test;

public class KnightProblemApplication {
    /**
     * 骑士问题入口
     */
    @Test
    public void application() {
        KnightProblem knightProblem = new KnightProblem();

        knightProblem.build(5,5,false,false);
        knightProblem.build(5,5,true,false);
    }
}

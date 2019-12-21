package com.gx.intelligentalgorithm.knightproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * 骑士周游问题
 */
public class KnightProblem {

    /**棋盘大小*/
    private final int N = 8;

    /**棋盘*/
    private int[][] chessboard;

    /**下一次落子策略*/
    private List<ChessboardPoint> nextStepStrategy;

    /**是否已经找到解*/
    private boolean isFinish;

    /**起始X*/
    private int startX;

    /**起始Y*/
    private int startY;

    /**累计找到解*/
    private int sum = 0;

    /**是否回到起点*/
    private boolean isBack;

    /**是否找到所有的解*/
    private boolean isFindAll;

    KnightProblem() {
        chessboard = new int[N+1][N+1];
        initNextStepStrategy();
        this.isFinish = false;
    }

    /**
     * 初始化策略，一共8个方向可移动
     */
    void initNextStepStrategy() {
        nextStepStrategy = new ArrayList<>();
        nextStepStrategy.add(new ChessboardPoint(1, -2));
        nextStepStrategy.add(new ChessboardPoint(2, -1));
        nextStepStrategy.add(new ChessboardPoint(2, 1));
        nextStepStrategy.add(new ChessboardPoint(1, 2));
        nextStepStrategy.add(new ChessboardPoint(-1, 2));
        nextStepStrategy.add(new ChessboardPoint(-2, 1));
        nextStepStrategy.add(new ChessboardPoint(-2, -1));
        nextStepStrategy.add(new ChessboardPoint(-1, -2));
    }

    /**
     * 展示棋盘落子情况
     */
    void showChessboard() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                String s = chessboard[i][j]+"";
                if(s.length() == 1) {
                    s = "0" + s;
                }
                System.out.print("[" + s + "] ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 重置棋盘以及相关属性
     */
    void reset() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                chessboard[i][j] = 0;
            }
        }
        this.isFinish = false;
        this.sum = 0;
        this.isBack = false;
        this.isFindAll = false;
    }

    /**
     * 构建解
     * @param x 起始X
     * @param y 起始Y
     * @param isBack 是否回到起点
     * @param isFindAll 是否找到所有的解
     */
    void build(int x, int y, boolean isBack, boolean isFindAll) {

        // 校验数据
        if(!checkCoordinate(x, y)) {
            return;
        }

        // 重置棋盘属性
        reset();

        // 存储传入参数
        this.startX = x;
        this.startY = y;
        this.isBack = isBack;
        this.isFindAll = isFindAll;

        // 调用主程序
        mainFunction(x,y,1);

    }

    /**
     * 主要递归算法
     * @param x x
     * @param y y
     * @param step 当前步数
     */
    void mainFunction(int x, int y, int step) {

        // 校验数据
        if(!checkCoordinate(x, y) || chessboard[x][y] != 0) {
            return;
        }

        // 最后一个格子了
        if(step == N*N) {
            // 是否需要回到起点
            if(isBack && Math.abs(x-startX)+Math.abs(y-startY) != 3) {
                return;
            }
            sum++;
            System.out.println("第"+sum+"个解:");
            chessboard[x][y] = step;
            showChessboard();
            chessboard[x][y] = 0;

            // 是否要找全部的解
            if(!isFindAll) {
                isFinish = true;
            }
            return;
        }

        chessboard[x][y] = step;

        // 判断剩余点是否可达(剪枝关键)
        if(!remainPointCanNext()) {
            chessboard[x][y] = 0;
            return;
        }

        List<ChessboardPoint> nextSteps = findNextStep(x, y) ;
        // 下一步的可选点排序,贪心的策略使得更快地找到解(剪枝关键中的关键)
        nextSteps.sort((s1,s2) -> {
            List<ChessboardPoint> newNextList1 = findNextStep(s1.getX(),s1.getY());
            List<ChessboardPoint> newNextList2 = findNextStep(s2.getX(),s2.getY());
            return Integer.compare(newNextList1.size(),newNextList2.size());
        });

        // 递归回溯
        for(ChessboardPoint nextStep : nextSteps) {
            int newX = nextStep.getX();
            int newY = nextStep.getY();
            mainFunction(newX,newY,step+1);

            // 只找一个解的结束标记
            if(isFinish) {
                return;
            }

        }
        chessboard[x][y] = 0;
    }

    /**
     * 检查剩余点是否可达
     * @return true:都可达
     */
    boolean remainPointCanNext() {

        // 筛选出剩余没走的点
        List<ChessboardPoint> remainPoints = new ArrayList<>();
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                if(chessboard[i][j] == 0) {
                    remainPoints.add(new ChessboardPoint(i,j));
                }
            }
        }

        // 只剩下一个点不需要考虑
        if(remainPoints.size() <= 1) {
            return true;
        }

        // 判断是否还有路回到起点
        if(isBack && findNextStep(startX,startY).isEmpty()) {
            return false;
        }

        // 遍历检查可达
        for (ChessboardPoint remainPoint : remainPoints) {
            if(findNextStep(remainPoint.getX(),remainPoint.getY()).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 找到下一步可以去的地方
     * @param x 横坐标
     * @param y 纵坐标
     * @return 下一个地点的集合
     */
    List<ChessboardPoint> findNextStep(int x, int y) {

        List<ChessboardPoint> nextSteps = new ArrayList<>();

        for (ChessboardPoint p : nextStepStrategy) {// 没有走过的格子为0
            int tempX = x + p.getX();
            int tempY = y + p.getY();

            if (checkCoordinate(tempX, tempY) && chessboard[tempX][tempY] == 0) {
                nextSteps.add(new ChessboardPoint(tempX, tempY));
            }
        }

        return nextSteps;
    }

    /**
     * 检验坐标合法性
     * @param x x
     * @param y y
     * @return true：合法
     */
    boolean checkCoordinate(int x, int y) {
        return (x > 0 && x <= N) && (y > 0 && y <= N);
    }

    /**
     * 封装点对象
     */
    public static class ChessboardPoint {
         private int x;
         private int y;

         public ChessboardPoint(int x, int y) {
             this.x = x;
             this.y = y;
         }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }
}

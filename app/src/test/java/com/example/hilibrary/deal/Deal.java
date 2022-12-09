package com.example.hilibrary.deal;


import java.util.HashMap;
import java.util.Random;

/**
 *
 */
public class Deal {
    private int multiple = 1;                           //倍数默认1倍
    private double winRate = 0.5;                       //胜率
    private double serviceChargeSum = 0;                //模拟盈亏比，次数
    private int simulateCount = 100;                    //默认100次
    private double moneyCount = 10;                   //总钱数
    private double moneyCountImmobilization = 0;        //总钱数
    private double serviceCharge = 0.0000;              //手续费
    private boolean immobilization = false;             //true固定 false不固定
    private Random random = new Random();               //随机数
    private double winProportion = 1.5;                 //赢比
    private double loseProportion = 1;                  //亏比
    private double winMoneyProportion = 0.03;           //赢钱幅度 0.01代表1%
    private double oldMoneyCount = moneyCount;

    private boolean notLog = false;

    public boolean isNotLog() {
        return notLog;
    }

    public void setNotLog(boolean notLog) {
        this.notLog = notLog;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public double getMoneyCountImmobilization() {
        return moneyCountImmobilization;
    }

    public void setMoneyCountImmobilization(double moneyCountImmobilization) {
        this.moneyCountImmobilization = moneyCountImmobilization;
    }

    public boolean isImmobilization() {
        return immobilization;
    }

    public void setImmobilization(boolean immobilization) {
        this.immobilization = immobilization;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public double getWinProportion() {
        return winProportion;
    }

    public void setWinProportion(double winProportion) {
        this.winProportion = winProportion;
    }

    public double getLoseProportion() {
        return loseProportion;
    }

    public void setLoseProportion(double loseProportion) {
        this.loseProportion = loseProportion;
    }

    public double getWinMoneyProportion() {
        return winMoneyProportion;
    }

    public void setWinMoneyProportion(double winMoneyProportion) {
        this.winMoneyProportion = winMoneyProportion;
    }

    public double getOldMoneyCount() {
        return oldMoneyCount;
    }

    public void setOldMoneyCount(double oldMoneyCount) {
        this.oldMoneyCount = oldMoneyCount;
    }

    public Deal() {

    }

    public Deal(double moneyCount, int multiple, double serviceCharge, int simulateCount, double serviceChargeSum) {
        this.moneyCount = moneyCount;
        this.multiple = multiple;
        this.serviceCharge = serviceCharge;
        this.simulateCount = simulateCount;
        this.serviceChargeSum = serviceChargeSum;

    }


    /**
     * @param winProportion      赢比
     * @param loseProportion     亏比
     * @param winMoneyProportion 赢钱幅度
     * @param winRate
     * @return
     */
    public double simulateDealGD(double winProportion, double loseProportion, double winMoneyProportion, double winRate) {
        Random random = new Random();
        winRate = winRate * 1000.0;

        int winCount = 0; //胜利次数
        int loseCount = 0; //失败次数
        double sumWin = 0;
        double sumLose = 0;


        log(winMoneyProportion + "-" + winRate);
        //输百分比
        double loseMoneyProportion = winMoneyProportion * (loseProportion / winProportion); //输百分比
        //放大后单次手续费
        double amplificationServiceCharge = (serviceCharge * multiple);
        log("放大后单次手续费：" + amplificationServiceCharge + (amplificationServiceCharge == 0.0005));

        for (int i = 0; i < simulateCount; i++) {
            int sy = random.nextInt(1000) + 1; //1-1000随机数
            double buyServiceChange = moneyCount * amplificationServiceCharge; //买手续费

            if (sy <= winRate) {
                //放大手续费和收益
//                moneyCount = moneyCount + (moneyCount * winMoneyProportion * multiple);
                double sellServiceChange = moneyCount * amplificationServiceCharge; //卖手续费
                //除去手续费最终盈利
//                moneyCount = moneyCount - (buyServiceChange + sellServiceChange);
                serviceChargeSum = serviceChargeSum + (buyServiceChange + sellServiceChange);
                sumWin = sumWin + (moneyCount * winMoneyProportion * multiple);
                //现在总资金 - (买入手续费 - 卖出手续费)
                //比如这一次是赢钱，那赢钱 1000+ (1000 * 0.01) - (1000*0.005 卖出手续费1000*0.005)
                //1000+10 - 0.5+0.5 = 9赢钱 = 1009 手续费 1
                winCount++;
            } else {
//                moneyCount = moneyCount - (moneyCount * loseMoneyProportion * multiple);
                double sellServiceChange = moneyCount * amplificationServiceCharge; //卖手续费
                //总数-（买手续费+卖手续费）
//                moneyCount = moneyCount - (buyServiceChange + sellServiceChange);
                serviceChargeSum = serviceChargeSum + (buyServiceChange + sellServiceChange);
                sumLose = sumLose + (moneyCount * loseMoneyProportion * multiple);
//                log(moneyCount+"--"+i);
                loseCount++;
            }
        }
        //算上手续费实际盈亏比

        if (winCount == 5) {
            //总亏损
            //总盈利
            System.out.println("\n\n\n\n模拟次数：" + simulateCount);
            System.out.println("总盈利：" + sumWin);
            System.out.println("总亏损：" + sumLose);
            System.out.println("手续费：" + serviceChargeSum);
            System.out.println("实际营收：" + (moneyCount + (sumWin - sumLose) - serviceChargeSum));
        }
        return winCount;

    }


    /**
     * 算法
     * 赢还是输
     *
     * @return
     */
    public DealBean simulateDeal() {

        //止损幅度
        double loseMoneyProportion = loseProportion / winProportion * winMoneyProportion;
        double recordWinMoney = 0; //记录赚钱
        double recordLoseMoney = 0; //记录亏钱
        double recordServiceCharge = 0; //记录手续费
        boolean s = true;
        oldMoneyCount = moneyCount;
        //模拟次数
        DealBean bean = new DealBean();

        while (s) {
            moneyCount = oldMoneyCount;
            int winMoneyCount = 0;
            int loseMoneyCount = 0;
            recordWinMoney = 0; //记录赚钱
            recordLoseMoney = 0; //记录亏钱
            recordServiceCharge = 0; //记录手续费
            moneyCountImmobilization = 0;
            bean = new DealBean();

            for (int i = 1; i <= simulateCount; i++) {
                //买入手续费 本金 * 手续费 * 倍数
                if (isSuccess()) {
                    winMoneyCount++;
                    //赢钱数
                    double winMoneyNum = (moneyCount * winMoneyProportion) * multiple;
                    //持仓金额 * 费率
                    double buyServiceCharge = moneyCount * serviceCharge * multiple;
                    double sellMoney = (moneyCount + winMoneyNum);
                    double sellServiceCharge = (sellMoney * serviceCharge * multiple);

                    if (!immobilization) {
                        moneyCount = moneyCount + winMoneyNum - (sellServiceCharge + buyServiceCharge);
                    } else {
                        moneyCountImmobilization = moneyCountImmobilization + winMoneyNum - (sellServiceCharge + buyServiceCharge);
                    }
                    recordWinMoney = recordWinMoney + winMoneyNum;
                    recordServiceCharge = recordServiceCharge + buyServiceCharge + sellServiceCharge;
                    bean.getRe().add("赚钱" + recordWinMoney + " 总资金" + moneyCount + "\n");

                } else {
                    loseMoneyCount++;
                    //亏钱数
                    double loseMoneyNum = (moneyCount * loseMoneyProportion) * multiple;
                    //手续费计算
                    double buyServiceCharge = moneyCount * serviceCharge * multiple;    //买入费率计算
                    double sellMoney = (moneyCount - loseMoneyNum);                     //现有总资金-亏损钱
                    double sellServiceCharge = (sellMoney * serviceCharge * multiple);  //卖出费率计算
                    if (!immobilization) {
                        //金额等于总钱+赢钱 - 手续费
                        moneyCount = moneyCount - loseMoneyNum - (sellServiceCharge + buyServiceCharge);
                    } else {
                        moneyCountImmobilization = moneyCountImmobilization - loseMoneyNum - (sellServiceCharge + buyServiceCharge);
                    }
                    recordLoseMoney = recordLoseMoney + loseMoneyNum;
                    recordServiceCharge = recordServiceCharge + buyServiceCharge + sellServiceCharge;

                    bean.getRe().add("亏钱" + loseMoneyNum + " 总资金" + moneyCount + "\n");
                }
            }
            if (winMoneyCount == winRate * 100) {
                s = false;
            }
        }

        log("\n\n\n\n\n\n同样比例，为什么多亏钱：亏5%  100/95 = 1.052631578");
        log("模拟次数：" + simulateCount);
        log("类型：" + (immobilization ? "固定" : "滚仓"));
        log("胜率：" + (winRate * 100) + "%");
        log("盈亏比：" + "   \n\t赢比：" + winProportion + "\n\t亏比：" + loseProportion);
        log("赢损率：" + "   \n\t赢率：" + winMoneyProportion + "%" + "\n\t损率：" + loseMoneyProportion);
        log("本金：" + oldMoneyCount);
        if (immobilization) {
            log("总收益：" + (recordWinMoney - recordLoseMoney));
        } else {
            log("总收益：" + (moneyCount - oldMoneyCount));
        }
        log("总赚钱：" + recordWinMoney);
        log("总亏钱：" + recordLoseMoney);
        log("总手续费续费：" + recordServiceCharge);
        if (!immobilization) {
            log("总金额：" + moneyCount);
        } else {
            log("总金额：" + (moneyCount + moneyCountImmobilization));
        }

        bean.setRecordWinMoney(recordWinMoney);
        bean.setRecordLoseMoney(recordLoseMoney);
        bean.setRecordServiceCharge(recordServiceCharge);

        log("\n\n\n\n\n\n");
        return bean;
    }


    /**
     * 随机生成1-100，判断成功还是失败
     * 获取成功还是失败
     *
     * @return
     */
    public boolean isSuccess() {
        int sj = random.nextInt(100) + 1;
        if (100 - (winRate * 100) < sj) {
            return true;
        } else {
            return false;
        }
    }


    public double getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(double moneyCount) {
        this.moneyCount = moneyCount;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public int getSimulateCount() {
        return simulateCount;
    }

    public void setSimulateCount(int simulateCount) {
        this.simulateCount = simulateCount;
    }

    public double getServiceChargeSum() {
        return serviceChargeSum;
    }

    public void setServiceChargeSum(double serviceChargeSum) {
        this.serviceChargeSum = serviceChargeSum;
    }

    private void log(String s) {
        if (!notLog) {
            System.out.println(s);
        }
    }

}
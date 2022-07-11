package com.example.hilibrary

import org.junit.Assert.*
import org.junit.Test
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {


//        var deal = Deal();
//        deal.isNotLog = true
//        deal.isImmobilization = false
//        deal.winProportion = 1.5;  //盈亏比
//        deal.loseProportion = 1.0; //盈亏比
//        deal.winRate = 0.5; //盈亏比
//        deal.winMoneyProportion = 0.05; //目标幅度
//        var d = deal.simulateDeal()


        var record = arrayListOf<StringBuffer>();

        var winMoneyProportion1 = StringBuffer();

        for(i in 1 until 10){
            var sp = StringBuffer();
            for (i in 1 until 20) {
                var deal = Deal();
                deal.isNotLog = true
                deal.isImmobilization = true
                deal.winProportion = 1.5;  //盈亏比
                deal.loseProportion = 1.0; //盈亏比
                deal.winRate = 0.5; //胜率
                deal.winMoneyProportion = 0.01 * i; //目标幅度
                var d = deal.simulateDeal()
                var df = DecimalFormat("#.##");
                var df1 = DecimalFormat("##.#");
                df.roundingMode = RoundingMode.CEILING;
//                record.add(df.format(d.recordWinMoney / d.recordLoseMoney));
                winMoneyProportion1.append(((df1.format(deal.winMoneyProportion*100)).toString()+"%\t"));
                sp.append(df.format(d.recordWinMoney - d.recordLoseMoney) + "\t");
            }
            record.add(sp);
        }

        println("\n\n\n\n\n");

//        println(winMoneyProportion1);

        for (r in record) {
            println(r);
        }
        println("\n\n\n\n\n");

        assertEquals(4, 2 + 2)


    }
}
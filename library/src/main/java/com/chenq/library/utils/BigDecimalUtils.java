package com.chenq.library.utils;

import java.math.BigDecimal;

/**
 * create by chenqi on 2020/7/12.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class BigDecimalUtils {

    private static BigDecimalUtils mInstance;

    public static BigDecimalUtils getInstance() {
        if (mInstance == null) {
            mInstance = new BigDecimalUtils();
        }
        return mInstance;
    }

    public BigDecimalUtils() {
    }

    /**
     * 两数相加
     *
     * @param v1
     * @param v2
     * @return
     */
    public Double add(double v1, double v2) {
        return new BigDecimal(Double.toString(v1)).add(new BigDecimal(Double.toString(v2))).doubleValue();
    }

    /**
     * 两数相减
     *
     * @param v1
     * @param v2
     * @return
     */
    public Double sub(double v1, double v2) {
        return new BigDecimal(Double.toString(v1)).subtract(new BigDecimal(Double.toString(v2))).doubleValue();
    }

    /**
     * 两数相乘
     *
     * @param v1
     * @param v2
     * @param scale        保留小数点后位数
     * @param roundingMode 舍入模式 例如：BigDecimal.ROUND_HALF_UP为四舍五入
     * @return
     */
    public Double mul(double v1, double v2, int scale, int roundingMode) {
        if (scale < 0) return 0d;
        return new BigDecimal(Double.toString(v1))
                .multiply(new BigDecimal(Double.toString(v2)))
                .divide(new BigDecimal("1.0"), scale, roundingMode)
                .doubleValue();
    }

    /**
     * 两数相除
     *
     * @param v1
     * @param v2
     * @param scale        保留小数点后位数
     * @param roundingMode 舍入模式 例如：BigDecimal.ROUND_HALF_UP为四舍五入
     * @return
     */
    public Double div(double v1, double v2, int scale, int roundingMode) {
        if (scale < 0) return 0d;
        return new BigDecimal(Double.toString(v1))
                .divide(new BigDecimal(Double.toString(v2)), scale, roundingMode)
                .doubleValue();
    }


}

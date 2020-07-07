package com.example.chenq.util;

import android.content.Context;
import android.graphics.Color;

import com.example.chenq.R;

/**
 * create by chenqi on 2020/6/28.
 * Email: chenqwork@gmail.com
 * Desc: 调光灯相关配置
 */
public class DimmingLampConfig {

    private static final String TAG = "DimmingLampConfig";

    private static String[] rgbXY =
            new String[]{
                    "45184 19672",
                    "45169 19742",
                    "45145 19814",
                    "45113 19889",
                    "45074 19968",
                    "45026 20050",
                    "44971 20135",
                    "44908 20224",
                    "44839 20315",
                    "44762 20409",
                    "44679 20507",
                    "44589 20607",
                    "44493 20710",
                    "44391 20815",
                    "44283 20924",
                    "44169 21034",
                    "44051 21148",
                    "43927 21263",
                    "43799 21381",
                    "43666 21501",
                    "43529 21624",
                    "43388 21748",
                    "43243 21874",
                    "43095 22002",
                    "42944 22131",
                    "42790 22263",
                    "42633 22395",
                    "42404 22589",
                    "42169 22789",
                    "41927 22994",
                    "41679 23204",
                    "41425 23419",
                    "41165 23639",
                    "40900 23864",
                    "40629 24093",
                    "40354 24326",
                    "40074 24564",
                    "39790 24804",
                    "39502 25048",
                    "39210 25296",
                    "38915 25546",
                    "38616 25799",
                    "38315 26054",
                    "38011 26311",
                    "37705 26570",
                    "37398 26831",
                    "37088 27093",
                    "36777 27357",
                    "36465 27621",
                    "36153 27886",
                    "35839 28151",
                    "35526 28417",
                    "35212 28683",
                    "34704 29113",
                    "34175 29561",
                    "33626 30027",
                    "33056 30510",
                    "32465 31010",
                    "31854 31528",
                    "31223 32062",
                    "30572 32614",
                    "29903 33181",
                    "29216 33763",
                    "28512 34359",
                    "27793 34969",
                    "27060 35589",
                    "26315 36220",
                    "25561 36860",
                    "24799 37505",
                    "24032 38155",
                    "23263 38806",
                    "22495 39457",
                    "21732 40104",
                    "20975 40745",
                    "20229 41376",
                    "19498 41996",
                    "18783 42602",
                    "18090 43189",
                    "17420 43756",
                    "17095 43997",
                    "16748 44135",
                    "16373 44121",
                    "15969 43914",
                    "15535 43478",
                    "15075 42786",
                    "14593 41822",
                    "14095 40585",
                    "13593 39086",
                    "13094 37353",
                    "12609 35427",
                    "12147 33357",
                    "11715 31196",
                    "11321 28998",
                    "10966 26814",
                    "10653 24685",
                    "10381 22646",
                    "10148 20721",
                    "9952 18926",
                    "9788 17270",
                    "9652 15754",
                    "9541 14378",
                    "9452 13134",
                    "9379 12015",
                    "9320 11012",
                    "9272 10114",
                    "9254 9753",
                    "9240 9399",
                    "9231 9053",
                    "9227 8715",
                    "9230 8386",
                    "9241 8066",
                    "9259 7758",
                    "9286 7461",
                    "9323 7175",
                    "9368 6902",
                    "9424 6643",
                    "9490 6397",
                    "9567 6166",
                    "9656 5950",
                    "9755 5748",
                    "9866 5563",
                    "9989 5394",
                    "10123 5240",
                    "10270 5104",
                    "10428 4984",
                    "10598 4881",
                    "10779 4794",
                    "10972 4725",
                    "11176 4672",
                    "11392 4635",
                    "11618 4615",
                    "11849 4697",
                    "12092 4784",
                    "12346 4877",
                    "12611 4975",
                    "12887 5078",
                    "13174 5187",
                    "13473 5301",
                    "13782 5420",
                    "14102 5545",
                    "14433 5674",
                    "14774 5809",
                    "15125 5948",
                    "15486 6093",
                    "15856 6242",
                    "16235 6396",
                    "16623 6554",
                    "17019 6716",
                    "17423 6883",
                    "17834 7053",
                    "18252 7227",
                    "18677 7404",
                    "19108 7585",
                    "19544 7769",
                    "19985 7955",
                    "20430 8144",
                    "20879 8336",
                    "21785 8762",
                    "22747 9215",
                    "23766 9695",
                    "24840 10200",
                    "25967 10729",
                    "27143 11282",
                    "28364 11855",
                    "29622 12445",
                    "30909 13048",
                    "32214 13659",
                    "33526 14272",
                    "34830 14882",
                    "36114 15481",
                    "37360 16063",
                    "38555 16619",
                    "39683 17143",
                    "40731 17630",
                    "41687 18073",
                    "42542 18468",
                    "43290 18812",
                    "43927 19104",
                    "44452 19343",
                    "44869 19532"
            };

    /**
     * 根据rgb_xy 获取数组下标
     *
     * @param atrValue
     * @return
     */
    public static int getIndexFromXYY(String atrValue) {
        if (atrValue != null && !atrValue.equals("")) {
            for (int i = 0; i < rgbXY.length; i++) {
                if (rgbXY[i].equals(atrValue)) return i;
            }
        }
        return -1;
    }

    /**
     * 根据index 获取RGB_XY
     *
     * @param index
     * @return
     */
    public static String getXYYFromIndex(int index) {
        if (index >= rgbXY.length || index < 0) {
            return null;
        }
        return rgbXY[index];
    }

    /**
     * 获取rgb 数组长度
     *
     * @return
     */
    public static int getRGBXYLength() {
        return rgbXY.length;
    }


    /**
     * 获取调光灯色阶数组，38份
     *
     * @param context
     * @return
     */
    public static int[] getDimmingColor(Context context) {
        return new int[]{
                R.color.color_dimming_list_1,
                R.color.color_dimming_list_2,
                R.color.color_dimming_list_3,
                R.color.color_dimming_list_4,
                R.color.color_dimming_list_5,
                R.color.color_dimming_list_6,
                R.color.color_dimming_list_7,
                R.color.color_dimming_list_8,
                R.color.color_dimming_list_9,
                R.color.color_dimming_list_10,
                R.color.color_dimming_list_11,
                R.color.color_dimming_list_12,
                R.color.color_dimming_list_13,
                R.color.color_dimming_list_14,
                R.color.color_dimming_list_15,
                R.color.color_dimming_list_16,
                R.color.color_dimming_list_17,
                R.color.color_dimming_list_18,
                R.color.color_dimming_list_19,
                R.color.color_dimming_list_20,
                R.color.color_dimming_list_21,
                R.color.color_dimming_list_22,
                R.color.color_dimming_list_23,
                R.color.color_dimming_list_24,
                R.color.color_dimming_list_25,
                R.color.color_dimming_list_26,
                R.color.color_dimming_list_27,
                R.color.color_dimming_list_28,
                R.color.color_dimming_list_29,
                R.color.color_dimming_list_30,
                R.color.color_dimming_list_31,
                R.color.color_dimming_list_32,
                R.color.color_dimming_list_33,
                R.color.color_dimming_list_34,
                R.color.color_dimming_list_35,
                R.color.color_dimming_list_36,
                R.color.color_dimming_list_37,
                R.color.color_dimming_list_38
        };
    }


    private String rgb = "";

    /**
     * 获取RGB色值
     *
     * @param
     * @return
     */
    public static int[] getDimmingRGBColor() {
        return new int[]{
                R.color.color_rgb_001,
                R.color.color_rgb_002,
                R.color.color_rgb_003,
                R.color.color_rgb_004,
                R.color.color_rgb_005,
                R.color.color_rgb_006,
                R.color.color_rgb_007,
                R.color.color_rgb_008,
                R.color.color_rgb_009,
                R.color.color_rgb_010,
                R.color.color_rgb_011,
                R.color.color_rgb_012,
                R.color.color_rgb_013,
                R.color.color_rgb_014,
                R.color.color_rgb_015,
                R.color.color_rgb_016,
                R.color.color_rgb_017,
                R.color.color_rgb_018,
                R.color.color_rgb_019,
                R.color.color_rgb_020,
                R.color.color_rgb_021,
                R.color.color_rgb_022,
                R.color.color_rgb_023,
                R.color.color_rgb_024,
                R.color.color_rgb_025,
                R.color.color_rgb_026,
                R.color.color_rgb_027,
                R.color.color_rgb_028,
                R.color.color_rgb_029,
                R.color.color_rgb_030,
                R.color.color_rgb_031,
                R.color.color_rgb_032,
                R.color.color_rgb_033,
                R.color.color_rgb_034,
                R.color.color_rgb_035,
                R.color.color_rgb_036,
                R.color.color_rgb_037,
                R.color.color_rgb_038,
                R.color.color_rgb_039,
                R.color.color_rgb_040,
                R.color.color_rgb_041,
                R.color.color_rgb_042,
                R.color.color_rgb_043,
                R.color.color_rgb_044,
                R.color.color_rgb_045,
                R.color.color_rgb_046,
                R.color.color_rgb_047,
                R.color.color_rgb_048,
                R.color.color_rgb_049,
                R.color.color_rgb_050,
                R.color.color_rgb_051,
                R.color.color_rgb_052,
                R.color.color_rgb_053,
                R.color.color_rgb_054,
                R.color.color_rgb_055,
                R.color.color_rgb_056,
                R.color.color_rgb_057,
                R.color.color_rgb_058,
                R.color.color_rgb_059,
                R.color.color_rgb_060,
                R.color.color_rgb_061,
                R.color.color_rgb_062,
                R.color.color_rgb_063,
                R.color.color_rgb_064,
                R.color.color_rgb_065,
                R.color.color_rgb_066,
                R.color.color_rgb_067,
                R.color.color_rgb_068,
                R.color.color_rgb_069,
                R.color.color_rgb_070,
                R.color.color_rgb_071,
                R.color.color_rgb_072,
                R.color.color_rgb_073,
                R.color.color_rgb_074,
                R.color.color_rgb_075,
                R.color.color_rgb_076,
                R.color.color_rgb_077,
                R.color.color_rgb_078,
                R.color.color_rgb_079,
                R.color.color_rgb_080,
                R.color.color_rgb_081,
                R.color.color_rgb_082,
                R.color.color_rgb_083,
                R.color.color_rgb_084,
                R.color.color_rgb_085,
                R.color.color_rgb_086,
                R.color.color_rgb_087,
                R.color.color_rgb_088,
                R.color.color_rgb_089,
                R.color.color_rgb_090,
                R.color.color_rgb_091,
                R.color.color_rgb_092,
                R.color.color_rgb_093,
                R.color.color_rgb_094,
                R.color.color_rgb_095,
                R.color.color_rgb_096,
                R.color.color_rgb_097,
                R.color.color_rgb_098,
                R.color.color_rgb_099,
                R.color.color_rgb_100,
                R.color.color_rgb_101,
                R.color.color_rgb_102,
                R.color.color_rgb_103,
                R.color.color_rgb_104,
                R.color.color_rgb_105,
                R.color.color_rgb_106,
                R.color.color_rgb_107,
                R.color.color_rgb_108,
                R.color.color_rgb_109,
                R.color.color_rgb_110,
                R.color.color_rgb_111,
                R.color.color_rgb_112,
                R.color.color_rgb_113,
                R.color.color_rgb_114,
                R.color.color_rgb_115,
                R.color.color_rgb_116,
                R.color.color_rgb_117,
                R.color.color_rgb_118,
                R.color.color_rgb_119,
                R.color.color_rgb_120,
                R.color.color_rgb_121,
                R.color.color_rgb_122,
                R.color.color_rgb_123,
                R.color.color_rgb_124,
                R.color.color_rgb_125,
                R.color.color_rgb_126,
                R.color.color_rgb_127,
                R.color.color_rgb_128,
                R.color.color_rgb_129,
                R.color.color_rgb_130,
                R.color.color_rgb_131,
                R.color.color_rgb_132,
                R.color.color_rgb_133,
                R.color.color_rgb_134,
                R.color.color_rgb_135,
                R.color.color_rgb_136,
                R.color.color_rgb_137,
                R.color.color_rgb_138,
                R.color.color_rgb_139,
                R.color.color_rgb_140,
                R.color.color_rgb_141,
                R.color.color_rgb_142,
                R.color.color_rgb_143,
                R.color.color_rgb_144,
                R.color.color_rgb_145,
                R.color.color_rgb_146,
                R.color.color_rgb_147,
                R.color.color_rgb_148,
                R.color.color_rgb_149,
                R.color.color_rgb_150,
                R.color.color_rgb_151,
                R.color.color_rgb_152,
                R.color.color_rgb_153,
                R.color.color_rgb_154,
                R.color.color_rgb_155,
                R.color.color_rgb_156,
                R.color.color_rgb_157,
                R.color.color_rgb_158,
                R.color.color_rgb_159,
                R.color.color_rgb_160,
                R.color.color_rgb_161,
                R.color.color_rgb_162,
                R.color.color_rgb_163,
                R.color.color_rgb_164,
                R.color.color_rgb_165,
                R.color.color_rgb_166,
                R.color.color_rgb_167,
                R.color.color_rgb_168,
                R.color.color_rgb_169,
                R.color.color_rgb_170,
                R.color.color_rgb_171,
                R.color.color_rgb_172,
                R.color.color_rgb_173,
                R.color.color_rgb_174,
                R.color.color_rgb_175,
                R.color.color_rgb_176,
                R.color.color_rgb_177,
                R.color.color_rgb_178,
                R.color.color_rgb_179,
                R.color.color_rgb_180
        };
    }

    private static float[] r = new float[]{
            224.0f,
            225.0f,
            226.0f,
            227.0f,
            228.0f,
            229.0f,
            230.0f,
            231.0f,
            232.0f,
            233.0f,
            234.0f,
            235.0f,
            236.0f,
            237.0f,
            238.0f,
            239.0f,
            240.0f,
            241.0f,
            242.0f,
            243.0f,
            244.0f,
            245.0f,
            246.0f,
            247.0f,
            248.0f,
            249.0f,
            250.0f,
            249.8846153846154f,
            249.76923076923077f,
            249.65384615384616f,
            249.53846153846155f,
            249.42307692307693f,
            249.30769230769232f,
            249.19230769230768f,
            249.07692307692307f,
            248.96153846153845f,
            248.84615384615384f,
            248.73076923076923f,
            248.6153846153846f,
            248.5f,
            248.3846153846154f,
            248.26923076923077f,
            248.15384615384616f,
            248.03846153846155f,
            247.92307692307693f,
            247.80769230769232f,
            247.69230769230768f,
            247.57692307692307f,
            247.46153846153845f,
            247.34615384615384f,
            247.23076923076923f,
            247.1153846153846f,
            247.0f,
            241.69230769230768f,
            236.3846153846154f,
            231.07692307692307f,
            225.76923076923077f,
            220.46153846153845f,
            215.15384615384616f,
            209.84615384615384f,
            204.53846153846155f,
            199.23076923076923f,
            193.92307692307693f,
            188.6153846153846f,
            183.30769230769232f,
            178.0f,
            172.69230769230768f,
            167.3846153846154f,
            162.0769230769231f,
            156.76923076923077f,
            151.46153846153845f,
            146.15384615384616f,
            140.84615384615387f,
            135.53846153846155f,
            130.23076923076923f,
            124.92307692307693f,
            119.61538461538461f,
            114.30769230769232f,
            109.0f,
            104.8076923076923f,
            100.61538461538461f,
            96.42307692307692f,
            92.23076923076923f,
            88.03846153846153f,
            83.84615384615384f,
            79.65384615384616f,
            75.46153846153845f,
            71.26923076923077f,
            67.07692307692307f,
            62.88461538461538f,
            58.69230769230769f,
            54.5f,
            50.30769230769231f,
            46.11538461538461f,
            41.92307692307692f,
            37.730769230769226f,
            33.53846153846153f,
            29.34615384615384f,
            25.153846153846146f,
            20.961538461538453f,
            16.76923076923076f,
            12.576923076923066f,
            8.384615384615387f,
            4.192307692307693f,
            0.0f,
            3.769230769230769f,
            7.538461538461538f,
            11.307692307692307f,
            15.076923076923077f,
            18.846153846153847f,
            22.615384615384613f,
            26.384615384615383f,
            30.153846153846153f,
            33.92307692307692f,
            37.69230769230769f,
            41.46153846153846f,
            45.230769230769226f,
            49.0f,
            52.76923076923077f,
            56.53846153846154f,
            60.30769230769231f,
            64.07692307692308f,
            67.84615384615384f,
            71.61538461538461f,
            75.38461538461539f,
            79.15384615384615f,
            82.92307692307692f,
            86.6923076923077f,
            90.46153846153845f,
            94.23076923076923f,
            98.0f,
            101.23076923076923f,
            104.46153846153847f,
            107.6923076923077f,
            110.92307692307692f,
            114.15384615384616f,
            117.38461538461539f,
            120.61538461538461f,
            123.84615384615384f,
            127.07692307692308f,
            130.30769230769232f,
            133.53846153846155f,
            136.76923076923077f,
            140.0f,
            143.23076923076923f,
            146.46153846153845f,
            149.69230769230768f,
            152.92307692307693f,
            156.15384615384616f,
            159.3846153846154f,
            162.6153846153846f,
            165.84615384615387f,
            169.0769230769231f,
            172.30769230769232f,
            175.53846153846155f,
            178.76923076923077f,
            182.0f,
            183.75f,
            185.5f,
            187.25f,
            189.0f,
            190.75f,
            192.5f,
            194.25f,
            196.0f,
            197.75f,
            199.5f,
            201.25f,
            203.0f,
            204.75f,
            206.5f,
            208.25f,
            210.0f,
            211.75f,
            213.5f,
            215.25f,
            217.0f,
            218.75f,
            220.5f,
            222.25f
    };

    private static float[] g = new float[]{
            32.0f,
            34.61538461538461f,
            37.23076923076923f,
            39.84615384615385f,
            42.46153846153846f,
            45.07692307692308f,
            47.69230769230769f,
            50.30769230769231f,
            52.92307692307692f,
            55.53846153846154f,
            58.15384615384615f,
            60.769230769230774f,
            63.38461538461539f,
            66.0f,
            68.61538461538461f,
            71.23076923076923f,
            73.84615384615384f,
            76.46153846153845f,
            79.07692307692308f,
            81.6923076923077f,
            84.3076923076923f,
            86.92307692307693f,
            89.53846153846155f,
            92.15384615384616f,
            94.76923076923077f,
            97.38461538461539f,
            100.0f,
            103.11538461538461f,
            106.23076923076923f,
            109.34615384615384f,
            112.46153846153847f,
            115.57692307692308f,
            118.6923076923077f,
            121.8076923076923f,
            124.92307692307692f,
            128.03846153846155f,
            131.15384615384616f,
            134.26923076923077f,
            137.3846153846154f,
            140.5f,
            143.6153846153846f,
            146.73076923076923f,
            149.84615384615384f,
            152.96153846153845f,
            156.0769230769231f,
            159.19230769230768f,
            162.30769230769232f,
            165.4230769230769f,
            168.53846153846155f,
            171.65384615384616f,
            174.76923076923077f,
            177.8846153846154f,
            181.0f,
            182.19230769230768f,
            183.3846153846154f,
            184.57692307692307f,
            185.76923076923077f,
            186.96153846153845f,
            188.15384615384616f,
            189.34615384615384f,
            190.53846153846155f,
            191.73076923076923f,
            192.92307692307693f,
            194.1153846153846f,
            195.30769230769232f,
            196.5f,
            197.69230769230768f,
            198.8846153846154f,
            200.07692307692307f,
            201.26923076923077f,
            202.46153846153845f,
            203.65384615384616f,
            204.84615384615384f,
            206.03846153846155f,
            207.23076923076923f,
            208.42307692307693f,
            209.6153846153846f,
            210.80769230769232f,
            212.0f,
            209.42307692307693f,
            206.84615384615384f,
            204.26923076923077f,
            201.69230769230768f,
            199.1153846153846f,
            196.53846153846155f,
            193.96153846153845f,
            191.3846153846154f,
            188.80769230769232f,
            186.23076923076923f,
            183.65384615384616f,
            181.07692307692307f,
            178.5f,
            175.9230769230769f,
            173.34615384615384f,
            170.76923076923077f,
            168.19230769230768f,
            165.6153846153846f,
            163.03846153846155f,
            160.46153846153845f,
            157.8846153846154f,
            155.30769230769232f,
            152.73076923076923f,
            150.15384615384613f,
            147.57692307692307f,
            145.0f,
            141.5f,
            138.0f,
            134.5f,
            131.0f,
            127.5f,
            124.0f,
            120.5f,
            117.0f,
            113.5f,
            110.0f,
            106.5f,
            103.0f,
            99.5f,
            96.0f,
            92.5f,
            89.0f,
            85.5f,
            82.0f,
            78.5f,
            75.0f,
            71.5f,
            68.0f,
            64.5f,
            61.0f,
            57.5f,
            54.0f,
            53.15384615384615f,
            52.30769230769231f,
            51.46153846153846f,
            50.61538461538461f,
            49.76923076923077f,
            48.92307692307692f,
            48.07692307692308f,
            47.23076923076923f,
            46.38461538461539f,
            45.53846153846154f,
            44.69230769230769f,
            43.84615384615385f,
            43.0f,
            42.15384615384615f,
            41.30769230769231f,
            40.46153846153846f,
            39.61538461538461f,
            38.769230769230774f,
            37.92307692307692f,
            37.07692307692308f,
            36.230769230769226f,
            35.38461538461539f,
            34.53846153846154f,
            33.69230769230769f,
            32.84615384615385f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f,
            32.0f
    };

    private static float[] b = new float[]{
            32.0f,
            30.76923076923077f,
            29.53846153846154f,
            28.307692307692307f,
            27.076923076923077f,
            25.846153846153847f,
            24.615384615384613f,
            23.384615384615383f,
            22.153846153846153f,
            20.923076923076923f,
            19.692307692307693f,
            18.46153846153846f,
            17.23076923076923f,
            16.0f,
            14.769230769230766f,
            13.538461538461537f,
            12.307692307692307f,
            11.076923076923077f,
            9.846153846153847f,
            8.615384615384613f,
            7.384615384615383f,
            6.153846153846153f,
            4.92307692307692f,
            3.69230769230769f,
            2.46153846153846f,
            1.23076923076923f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            9.807692307692308f,
            19.615384615384617f,
            29.423076923076927f,
            39.23076923076923f,
            49.03846153846154f,
            58.846153846153854f,
            68.65384615384616f,
            78.46153846153847f,
            88.26923076923077f,
            98.07692307692308f,
            107.88461538461539f,
            117.69230769230771f,
            127.50000000000001f,
            137.30769230769232f,
            147.1153846153846f,
            156.92307692307693f,
            166.73076923076925f,
            176.53846153846155f,
            186.34615384615387f,
            196.15384615384616f,
            205.96153846153848f,
            215.76923076923077f,
            225.5769230769231f,
            235.38461538461542f,
            245.1923076923077f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            255.0f,
            253.80769230769232f,
            252.6153846153846f,
            251.42307692307693f,
            250.23076923076923f,
            249.03846153846155f,
            247.84615384615384f,
            246.65384615384616f,
            245.46153846153845f,
            244.26923076923077f,
            243.07692307692307f,
            241.8846153846154f,
            240.69230769230768f,
            239.5f,
            238.30769230769232f,
            237.1153846153846f,
            235.92307692307693f,
            234.73076923076923f,
            233.53846153846155f,
            232.34615384615384f,
            231.15384615384616f,
            229.96153846153845f,
            228.76923076923077f,
            227.57692307692307f,
            226.3846153846154f,
            225.19230769230768f,
            224.0f,
            216.0f,
            208.0f,
            200.0f,
            192.0f,
            184.0f,
            176.0f,
            168.0f,
            160.0f,
            152.0f,
            144.0f,
            136.0f,
            128.0f,
            120.0f,
            112.0f,
            104.0f,
            96.0f,
            88.0f,
            80.0f,
            72.0f,
            64.0f,
            56.0f,
            48.0f,
            40.0f
    };


    public static int[] mRgbColors = new int[]{
            -2088928,
            -2022882,
            -1956579,
            -1890532,
            -1824229,
            -1757927,
            -1691880,
            -1625577,
            -1559530,
            -1493228,
            -1426925,
            -1360878,
            -1294575,
            -1228272,
            -1162226,
            -1095923,
            -1029876,
            -963573,
            -897271,
            -831224,
            -764921,
            -698874,
            -632572,
            -566269,
            -500222,
            -433919,
            -367616,
            -432384,
            -431616,
            -430848,
            -430080,
            -429312,
            -428544,
            -427776,
            -427008,
            -491520,
            -490752,
            -489984,
            -489216,
            -488448,
            -487680,
            -486912,
            -486144,
            -485376,
            -549888,
            -549120,
            -548352,
            -547584,
            -546816,
            -546048,
            -545280,
            -544512,
            -543488,
            -936448,
            -1263872,
            -1591296,
            -1984256,
            -2311680,
            -2638848,
            -3031808,
            -3359232,
            -3686656,
            -4079616,
            -4406784,
            -4734208,
            -5061632,
            -5454592,
            -5782016,
            -6109184,
            -6502144,
            -6829568,
            -7156992,
            -7549952,
            -7877120,
            -8204544,
            -8597504,
            -8924928,
            -9252352,
            -9579520,
            -9907959,
            -10170861,
            -10433507,
            -10696409,
            -10959055,
            -11287494,
            -11550396,
            -11813042,
            -12075944,
            -12338590,
            -12667029,
            -12929675,
            -13192577,
            -13455479,
            -13718125,
            -14046564,
            -14309210,
            -14572112,
            -14834758,
            -15097660,
            -15426099,
            -15688745,
            -15951647,
            -16214293,
            -16477195,
            -16739841,
            -16544257,
            -16282881,
            -16021761,
            -15760385,
            -15564801,
            -15303425,
            -15042305,
            -14780929,
            -14585345,
            -14323969,
            -14062849,
            -13801473,
            -13540353,
            -13344513,
            -13083393,
            -12822017,
            -12560897,
            -12365057,
            -12103937,
            -11842561,
            -11581441,
            -11385601,
            -11124481,
            -10863105,
            -10601985,
            -10340609,
            -10144259,
            -9947908,
            -9751557,
            -9555206,
            -9293319,
            -9096969,
            -8900362,
            -8704011,
            -8442124,
            -8245773,
            -8049423,
            -7853072,
            -7590929,
            -7394578,
            -7198227,
            -7001877,
            -6805526,
            -6543639,
            -6347288,
            -6150681,
            -5954331,
            -5692444,
            -5496093,
            -5299742,
            -5103391,
            -4841248,
            -4775720,
            -4644656,
            -4513592,
            -4382528,
            -4317000,
            -4185936,
            -4054872,
            -3923808,
            -3858280,
            -3727216,
            -3596152,
            -3465088,
            -3399560,
            -3268496,
            -3137432,
            -3006368,
            -2940840,
            -2809776,
            -2678712,
            -2547648,
            -2482120,
            -2351056,
            -2219992
    };


    public static int getRGBColorValue(int i) {
        if (i < 0 || i >= 180) {
            return 0;
        }
        return mRgbColors[i];
    }

    public static int getRGBColor(int i) {
        if (i < 0 || i >= 180) {
            return 0;
        }
        float red = r[i];
        float green = g[i];
        float blue = b[i];
        String color = toHex((int) red, (int) green, (int) blue);
        LogUtils.e(TAG, color);
        return Color.parseColor(color);
    }


    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
                + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number) {
        String hexValue = Integer.toHexString(number);
        if (hexValue.length() == 1) {
            hexValue = "0" + hexValue;
        }
        return hexValue.toUpperCase();
    }


    public static void main(String[] args) {
        String colors = "e02020, e0221e, e1241d, e2261c, e3291b, e42b1a, e52d19, e62f18, e63217, e73416, e83615, e93814, ea3b13, eb3d12, ec3f11, ed4210, ed440e, ee460d, ef480c, f04b0b, f14d0a, f24f09, f35108, f35407, f45606, f55805, f65a04, f75d03, f85f02, f96101, fa6400, f96600, f96900, f96c00, f96e00, f97100, f97400, f97600, f97900, f97c00, f97f00, f88100, f88400, f88700, f88900, f88c00, f88f00, f89100, f89400, f89700, f89a00, f79c00, f79f00, f7a200, f7a400, f7a700, f7aa00, f7ac00, f7af00, f7b200, f7b500, f2b600, edb700, e9b800, e4b900, e0ba00, dbbb00, d6bc00, d2bd00, cdbe00, c9bf00, c4c000, bfc100, bbc200, b6c300, b2c400, adc500, a8c600, a4c700, 9fc800, 9bc900, 96ca00, 91cb00, 8dcc00, 88cd00, 84ce00, 7fcf00, 7ad000, 76d100, 71d200, 6dd400, 69d108, 65cf11, 62cd19, 5ecb22, 5ac82a, 57c633, 53c43b, 4fc244, 4cbf4c, 48bd55, 45bb5d, 41b966, 3db66e, 3ab477, 36b27f, 32b088, 2fae90, 2bab99, 27a9a1, 24a7aa, 20a5b2, 1da2bb, 19a0c3, 159ecc, 129cd4, 0e99dd, 0a97e5, 0795ee, 0393f6, 0091ff, 038dff, 068aff, 0987ff, 0d84ff, 1081ff, 137eff, 167bff, 1a78ff, 1d75ff, 2072ff, 236fff, 276cff, 2a69ff, 2d66ff, 3163ff, 3460ff, 375dff, 3a5aff, 3e57ff, 4154ff, 4451ff, 474eff, 4b4bff, 4e48ff, 5145ff, 5442ff, 583fff, 5b3cff, 5e39ff, 6236ff, 6435fd, 6734fc, 6a33fb, 6d33fa, 7032f9, 7231f8, 7530f7, 7830f6, 7b2ff5, 7e2ef4, 802df3, 832df2, 862cf1, 892bf0, 8c2bef, 8e2aee, 9129ed, 9428ec, 9728eb, 9a27ea, 9c26e9, 9f25e8, a225e7, a524e6, a823e5, aa22e4, ad22e3, b021e2, b320e1";
        String color[] = colors.trim().split(",");

        for (int i = 0; i < 180; i++) {
            int cur = i + 1;
            String curS = cur + "";
            if (curS.length() == 1) {
                curS = "00" + curS;
            }
            if (curS.length() == 2) {
                curS = "0" + curS;
            }
            System.out.println("<color name=\"color_rgb_" + curS + "\">#" + color[i] + "</color>");
        }

    }
}

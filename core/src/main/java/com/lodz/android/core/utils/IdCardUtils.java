package com.lodz.android.core.utils;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证帮助类
 * Created by zhouL on 2017/7/3.
 */

public class IdCardUtils {

    /** 每位加权因子 */
    private static final int POWER[] = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    /**
     * 验证18位身份编码是否合法
     * @param idCard 身份证号
     */
    public static boolean validateIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        if (idCard.length() != 18) {
            return false;
        }
        String code17 = idCard.substring(0, 17);// 取出前17位
        if (!isNum(code17)) {//前17位存在非数字
            return false;
        }
        // 获取校验位
        String validationCode = getCheckCode18(getPowerSum(converCharToInt(code17.toCharArray())));
        return !TextUtils.isEmpty(validationCode) && validationCode.equalsIgnoreCase(idCard.substring(17, 18));
    }

    /**
     * 获取身份证的校验位
     * @param idCard 17位待补全的身份证号
     */
    public static String getValidationCode(String idCard){
        if (TextUtils.isEmpty(idCard)) {
            return "";
        }
        if (idCard.length() != 17) {
            return "";
        }
        if (!isNum(idCard)) {//前17位存在非数字
            return "";
        }
        return getCheckCode18(getPowerSum(converCharToInt(idCard.toCharArray())));
    }

    /**
     * 校验文本是否是纯数字
     * @param str 文本
     */
    private static boolean isNum(String str) {
        return !TextUtils.isEmpty(str) && str.matches("^[0-9]*$");
    }

    /**
     * 将字符数组转换成数字数组
     * @param array 数组
     */
    private static int[] converCharToInt(char[] array) {
        int[] intArray = new int[array.length];
        try {
            for (int i = 0; i < array.length; i++) {
                intArray[i] = Integer.parseInt(String.valueOf(array[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intArray;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     * @param array 身份证前17位数组
     */
    private static int getPowerSum(int[] array) {
        int iSum = 0;
        if (POWER.length == array.length) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < POWER.length; j++) {
                    if (i == j) {
                        iSum = iSum + array[i] * POWER[j];
                    }
                }
            }
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     * @param sum 证件号与power的和值
     */
    private static String getCheckCode18(int sum) {
        String code = "";
        switch (sum % 11) {
            case 10:
                code = "2";
                break;
            case 9:
                code = "3";
                break;
            case 8:
                code = "4";
                break;
            case 7:
                code = "5";
                break;
            case 6:
                code = "6";
                break;
            case 5:
                code = "7";
                break;
            case 4:
                code = "8";
                break;
            case 3:
                code = "9";
                break;
            case 2:
                code = "x";
                break;
            case 1:
                code = "0";
                break;
            case 0:
                code = "1";
                break;
        }
        return code;
    }

    /**
     * 获取身份证的性别代码（0非身份证 1男 2女）
     * @param idCard 身份证号
     */
    public static int getSexCode(String idCard){
        if (!validateIdCard(idCard)){
            return 0;
        }
        String sex = idCard.substring(16, 17);
        return Integer.parseInt(sex) % 2 != 0 ? 1 : 2;
    }

    /**
     * 获取身份证的性别（男 女）
     * @param idCard 身份证号
     */
    public static String getSexStr(String idCard){
        int sexCode = getSexCode(idCard);
        if (sexCode == 1){
            return "男";
        }
        if (sexCode == 2){
            return "女";
        }
        return "";
    }

    /**
     * 获取出生年月
     * @param idCard 身份证号
     * @param dateFormat 日期格式
     */
    public static String getBirth(String idCard, String dateFormat) {
        if (!validateIdCard(idCard)){
            return "";
        }
        if (TextUtils.isEmpty(dateFormat)) {
            return "";
        }
        String original = idCard.substring(6, 14);
        return DateUtils.TYPE_5.equalsIgnoreCase(dateFormat) ? original : DateUtils.changeFormatString(DateUtils.TYPE_5, dateFormat, original);
    }

    /**
     * 获取出生年月（格式yyyyMMdd）
     * @param idCard 身份证号
     */
    public static String getBirth(String idCard) {
        return getBirth(idCard, DateUtils.TYPE_5);
    }

    /**
     * 获取年
     * @param idCard 身份证号
     */
    public static String getYear(String idCard) {
        if (!validateIdCard(idCard)){
            return "";
        }
        return idCard.substring(6, 10);
    }

    /**
     * 获取月
     * @param idCard 身份证号
     */
    public static String getMonth(String idCard) {
        if (!validateIdCard(idCard)){
            return "";
        }
        return idCard.substring(10, 12);
    }

    /**
     * 获取日
     * @param idCard 身份证号
     */
    public static String getDay(String idCard) {
        if (!validateIdCard(idCard)){
            return "";
        }
        return idCard.substring(12, 14);
    }

    /**
     * 获取年龄
     * @param idCard 身份证号
     */
    public static int getAge(String idCard){
        if (!validateIdCard(idCard)){
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year - Integer.valueOf(getYear(idCard));
    }

    /**
     * 获取户籍省份
     * @param idCard 身份证号
     */
    public static String getProvince(String idCard) {
        if (!validateIdCard(idCard)){
            return "";
        }
        String provinceNum = idCard.substring(0, 2);
        Map<String, String> map = getProvinceMap();
        return TextUtils.isEmpty(map.get(provinceNum)) ? "" : map.get(provinceNum);
    }

    /** 获取省份代码 */
    private static Map<String, String> getProvinceMap(){
        Map<String, String> map = new HashMap<>();
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");
        return map;
    }

//
//    /**
//     * 省、直辖市代码表
//     */
//    private static final String cityCode[] = {
//            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41",
//            "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71",
//            "81", "82", "91"
//    };
//
//
//    /**
//     * 第18位校检码
//     */
//    private static final String verifyCode[] = {
//            "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"
//    };
//    /**
//     * 最低年限
//     */
//    private static final int MIN = 1930;
//    private static Map<String, String> cityCodes = new HashMap<>();
//    /**
//     * 台湾身份首字母对应数字
//     */
//    private static Map<String, Integer> twFirstCode = new HashMap<>();
//    /**
//     * 香港身份首字母对应数字
//     */
//    private static Map<String, Integer> hkFirstCode = new HashMap<>();
//
//    static {
//        cityCodes.put("11", "北京");
//        cityCodes.put("12", "天津");
//        cityCodes.put("13", "河北");
//        cityCodes.put("14", "山西");
//        cityCodes.put("15", "内蒙古");
//        cityCodes.put("21", "辽宁");
//        cityCodes.put("22", "吉林");
//        cityCodes.put("23", "黑龙江");
//        cityCodes.put("31", "上海");
//        cityCodes.put("32", "江苏");
//        cityCodes.put("33", "浙江");
//        cityCodes.put("34", "安徽");
//        cityCodes.put("35", "福建");
//        cityCodes.put("36", "江西");
//        cityCodes.put("37", "山东");
//        cityCodes.put("41", "河南");
//        cityCodes.put("42", "湖北");
//        cityCodes.put("43", "湖南");
//        cityCodes.put("44", "广东");
//        cityCodes.put("45", "广西");
//        cityCodes.put("46", "海南");
//        cityCodes.put("50", "重庆");
//        cityCodes.put("51", "四川");
//        cityCodes.put("52", "贵州");
//        cityCodes.put("53", "云南");
//        cityCodes.put("54", "西藏");
//        cityCodes.put("61", "陕西");
//        cityCodes.put("62", "甘肃");
//        cityCodes.put("63", "青海");
//        cityCodes.put("64", "宁夏");
//        cityCodes.put("65", "新疆");
//        cityCodes.put("71", "台湾");
//        cityCodes.put("81", "香港");
//        cityCodes.put("82", "澳门");
//        cityCodes.put("91", "国外");
//        twFirstCode.put("A", 10);
//        twFirstCode.put("B", 11);
//        twFirstCode.put("C", 12);
//        twFirstCode.put("D", 13);
//        twFirstCode.put("E", 14);
//        twFirstCode.put("F", 15);
//        twFirstCode.put("G", 16);
//        twFirstCode.put("H", 17);
//        twFirstCode.put("J", 18);
//        twFirstCode.put("K", 19);
//        twFirstCode.put("L", 20);
//        twFirstCode.put("M", 21);
//        twFirstCode.put("N", 22);
//        twFirstCode.put("P", 23);
//        twFirstCode.put("Q", 24);
//        twFirstCode.put("R", 25);
//        twFirstCode.put("S", 26);
//        twFirstCode.put("T", 27);
//        twFirstCode.put("U", 28);
//        twFirstCode.put("V", 29);
//        twFirstCode.put("X", 30);
//        twFirstCode.put("Y", 31);
//        twFirstCode.put("W", 32);
//        twFirstCode.put("Z", 33);
//        twFirstCode.put("I", 34);
//        twFirstCode.put("O", 35);
//        hkFirstCode.put("A", 1);
//        hkFirstCode.put("B", 2);
//        hkFirstCode.put("C", 3);
//        hkFirstCode.put("R", 18);
//        hkFirstCode.put("U", 21);
//        hkFirstCode.put("Z", 26);
//        hkFirstCode.put("X", 24);
//        hkFirstCode.put("W", 23);
//        hkFirstCode.put("O", 15);
//        hkFirstCode.put("N", 14);
//    }
//
//    /**
//     * 将15位身份证号码转换为18位
//     *
//     * @param idCard 15位身份编码
//     * @return 18位身份编码
//     */
//    public static String conver15CardTo18(String idCard) {
//        String idCard18 = "";
//        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
//            return null;
//        }
//        if (isNum(idCard)) {
//            // 获取出生年月日
//            String birthday = idCard.substring(6, 12);
//            Date birthDate = null;
//            try {
//                birthDate = new SimpleDateFormat("yyMMdd", Locale.CHINA).parse(birthday);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Calendar cal = Calendar.getInstance();
//            if (birthDate != null)
//                cal.setTime(birthDate);
//            // 获取出生年(完全表现形式,如：2010)
//            String sYear = String.valueOf(cal.get(Calendar.YEAR));
//            idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
//            // 转换字符数组
//            char[] cArr = idCard18.toCharArray();
//            if (cArr != null) {
//                int[] iCard = converCharToInt(cArr);
//                int iSum17 = getPowerSum(iCard);
//                // 获取校验位
//                String sVal = getCheckCode18(iSum17);
//                if (sVal.length() > 0) {
//                    idCard18 += sVal;
//                } else {
//                    return null;
//                }
//            }
//        } else {
//            return null;
//        }
//        return idCard18;
//    }
//
//    /**
//     * 验证身份证是否合法
//     */
//    public static boolean validateCard(String idCard) {
//        String card = idCard.trim();
//        if (validateIdCard18(card)) {
//            return true;
//        }
//        if (validateIdCard15(card)) {
//            return true;
//        }
//        String[] cardval = validateIdCard10(card);
//        if (cardval != null) {
//            if (cardval[2].equals("true")) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 验证15位身份编码是否合法
//     *
//     * @param idCard 身份编码
//     * @return 是否合法
//     */
//    public static boolean validateIdCard15(String idCard) {
//        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
//            return false;
//        }
//        if (isNum(idCard)) {
//            String proCode = idCard.substring(0, 2);
//            if (cityCodes.get(proCode) == null) {
//                return false;
//            }
//            String birthCode = idCard.substring(6, 12);
//            Date birthDate = null;
//            try {
//                birthDate = new SimpleDateFormat("yy", Locale.CHINA).parse(birthCode.substring(0, 2));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Calendar cal = Calendar.getInstance();
//            if (birthDate != null)
//                cal.setTime(birthDate);
//            if (!valiDate(cal.get(Calendar.YEAR), Integer.valueOf(birthCode.substring(2, 4)),
//                    Integer.valueOf(birthCode.substring(4, 6)))) {
//                return false;
//            }
//        } else {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 验证10位身份编码是否合法
//     *
//     * @param idCard 身份编码
//     * @return 身份证信息数组
//     * <p>
//     * [0] - 台湾、澳门、香港 [1] - 性别(男M,女F,未知N) [2] - 是否合法(合法true,不合法false)
//     * 若不是身份证件号码则返回null
//     * </p>
//     */
//    public static String[] validateIdCard10(String idCard) {
//        String[] info = new String[3];
//        String card = idCard.replaceAll("[\\(|\\)]", "");
//        if (card.length() != 8 && card.length() != 9 && idCard.length() != 10) {
//            return null;
//        }
//        if (idCard.matches("^[a-zA-Z][0-9]{9}$")) { // 台湾
//            info[0] = "台湾";
//            System.out.println("11111");
//            String char2 = idCard.substring(1, 2);
//            if (char2.equals("1")) {
//                info[1] = "M";
//                System.out.println("MMMMMMM");
//            } else if (char2.equals("2")) {
//                info[1] = "F";
//                System.out.println("FFFFFFF");
//            } else {
//                info[1] = "N";
//                info[2] = "false";
//                System.out.println("NNNN");
//                return info;
//            }
//            info[2] = validateTWCard(idCard) ? "true" : "false";
//        } else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) { // 澳门
//            info[0] = "澳门";
//            info[1] = "N";
//        } else if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) { // 香港
//            info[0] = "香港";
//            info[1] = "N";
//            info[2] = validateHKCard(idCard) ? "true" : "false";
//        } else {
//            return null;
//        }
//        return info;
//    }
//
//    /**
//     * 验证台湾身份证号码
//     *
//     * @param idCard 身份证号码
//     * @return 验证码是否符合
//     */
//    public static boolean validateTWCard(String idCard) {
//        String start = idCard.substring(0, 1);
//        String mid = idCard.substring(1, 9);
//        String end = idCard.substring(9, 10);
//        Integer iStart = twFirstCode.get(start);
//        Integer sum = iStart / 10 + (iStart % 10) * 9;
//        char[] chars = mid.toCharArray();
//        Integer iflag = 8;
//        for (char c : chars) {
//            sum = sum + Integer.valueOf(c + "") * iflag;
//            iflag--;
//        }
//        return (sum % 10 == 0 ? 0 : (10 - sum % 10)) == Integer.valueOf(end);
//    }
//
//    /**
//     * 验证香港身份证号码(存在Bug，部份特殊身份证无法检查)
//     * <p>
//     * 身份证前2位为英文字符，如果只出现一个英文字符则表示第一位是空格，对应数字58 前2位英文字符A-Z分别对应数字10-35
//     * 最后一位校验码为0-9的数字加上字符"A"，"A"代表10
//     * </p>
//     * <p>
//     * 将身份证号码全部转换为数字，分别对应乘9-1相加的总和，整除11则证件号码有效
//     * </p>
//     *
//     * @param idCard 身份证号码
//     * @return 验证码是否符合
//     */
//    public static boolean validateHKCard(String idCard) {
//        String card = idCard.replaceAll("[\\(|\\)]", "");
//        Integer sum = 0;
//        if (card.length() == 9) {
//            sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 9
//                    + (Integer.valueOf(card.substring(1, 2).toUpperCase().toCharArray()[0]) - 55) * 8;
//            card = card.substring(1, 9);
//        } else {
//            sum = 522 + (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 8;
//        }
//        String mid = card.substring(1, 7);
//        String end = card.substring(7, 8);
//        char[] chars = mid.toCharArray();
//        Integer iflag = 7;
//        for (char c : chars) {
//            sum = sum + Integer.valueOf(c + "") * iflag;
//            iflag--;
//        }
//        if (end.toUpperCase().equals("A")) {
//            sum = sum + 10;
//        } else {
//            sum = sum + Integer.valueOf(end);
//        }
//        return (sum % 11 == 0);
//    }
//
//    /**
//     * 验证小于当前日期 是否有效
//     *
//     * @param iYear  待验证日期(年)
//     * @param iMonth 待验证日期(月 1-12)
//     * @param iDate  待验证日期(日)
//     * @return 是否有效
//     */
//    public static boolean valiDate(int iYear, int iMonth, int iDate) {
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int datePerMonth;
//        if (iYear < MIN || iYear >= year) {
//            return false;
//        }
//        if (iMonth < 1 || iMonth > 12) {
//            return false;
//        }
//        switch (iMonth) {
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                datePerMonth = 30;
//                break;
//            case 2:
//                boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0))
//                        && (iYear > MIN && iYear < year);
//                datePerMonth = dm ? 29 : 28;
//                break;
//            default:
//                datePerMonth = 31;
//        }
//        return (iDate >= 1) && (iDate <= datePerMonth);
//    }

}

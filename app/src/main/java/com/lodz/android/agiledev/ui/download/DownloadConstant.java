package com.lodz.android.agiledev.ui.download;

import com.lodz.android.agiledev.bean.AppInfoBean;
import com.lodz.android.agiledev.utils.file.FileManager;

import java.util.ArrayList;
import java.util.List;

import zlc.season.rxdownload3.core.Mission;

/**
 * 下载使用的常量
 * Created by zhouL on 2018/4/12.
 */
public class DownloadConstant {

    /** 应用名称 */
    public static final String[] APP_NAME = new String[]{
            "QQ",
            "支付宝",
            "UC浏览器",
            "腾讯视频",
            "今日头条",
            "手机淘宝",
            "腾讯新闻",
            "酷狗音乐",
            "高德地图",
            "微信",
            "京东商城",
            "美图秀秀",
            "新浪微博",
            "百思不得姐",
    };

    /** 应用图片 */
    public static final String[] APP_IMG_URL = new String[]{
            "http://p18.qhimg.com/dr/72__/t0111cb71dabfd83b21.png",
            "http://p18.qhimg.com/dr/72__/t01a16bcd9acd07d029.png",
            "http://p19.qhimg.com/dr/72__/t01195d02b486ef8ebe.png",
            "http://p18.qhimg.com/dr/72__/t01ed14e0ab1a768377.png",
            "http://p15.qhimg.com/dr/72__/t013d31024ae54d9c35.png",
            "http://p15.qhimg.com/dr/72__/t012db2bee5fb7dc814.png",
            "http://p16.qhimg.com/dr/72__/t011a0a5448d18044a0.png",
            "http://p18.qhimg.com/dr/72__/t019415e178f77a148f.png",
            "http://p18.qhimg.com/dr/72__/t01b404ce7cbbafae18.png",
            "http://p16.qhimg.com/dr/72__/t019621a78aec567334.png",
            "http://p15.qhimg.com/dr/72__/t01ae7badb0e3c4c2f3.png",
            "http://p15.qhimg.com/dr/72__/t011cd515c7c9390202.png",
            "https://hiphotos.bdimg.com/wisegame/wh%3D72%2C72/sign=edcec7707ccf3bc7e855c5ebe32c8d93/0823dd54564e92585bcebd939082d158cdbf4ed0.jpg",
            "https://hiphotos.bdimg.com/wisegame/wh%3D72%2C72/sign=877397f7d939b6004d9b07b0db7c0218/08f790529822720e9e4e76ee77cb0a46f21fab26.jpg",
    };

    /** 下载地址 */
    public static final String[] APP_DOWNLOAD_URL = new String[]{
            "http://shouji.360tpcdn.com/170918/a01da193400dd5ffd42811db28effd53/com.tencent.mobileqq_730.apk",
            "http://shouji.360tpcdn.com/170919/e7f5386759129f378731520a4c953213/com.eg.android.AlipayGphone_115.apk",
            "http://shouji.360tpcdn.com/170919/9f1c0f93a445d7d788519f38fdb3de77/com.UCMobile_704.apk",
            "http://shouji.360tpcdn.com/170918/f7aa8587561e4031553316ada312ab38/com.tencent.qqlive_13049.apk",
            "http://shouji.360tpcdn.com/170918/93d1695d87df5a0c0002058afc0361f1/com.ss.android.article.news_636.apk",
            "http://shouji.360tpcdn.com/170901/ec1eaad9d0108b30d8bd602da9954bb7/com.taobao.taobao_161.apk",
            "http://shouji.360tpcdn.com/170919/04fd8ec516c87571bf274537419b7651/com.tencent.news_5440.apk",
            "http://shouji.360tpcdn.com/170824/dfa7accaf99f4264c3f1a57c72b6b2de/com.kugou.android_8862.apk",
            "http://shouji.360tpcdn.com/170922/b9122b7a3c969511c4584fecd6e0b56a/com.autonavi.minimap_6180.apk",
            "http://shouji.360tpcdn.com/170821/9a7f5c1ee54e3c5bc84070c21e9b5b49/com.tencent.mm_1100.apk",
            "http://shouji.360tpcdn.com/170917/af50b75c9980cd6cba079052f4aa4e63/com.jingdong.app.mall_52563.apk",
            "http://shouji.360tpcdn.com/170919/1a4d1a0ca1255ae315c36394dd2b0865/com.mt.mtxx.mtxx_6860.apk",
            "http://p.gdown.baidu.com/e0023fb0f5c58e3333842eeb4ee422aa1fc0cc1103326e5f2093102d7e6c0d3ef83b99c0525a5429791def43eb691d86da466ffb57cd0b89bcf956a4cfec584008b73a3a2e5a2d24cef20436dee5b04480e6d9add2f14aa633eff4c1b35c588f377dba8261278660",
            "http://p.gdown.baidu.com/1a2164fa926e347963b7ad347f8260414c073a8b40b9404232e8f357a03a38e4c21cc8c8d80805461a6186400fbeaf809d99d83d6dbcf2a04d31b22dfd4565b92881bf285fbedb4b6e4982a6b4bdc01d77aa4bfa3b8a75811dc9fa9c67eafec1925c2662b578ec01b510a0aa012ef17ea38fc2fb652793d2a3b4c566f25cbab952651f73c5d0e634bfa1674b78e8181cd702e9bb029c1b3aa88a1ad7d476e1b8d60a421d67d85420c04129db6eca2a531d5c8c4dc04440e91dffa70ea66e903d4b01eeea6976e7707e30b120a0e94e7f7d5e21de5f134780",
    };

    /** 获取应用信息 */
    public static List<AppInfoBean> getMarketApps(){
        List<AppInfoBean> list = new ArrayList<>();
        for (int i = 0; i < APP_NAME.length; i++) {
            AppInfoBean bean = new AppInfoBean();
            bean.appName = APP_NAME[i];
            bean.imgUrl = APP_IMG_URL[i];
            bean.mission = new Mission(APP_DOWNLOAD_URL[i], APP_NAME[i] + ".apk", FileManager.getDownloadFolderPath());
            list.add(bean);
        }
        return list;
    }
}

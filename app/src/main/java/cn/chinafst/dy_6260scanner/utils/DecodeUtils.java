package cn.chinafst.dy_6260scanner.utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

public class DecodeUtils {

    public static byte[] EXIT_CARD = {0x7E, 0x11, 0x00, 0x01, 0x02, 0x14, 0x7E};
    public static byte[] ENTER_CARD = {0x7E, 0x11, 0x00, 0x01, 0x03, 0x15, 0x7E};
    public static byte[] SCAN_CARD = {0x7E, 0x11, 0x00, 0x01, 0x01, 0x13, 0x7E};
    public static byte[] READ_DATA = {0x7E, 0x15, 0x00, 0x00, 0x15, 0x7E};
    public static byte[] CARD_STATE = {0x7E, 0x13, 0x00, 0x00, 0x13, 0x7E };

    public static final int STATE_EXIT = 300;
    public static final int STATE_READ = 301;


    // 将GB2312转化为中文,如bdadcbd5→江苏
    public static String stringToGbk(String string) throws Exception {
        byte[] bytes = new byte[string.length() / 2];
        for (int j = 0; j < bytes.length; j++) {
            byte high = Byte.parseByte(string.substring(j * 2, j * 2 + 1), 16);
            byte low = Byte.parseByte(string.substring(j * 2 + 1, j * 2 + 2),
                    16);
            bytes[j] = (byte) (high << 4 | low);
        }
        String result = new String(bytes, "GBK");
        return result;
    }

    // 将中文转化为GB2312编码,并且以byte[]形式返回,如江苏→byte[]{0xbd,0xad,0xcb..}
    public static byte[] gbkToString(String str) throws Exception {
        return new String(str.getBytes("GBK"), "gb2312").getBytes("gb2312");
    }

    // 将十六进制的byte[]转化为string，如byte[]{0x7e，0x80,0x11,0x20}→7e801120
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    // 将字符数组byte[]变为16进制,每个byte有空格分开，例如7e 00 03
    public static StringBuilder byte2HexStr(byte[] data) {

        if (data != null && data.length > 0) {
            StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            return stringBuilder;
        }
        return null;
    }

    // 将byte[]数组转化为8、10等各种进制，例如byte[0x11,0x20]→4384，binary（byte[] 10)其中10代表十进制
    public static String bytesToAllHex(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    // 将String的十六进制转化为byte的十六进制，例如7e→new byte[]{0x7e}
    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < tmp.length / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    // 四位数，未满四位前面补0
    public static String AddZeroToFour(String string) {
        String newString = null;
        if (string.length() == 1) {
            newString = "000" + string;
        }
        if (string.length() == 2) {
            newString = "00" + string;
        }
        if (string.length() == 3) {
            newString = "0" + string;
        }
        if (string.length() == 4) {
            newString = string;
        }
        return newString;
    }

    //两位数,未满两位数前面补0
    public static String AddZeroToTwo(String string) {
        String newString = null;
        if (string.length() == 1) {
            newString = "0" + string;
        } else {
            newString = string;
        }
        return newString;

    }

    /**
     * 计算crc8值
     * arr 需要计算的数组
     * length 长度
     */
    public static byte crc8(byte[] arr, int length) {
        int i, j;
        byte crc = 0;
        for (j = 0; j < length; j++) {
            crc = (byte) (crc ^ arr[j]);
            for (i = 8; i > 0; i--) {
                if ((crc & 0x80) == 0x80) {
                    crc = (byte) ((crc << 1) ^ 0x31);
                } else {
                    crc = (byte) (crc << 1);
                }
            }
        }
        return (byte) crc;
    }

    /**
     * 计算crc8值
     *
     * @param tailByte 需要计算的数组
     * @return
     */
    public static byte crc8(byte[] tailByte) {
        // TODO Auto-generated method stub
        int i, j;
        byte crc = 0;

        for (j = 0; j < tailByte.length; j++) {
            crc = (byte) (crc ^ tailByte[j]);
            for (i = 8; i > 0; i--) {
                if ((crc & 0x80) == 0x80) {
                    crc = (byte) ((crc << 1) ^ 0x31);
                } else {
                    crc = (byte) (crc << 1);
                }
            }
        }
        return crc;
    }

    /**
     * 获取唯一的ID（新增数据需要）
     */
    public static String getOnlyID(Context context) {
        return getMD5(getUUID(context) + String.valueOf(new Random().nextInt()) + getDate_default());
    }
    /**
     * 返回设备相关ID,这里用蓝牙mac（唯一且不可变）
     */
    public static String getUUID(Context context) {

        String address = BluetoothAdapter.getDefaultAdapter().getAddress();
        return address;
    }

    /**
     * 获取现在时间（用于拼接文件名等）
     */
    public static String getDate_default() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyy-MM-dd_HH-mm-ss
        return sdf.format(new Date());
    }

    /**
     * 获取md5标签（跟服务器一致）
     */
    public static String getMD5(String key) {
        if (null == key)
            key = "";
        byte[] source = key.getBytes();
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toUpperCase();
    }
}

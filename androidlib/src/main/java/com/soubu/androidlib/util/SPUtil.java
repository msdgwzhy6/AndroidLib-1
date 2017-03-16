package com.soubu.androidlib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;

/**
 * 作者：余天然 on 16/9/15 上午11:47
 */
public class SPUtil {

    private final static String PREFERENCE_NAME = "SPUtil";
    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存储参数
     */
    public static <T> boolean putValue(String key, T value) {
        if (value == null) {
            return false;
        }
        SharedPreferences.Editor editor = preferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else {
            Log.e("print", "value=" + value.toString());
            throw new RuntimeException("不可存储非基本类型的参数");
        }
        return editor.commit();
    }

    /**
     * 读取参数
     */
    public static <T> T getValue(String key, T def) {
        if (def instanceof String) {
            return (T) preferences.getString(key, (String) def);
        } else if (def instanceof Integer) {
            Integer res = preferences.getInt(key, (Integer) def);
            return (T) res;
        } else if (def instanceof Long) {
            Long res = preferences.getLong(key, (Long) def);
            return (T) res;
        } else if (def instanceof Float) {
            Float res = preferences.getFloat(key, (Float) def);
            return (T) res;
        } else if (def instanceof Boolean) {
            Boolean res = preferences.getBoolean(key, (Boolean) def);
            return (T) res;
        } else {
            return null;
        }
    }

    /**
     * 存储对象
     */
    public static <T> boolean putObject(String key, T value) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(value);
            String objectVal = new String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, objectVal);
            return editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 读取对象
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        if (preferences.contains(key)) {
            String objectVal = preferences.getString(key, null);
            byte[] buffer = android.util.Base64.decode(objectVal, android.util.Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove( String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        return preferences.getAll();
    }
}

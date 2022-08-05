package javaWeb.test.service;


public interface RedisService {
    /**
     * set存数据
     * @param key: 键
     * @param value: 值
     * @return boolean
     **/
    boolean set(String key, String value);

    /**
     * get获取数据
     * @param key: 键
     * @return String key
     **/
    String get(String key);

    /**
     * expire 设置过期时间
     * @param key: 键
     * @param expire: 有效时间
     * @return boolean
     **/
    boolean expire(String key, long expire);

    /**
     * remove删除数据
     * @param key: 键
     * @return boolean
     **/
    boolean remove(String key);
}


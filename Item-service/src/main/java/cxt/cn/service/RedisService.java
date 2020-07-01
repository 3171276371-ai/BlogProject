package cxt.cn.service;

/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 *
 * @author chenxiaotian
 * @date 2018/8/7
 */
public interface RedisService {

    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 设置超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 自增操作
     * @param key 键
     * @param delta 自增步长
     * @return
     */
    Long increment(String key, long delta);

}

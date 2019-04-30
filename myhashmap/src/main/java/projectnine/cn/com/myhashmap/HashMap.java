package projectnine.cn.com.myhashmap;

/**
 * 手写hashmap  一些关键代码
 *
 * @param <K>
 * @param <V>
 */
public class HashMap<K, V> {

    /**
     * 散列table（桶）
     */
    public MapEntry[] table;

    /**
     * 存放MapEntry的个数
     */
    transient int size;

    /**
     * 扩容阀值
     */
    int threshold = 8;

    /**
     * 扩容因子  也有一些技巧 拿空间换时间
     */
    final float loadFactor = 0.75f;

    public class MapEntry<K, V> {
        K key;
        V value;
        MapEntry<K, V> next;
        int hash; //key的hash值

        public MapEntry(int hash, K key, V value, MapEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }


    public V put(K key, V value) {  // 如果用数组实现  复杂度为o(n)
        // 1 构建一个MapEntry
        // 2 for 循环数组有没有包含key 如果又替换（循环数组）
        // 3 没有包含  新增（扩容）
        if (table == null) {
            table = new MapEntry[8];
        }
        if (key == null) {//自行看hashmap源码
            return null;
        }
        //找到table位置
        int hash = hash(key);

        int index = getIndex(hash, table.length);

        // 判断是否存在该key
        for (MapEntry<K, V> e = table[index]; e != null; e = e.next) {   // 链表
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) { // 存在
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        //添加一个新的MapEntry
        addEntry(hash, key, value, index);
        return null;
    }

    /**
     * 添加一个新的MapEntry
     *
     * @param hash
     * @param key
     * @param value
     * @param index
     */
    private void addEntry(int hash, K key, V value, int index) {
        // hash_shift 16
        // 1 判断要不要扩容  mask_bits(value()>>hash_shift,hash_mask)
        // 1 hash值相等两个对象不一定相等
        // 2 hash值不相等两个对象一定不相等
        if (size >= threshold && table[index] != null) {
            resize(size << 1);  // 乘以2  扩容2倍
            // hash  不变
            // 重新计算 index
            index = getIndex(hash, table.length);
        }
        //添加
        createEntry(hash, key, value, index);

    }

    /**
     * 创建一个新的
     *
     * @param hash
     * @param key
     * @param value
     * @param index
     */
    private void createEntry(int hash, K key, V value, int index) {
        MapEntry<K, V> newEntry = new MapEntry<>(hash, key, value, table[index]);  // newEntry.next=table[index]  插到链表第一个位置
        table[index] = newEntry;
        size++;
    }

    /**
     * 扩容   index会变
     * newCapcity  table的长度
     *
     * @param
     */
    private void resize(int newCapcity) {
        MapEntry<K, V>[] newtable = new MapEntry[newCapcity];
        // 直接把之前的数组搬过来--> 不行  扩容之后 index会变 o(n)
        transfrom(newtable);
        table = newtable;
        threshold = (int) (newCapcity * loadFactor);   // 扩容因子*新table的长度
    }

    /**
     * 重新计算挪动散列
     *
     * @param newtable
     */
    private void transfrom(MapEntry<K, V>[] newtable) {
        int newCapcity = newtable.length;
        for (MapEntry<K, V> e : table) {
            while (null != e) {
                //从原来的数组中获取数据Entry 保证新的数组能链上
                MapEntry<K, V> next = e.next;
                int index = getIndex(e.hash, newCapcity);
                e.next = newtable[index];
                newtable[index] = e;
                e = next;
            }
        }
    }

    /**
     * 通过hash值找到table的index
     *
     * @param hash
     * @return
     */
    private int getIndex(int hash, int length) {
        // 2 的幂次，降低查询的时间复杂度  让散列更散
        return hash & length - 1;
    }

    /**
     * 二次hash
     *
     * @param key
     * @return
     */
    private int hash(K key) {   // 一般复写hashcode
        int h = 0;
        return (key == null) ? 0 : (h = key.hashCode() ^ (h >>> 16)); //无符号右移16位   除
    }


    public V get(K key) {
        if (key == null) {
            return null;
        }
        MapEntry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.value;
    }

    private MapEntry<K, V> getEntry(K key) {
        //找到table位置
        int hash = hash(key);
        int index = getIndex(hash, table.length);
        //判断是否存在该key
        for (MapEntry<K, V> e = table[index]; e != null; e = e.next) {   // 链表
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) { // 存在
                return e;
            }
        }
        return null;
    }


    public String getSize() {
        return "table  size  " + table.length + "     count  " + size;
    }


}

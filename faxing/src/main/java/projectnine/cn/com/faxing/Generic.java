package projectnine.cn.com.faxing;


/**
 * 泛型类
 */
public class Generic<T> {

    private T key;   //key这个成员变量的类型为T,T的类型由外部指定

    public Generic(T key) {
        this.key = key;
    }

    public T getKey(){
        return  key;
    }

}

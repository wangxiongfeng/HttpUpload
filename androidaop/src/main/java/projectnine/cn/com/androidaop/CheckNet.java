package projectnine.cn.com.androidaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wang on 2018/6/12.
 * 标记切点 注解
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
    String value();
}

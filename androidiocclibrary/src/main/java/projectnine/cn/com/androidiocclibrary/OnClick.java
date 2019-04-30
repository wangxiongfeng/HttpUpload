package projectnine.cn.com.androidiocclibrary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wang on 2018/4/18.
 * <p>
 * View注解的Annotation
 */

//@Target(ElementType.FIELD) 代表Annoaation的位置   FIELD属性上  TYPE 类上   ...
//@Retention(RetentionPolicy.CLASS)   什么时候生效     CLASS 编译时注解  RUNNING运行时  SOURSE..
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    int[] value();
}

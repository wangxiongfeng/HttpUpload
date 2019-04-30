package com.cn.styleinterface;

public class GenericTest {


    public class Generic<T> {

        private T key;

        public Generic(T key) {
            this.key = key;
        }

        public T getKey() {
            return key;
        }


        public <E> void show(E e) {
        }

        public <T> void show2(T e) {
        }   // 此T 与泛型类中的T不是同一个


    }


    /**
     * 泛型方法的基本介绍
     * 说明：
     * 1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     * 2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     * 3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     * 4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    private <T> T showKeyName(Generic<T> container) {
        T test = container.getKey();
        return test;
    }


    // 在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界，即在泛型声明的时候添加
    // public <T> T showKeyName(Generic<T extends Number> container)，编译器会报错："Unexpected bound"
    public <T extends Number> T showKeyName2(Generic<T> container) {
        System.out.println("container key :" + container.getKey());
        T test = container.getKey();
        return test;
    }


    private void showKeyName2() {
        System.out.print("  非静态方法  ");
    }


    //泛型方法和可变参数
    public static <T> void printMsg(T... args) {
        for (T arg : args) {
            System.out.print(arg + "  ");
        }
    }


    public static void main(String[] args) {

        printMsg("111", 222, "aaaa", "2323.4", 55.55);

        //null是为了告诉我们这里的引用没有指向任何地方或者说还未初始化，也就是说对象未创建
        ((GenericTest) null).printMsg("111", 222, "aaaa", "2323.4", 55.55);    //null  代表的是一个未知类型的引用

        GenericTest genericTest = new GenericTest();  //实例化后 才分配内存  才可访问
        genericTest.showKeyName2();

        genericTest.printMsg("111", 222, "aaaa", "2323.4", 55.55);
        GenericTest.printMsg("111", 222, "aaaa", "2323.4", 55.55);




        if(41==42.0){
            System.out.println("41==42.0");
        }else{
            System.out.println("41!=42.0");
        }

        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        System.out.println(c==d);  // 1  比较是否是同一个对象
        System.out.println(e==f);  // 0 比较是否是同一个对象
        System.out.println(c==(a+b));  // 1   比较内容
        System.out.println(c.equals(a+b));// 1   比较内容
        System.out.println(g==(a+b));  // 1  比较内容
        System.out.println(g.equals(a));//  0 比较内容
        System.out.println(g.equals(a+h));// 1 比较内容


    }


}

#include "projectnine_cn_com_androidndkdemo1_Java2C.h"
#include <string.h>
#include <jni.h>

//　这里JNIEXPORT和JNICALL都是JNI的关键字，表示此函数是要被JNI调用的
JNIEXPORT jstring JNICALL
Java_projectnine_cn_com_androidndkdemo1_Java2C_getFromC(JNIEnv *env, jobject instance){
    return (*env)->NewStringUTF(env,"hello i am from native c");
};


JNIEXPORT jstring JNICALL
Java_projectnine_cn_com_androidndkdemo1_Java2C_login(JNIEnv *env, jobject jobj, jstring jUserName, jstring jPSW, jint jcode){

    const char* resultMessage;
    jclass  jclazz;
    jclazz=(*env)->FindClass(env,"projectnine/cn/com/androidndkdemo1/Java2C"); // 获得java实例对象的引用

    jmethodID showMessage=(*env)->GetMethodID(env,jclazz,"showMessage","(Ljava/lang/String;)V"); // 通过实例对象获取实例方法ID

    jfieldID codeError=(*env)->GetFieldID(env,jclazz,"codeError","Ljava/lang/String;"); // 通过实例对象获取java成员变量ID
    jstring jcodeerror=(*env)->GetObjectField(env,jobj,codeError);  // 通过变量ID获取java成员变量

    jfieldID userNameError=(*env)->GetFieldID(env,jclazz,"userNameError","Ljava/lang/String;");
    jstring jusernameerror=(*env)->GetObjectField(env,jobj,userNameError);

    jfieldID loginSuccess=(*env)->GetStaticFieldID(env,jclazz,"loginSuccess","Ljava/lang/String;");
    jstring  jloginSuccess=(*env)->GetStaticObjectField(env,jclazz,loginSuccess);

    if(jcode == 1234){
        const char* cStr;
        jboolean isCopy;
        cStr = (*env)->GetStringUTFChars(env, jUserName,&isCopy);
        int result = strcmp(cStr, "wang");         //strcmp 比较字符串
        if(result == 0){
             resultMessage="成功了";
            (*env)->CallVoidMethod(env,jobj,showMessage,jloginSuccess);
        }else{
           resultMessage="用户名错误了";
           (*env)->CallVoidMethod(env,jobj,showMessage,jusernameerror);
        }
    }else{
        resultMessage="验证码错误了";
        (*env)->CallVoidMethod(env,jobj,showMessage,jcodeerror);
    }
    return (*env)->NewStringUTF(env, resultMessage);


};


JNIEXPORT jintArray JNICALL
Java_projectnine_cn_com_androidndkdemo1_Java2C_modifyValue(JNIEnv *env, jobject jobj, jintArray jArray){

      jboolean isCopy;
      //获取数组长度
      jsize size=(*env)->GetArrayLength(env,jArray);
      //获取数组
      jint* cArray=(*env)->GetIntArrayElements(env,jArray,&isCopy);
      //遍历数组   并将数组的每个元素值加100
      int i;
      for(i=0;i<size;i++){
          cArray[i]+=100;
      }
       //释放资源文件  0代表将内容复制到jArray中并释放掉cArray
       (*env)->ReleaseIntArrayElements(env,jArray,cArray,0);
       return jArray;

  };
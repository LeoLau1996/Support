package leo.work.support.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-08-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MVPSupport {

    public static <T> Class<T> getViewClass(Class<?> klass) {
        Type type = klass.getGenericSuperclass();
        if (type == null || !(type instanceof ParameterizedType))
            return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        if (types == null || types.length == 0)
            return null;
        return (Class<T>) types[0];
    }
}

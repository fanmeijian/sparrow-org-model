package cn.sparrowmini.org.model.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.PostRemove;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 因为hibernate的审计日志里，删除日志不含用户信息，因此做一个监听来记录删除人
 *
 * @author fansword
 */
public class DeleteLogListener {
    @PostRemove
    void delete(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
//		log.info("{} {}", fields.length, CurrentUser.get());
        for (Field field : fields) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(Id.class)
                        || annotation.annotationType().equals(EmbeddedId.class)) {
                    try {
                        Method method = object.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
//						System.out.println("{}({}) deleted by {}", object.getClass().getName() , method.invoke(object), CurrentUser.get());
                    } catch (IllegalArgumentException | NoSuchMethodException | SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

    }
}

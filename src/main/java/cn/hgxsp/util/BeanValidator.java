package cn.hgxsp.util;

import cn.hgxsp.exception.ParamException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * DESC：校验工具
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/24
 * Time : 14:00
 */
public class BeanValidator {

    //1.创建Validator工厂
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    //2.创建一个普通的校验方法  返回的map<有问题的字段 ， 原因>
    public static <T> Map<String , String > validate(T t , Class... groups){
        Validator validator = validatorFactory.getValidator() ;
        Set validateResult = validator.validate( t , groups );
        if(validateResult.isEmpty()){
            return Collections.emptyMap() ;
        }else{
            LinkedHashMap errors = Maps.newLinkedHashMap() ;
            Iterator iterator = validateResult.iterator() ;
            while (iterator.hasNext()){
                ConstraintViolation violation = (ConstraintViolation)iterator.next();
                errors.put(violation.getPropertyPath().toString() , violation.getMessage() );
            }
            return errors ;
        }
    }

    //3. 处理集合参数校验
    public static Map<String ,String > validateList(Collection<?> collection){
        //校验注解集合是否为空
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator() ;
        Map errors ;

        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap() ;
            }
            Object object = iterator.next() ;
            errors = validate(object , new Class[0]) ;
        }while (errors.isEmpty() );
        return errors ;
    }

    //4.创建一个公用的方法进行校验
    public static Map<String ,String>  validateObject(Object first , Object... objects){
        if(objects != null && objects.length > 0 ){
            return validateList(Lists.asList(first , objects)) ;
        }else{
            return validate(first,new Class[0]);
        }
    }

    //验证参数，后抛出异常，并返回给前端
    public static void check(Object Param) throws ParamException{
        Map<String, String> map = validateObject(Param);
        if(MapUtils.isNotEmpty(map)){
            throw new ParamException(map.toString()) ;
        }

    }


}


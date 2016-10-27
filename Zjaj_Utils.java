package cn.les.aj.qyxxgl.Utils;

import cn.les.aj.qyxxgl.qyjbxxgl.entity.LogQyjcxx;
import cn.les.aj.qyxxgl.qyjbxxgl.entity.Qyjcxx;
import cn.les.framework.annotation.Table;
import cn.les.yjzygl.entity.LogYjjyzj;
import cn.les.zjaj.entity.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;



/**
 * Created by yanfeng on 2016/10/18 0018.
 */
//镇江安监工具类
public class Zjaj_Utils {
    //将Zdzdxx放入LogQyjcxx
    public static LogQyjcxx Merge_qyjcxx(Qyjcxx qyjcxx){
        LogQyjcxx logQyjcxx=new LogQyjcxx();
        Method[] methods=Qyjcxx.class.getDeclaredMethods();
        Field[] fields=Qyjcxx.class.getDeclaredFields();
        Pattern pattern_get = Pattern.compile("^(get).*$");
        Pattern pattern_set = Pattern.compile("^(set).*$");
        Method method2;
        for(Method method:methods){
            if(pattern_get.matcher(method.getName()).matches()){
                try {
                    //如果有值才录入
                    if(method.invoke(qyjcxx)!=null) {
                            method2 = LogQyjcxx.class.getDeclaredMethod(method.getName().replaceFirst("g", "s"), method.invoke(qyjcxx).getClass());
                            method2.invoke(logQyjcxx, method.invoke(qyjcxx));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

        }
        return logQyjcxx;
    }
    //将map放入Bean
    public static <T> T mapToBean(Class<T> tClass,Map map){
        Field[] fields=tClass.getDeclaredFields();
        Object o= null;
        try {
            o = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Method method;
        for(Field field:fields){
            if(map.containsKey(field.getName())){
                String name=field.getName();
                Class c=field.getType();
                try {
                    method=tClass.getMethod("set"+upperCase(name),c);
                    try {
                        if(map.get(name)!=null&&map.get(name)!=""){
                            if(c==String.class){
                                map.put(name,String.valueOf(map.get(name)));
                            }else if(c.isInstance(map.get(name))){
                                map.put(name,c.cast(map.get(name)));
                            }
                            method.invoke(o,map.get(name));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
        }

        return (T)o;
    }
    //合并bean
    public static <T> T mergeBeans(T t,T... ts){
        for(T t1:ts){
            Field[] fields=t1.getClass().getDeclaredFields();
            Method methodG,methodS;
            for(Field field:fields){
                String name=field.getName();
                Class c=field.getType();
                try {
                    //获取t1的get方法和t的set方法
                    methodG=t1.getClass().getMethod("get"+upperCase(name),c);
                    methodS=t.getClass().getMethod("set"+upperCase(name),c);
                    try {
                        if(methodG.invoke(t1)!=null){
                            methodS.invoke(t,methodG.invoke(t1));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return  t;
    }
    public static <T> T mergeEntityToLog(T log,Object entity){
        Field[] fields=entity.getClass().getDeclaredFields();
        Method methodG,methodS;
        for(Field field:fields){
            String name=field.getName();
            Class c=field.getType();
            try {
                //获取t1的get方法和t的set方法
                methodG=entity.getClass().getMethod("get"+upperCase(name),c);
                methodS=log.getClass().getMethod("set"+upperCase(name),c);
                try {
                    if(methodG.invoke(entity)!=null){
                        methodS.invoke(log,methodG.invoke(entity));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return  log;
    }
    //按照log类写表格
    public static <T> void writeGridByLogClass(Class<T> log){
        Field[] fields=log.getDeclaredFields();
        String head="<div field=\"";
        String middle="\" width=\"90\" headerAlign=\"center\" allowSort=\"true\">";
        String end="</div>";
        for(Field field:fields){
            StringBuffer stringBuffer=new StringBuffer("");
            String name=field.getName();
            stringBuffer.append(head);
            stringBuffer.append(name);
            stringBuffer.append(middle);
            stringBuffer.append(name);
            stringBuffer.append(end);
            System.out.println(stringBuffer);
        }
    }
    //根据log编写查询sql
    public static <T> String writeSqlByLogClass(Class<T> log){
        Field[] fields=log.getDeclaredFields();
        String sql_name=entityName(log);
        StringBuffer stringBuffer=new StringBuffer("");
        for(Field field:fields){
            Class c=field.getType();
            String name=field.getName();
            if(Date.class==c||"operdate".equals(name)){
                if("".equals(stringBuffer.toString())){
                    stringBuffer.append("select ");
                    stringBuffer.append("to_char(");
                    stringBuffer.append(name);
                    stringBuffer.append(",'yyyy-MM-dd') as "+name);
                }else{
                    stringBuffer.append(",to_char(");
                    stringBuffer.append(name);
                    stringBuffer.append(",'yyyy-MM-dd') as "+name);
                }
            }else{
                if("".equals(stringBuffer.toString())){
                    stringBuffer.append("select ");
                    stringBuffer.append(name);
                }else{
                    stringBuffer.append(","+name);
                }
            }
        }
        stringBuffer.append(" from "+sql_name);
        return stringBuffer.toString();
    }
    //根据entity表名
    public static <T> String entityName(Class<T> entity){
        Table anno = entity.getAnnotation(Table.class);
        if(anno != null){
            Method[] met = anno.annotationType().getDeclaredMethods();
            for(Method me : met ){
                if(!me.isAccessible()){
                    me.setAccessible(true);
                }
                try {
                   if("name".equals(me.getName())){
                        return (String)me.invoke(anno, null);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }
    public static String upperCase(String s){
        char[] cs=s.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
    public static Date DateFormat(String date,String formate){
        SimpleDateFormat format = new SimpleDateFormat(formate);
            try {
                Date result=format.parse(date);
                return  result;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return null;
    }
    public static String DateToString(Date date,String formate){
        SimpleDateFormat format = new SimpleDateFormat(formate);
        return format.format(date);
    }
    public static void main(String[] args){
        writeGridByLogClass(LogYjjyzj.class);
//        entityName(LogQyjcxx.class);
//        System.out.println(writeSqlByLogClass(LogQyjcxx.class));
    }

}

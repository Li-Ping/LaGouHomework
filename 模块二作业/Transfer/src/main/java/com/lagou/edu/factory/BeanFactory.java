package com.lagou.edu.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:LiPing
 * @description：工厂类，使用反射技术生产对象
 * @date:Created in 12:33 2020/1/4 0004
 */
public class BeanFactory {

    /**
     * 读取解析xml文件，通过反射技术实例化对象并存储
     * 对外提供获取实例对象的接口
     */

    // 存储实例化后的对象
    private static Map<String,Object> map = new HashMap<>();

    /**
     * 应用启动后，立即执行，读取解析xml文件，通过反射技术实例化对象并存储
     */
    /*static {
        // 加载xml文件（类加载器以流的形式加载）
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 解析xml文件
        // 读取器
        SAXReader saxReader = new SAXReader();
        try {
            // 读取流到文档中
            Document document = saxReader.read(resourceAsStream);
            // 获取根节点
            Element rootElement = document.getRootElement();
            // 获取根节点下的所有bean标签
            List<Element> elementList = rootElement.selectNodes("//bean");
            for (Element element : elementList) {
                // 获取bean标签的id、class属性
                String id = element.attributeValue("id");
                String beanClass = element.attributeValue("class");
                // 通过反射技术实例化对象
                Class<?> aClass = Class.forName(beanClass);
                //实例化后的对象
                Object o = aClass.newInstance();
                // 存储实例化对象
                map.put(id, o);
            }

            // 实例化完成之后维护对象的依赖关系，检查哪些对象需要传值进入，根据它的配置，我们传入相应的值
            // 有property子元素的bean就有传值需求
            List<Element> propertyList = rootElement.selectNodes("//property");
            // 解析property，获取父元素
            for (int i = 0; i < propertyList.size(); i++) {
                Element element = propertyList.get(i);   //<property name="AccountDao" ref="accountDao"></property>
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                // 找到当前需要被处理依赖关系的bean
                Element parent = element.getParent();

                // 调用父元素对象的反射功能
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);
                // 遍历父对象中的所有方法，找到"set" + name
                Method[] methods = parentObject.getClass().getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    // 该方法就是 setAccountDao(AccountDao accountDao)
                    if (method.getName().equalsIgnoreCase("set" + name)) {
                        method.invoke(parentObject, map.get(ref));
                    }
                }

                // 把处理之后的parentObject重新放到map中
                map.put(parentId, parentObject);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }*/
        /**
     * 对外提供获取实例对象的接口
     * @param id
     * @return
     */
    public static Object getBean(String id){
        return  map.get(id);
    }
}

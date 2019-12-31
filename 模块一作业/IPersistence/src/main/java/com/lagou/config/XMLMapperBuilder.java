package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseMapper(InputStream in) throws DocumentException {
        Document document = new SAXReader().read(in);
        Element root = document.getRootElement();
        String namespace = root.attributeValue("namespace");
        List<Element> list = root.selectNodes("//select");
        list.addAll(root.selectNodes("//insert"));
        list.addAll(root.selectNodes("//update"));
        list.addAll(root.selectNodes("//delete"));
        for (Element element:list){
            String id = element.attributeValue("id");
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(element.attributeValue("resultType"));
            mappedStatement.setParameterType(element.attributeValue("parameterType"));
            mappedStatement.setSql(element.getTextTrim());
            configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
        }
    }
}

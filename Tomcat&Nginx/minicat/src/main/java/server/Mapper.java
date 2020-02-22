package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:13 2020/2/21 0021
 */
public class Mapper {

    private Map<String,Host> hostMap = new HashMap<String,Host>();

    public static class Host{

        private String appBase;

        private Map<String,Context> contextMap = new HashMap<String,Context>();

        public Host(){

        }

        public Host(String appBase,Map<String,Context> contextMap){
            this.appBase = appBase;
            this.contextMap = contextMap;
        }

        public String getAppBase() {
            return appBase;
        }

        public void setAppBase(String appBase) {
            this.appBase = appBase;
        }

        public Map<String, Context> getContextMap() {
            return contextMap;
        }

        public void setContextMap(Map<String, Context> contextMap) {
            this.contextMap = contextMap;
        }
    }

    public static class Context{

        private Map<String,Wrapper> wrapperMap = new HashMap<String,Wrapper>();

        private String docBase;

        public Context(){

        }

        public Context(String docBase,Map<String,Wrapper> wrapperMap){
            this.docBase = docBase;
            this.wrapperMap = wrapperMap;
        }

        public Map<String, Wrapper> getWrapperMap() {
            return wrapperMap;
        }

        public void setWrapperMap(Map<String, Wrapper> wrapperMap) {
            this.wrapperMap = wrapperMap;
        }

        public String getDocBase() {
            return docBase;
        }

        public void setDocBase(String docBase) {
            this.docBase = docBase;
        }
    }

    public static class Wrapper{

        private HttpServlet httpServlet;

        public Wrapper(){

        }

        public Wrapper(HttpServlet httpServlet){
            this.httpServlet = httpServlet;
        }

        public HttpServlet getHttpServlet() {
            return httpServlet;
        }

        public void setHttpServlet(HttpServlet httpServlet) {
            this.httpServlet = httpServlet;
        }
    }

    public Mapper(){

    }
    public Mapper(Map<String,Host> hostMap){
        this.hostMap = hostMap;
    }

    public Map<String, Host> getHostMap() {
        return hostMap;
    }

    public void setHostMap(Map<String, Host> hostMap) {
        this.hostMap = hostMap;
    }
}

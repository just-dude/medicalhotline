package common.exception;

import java.util.*;

/**
 * Created by SuslovAI on 14.08.2017.
 */
class ContextData {

    private static String DATA_WITHOUT_NAME = "dataWithoutName1";

    private String contextName;
    private Map<String, Object> dataMap = new HashMap();
    private List<Object> dataList = new ArrayList();

    public ContextData(String contextName) {
        this.contextName = contextName;
    }

    public ContextData(String contextName, List<Object> data) {
        this.contextName = contextName;
        this.dataList = data;
    }

    public ContextData(String contextName, Map<String, Object> data) {
        this.contextName = contextName;
        this.dataMap = data;
    }

    public void addData(Object data) {
        this.dataList.add(data);
    }

    public void addData(List<Object> data) {
        this.dataList.addAll(data);
    }

    public void addData(String name, Object data) {
        this.dataMap.put(name, data);
    }

    public String getname() {
        return contextName;
    }

    public void setname(String name) {
        this.contextName = name;
    }

    public Map getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        if (this.dataList.isEmpty() && this.dataMap.isEmpty()) {
            return "";
        }
        String dataStr = "";
        if (!this.dataList.isEmpty()) {
            dataStr = Arrays.toString(this.dataList.toArray());
        }
        if (!this.dataMap.isEmpty()) {
            if (dataStr != "") {
                dataStr += ", ";
            }
            dataStr += this.dataMap.toString();
        }
        return String.format("Context '%s' data: { %s }", this.contextName, dataStr);
    }
}

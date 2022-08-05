package javaWeb.test.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 自定义服务器响应数据结构
 *              200：成功
 *              500：错误，错误信息在Msg字段中
 *              501：bean验证错误，以Map形式返回
 *              555:异常抛出信息
 * @author： 庄婉如
 * @date: 2022/6/27 18:05
 * @Version:v1.0
 */
@Data
public class JSONResult implements Serializable {
    //定义Jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    //响应业务状态码
    private Integer code = 0;
    //响应消息
    private String msg = null;
    //响应数据
    private Object data = null;
    @Override
    public String toString() {
        return "[state=" + code + ", msg=" + msg + ", data" + data.toString()+ "]";
    }
    public JSONResult(Integer status, String msg, Object data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public JSONResult() {
        this.code = 200;
        this.msg = null;
        this.data = null;
    }

    public JSONResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }

    //判断状态是否成功
    public boolean isok() {
        return this.code == 200;
    }

    //
    public static JSONResult build(Integer status, String msg, Object data) {
        return new JSONResult(status, msg, data);
    }

    //响应正确，返回数据
    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    //响应正确，不需要返回数据
    public static JSONResult ok() {
        return new JSONResult();
    }

    //响应错误状态和详细信息,不返回数据
    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, "");
    }

    //响应错误状态和详细信息,返回数据
    public static JSONResult errorMap(Object data) {
        return new JSONResult(500, "error", data);
    }

    //响应错误状态和详细信息,返回数据
    public static JSONResult errorTokenMsg(String msg) {
        return new JSONResult(500, msg, "");
    }

    //
    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, "");
    }

/**
 * @Description: 将json结果集合转换成JSONResult对象
 *              需要转换的对象是一个类
 * @param jsonData
 * @param classzz
 * @return
 *
 * @author 庄婉如
 * @date 2022/6/27 18:27
 */
    public static JSONResult formatToPojo(String jsonData,Class<?> classzz){
        try{
            //如果是空
            if(classzz == null){
                return MAPPER.readValue(jsonData,JSONResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            //如果类非空
            if(classzz != null){
                if(data.isObject()){
                    obj = MAPPER.readValue(data.traverse(),classzz);
                }
                else if(data.isTextual()){
                    obj = MAPPER.readValue(data.asText(),classzz);
                }
            }
            return build(jsonNode.get("status").intValue(),
                    jsonNode.get("msg").asText(),obj);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * @Description: 没有Obj对象的转换
     * @param json
     * @return
     *
     * @author 庄婉如
     * @date 2022/6/27 18:41
     */
    public static JSONResult format(String json){
        try{
            return MAPPER.readValue(json,JSONResult.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: Obj是集合的转换，对象是List
     * @param jsonData
     * @param classzz
     * @return
     *
     * @author 庄婉如
     * @date 2022/6/27 19:14
     */
    public static JSONResult formatToList(String jsonData,Class<?> classzz){
        try{
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if(data.isArray()&&data.size()>0){
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class,classzz));
            }
            return build(jsonNode.get("status").intValue(),
                    jsonNode.get("msg").asText(),obj);

        }catch(Exception e){
            return null;
        }
    }
    public String getOK(){
        return "OK";
    }
    public void setOK(){
        this.msg="OK";
    }

}
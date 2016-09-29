package tips.volleytest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类测试以下4种Volley请求：
 * <p>
 * 1. StringRequest
 * <p>
 * 2. JsonObjectRequest
 * <p>
 * 3. JsonArrayObject
 * <p>
 * 4. MyVolleyRequest
 */
public class VolleyRequestDemo {

    public static final String VOLLEY_TAG = "tag_volley_request";

    public static final String JUHE_APPKEY = "effd958a513778eadd21f8d29a675644";
    public static final String JUHE_API_URL = "http://v.juhe.cn/postcode/query";

    /*
    * 聚合数据查询邮编对应的地址的url，用于测试
    * 请求示例：http://v.juhe.cn/postcode/query?postcode=邮编&key=申请的KEY
    */
    private String postcode = "210044";
    private String url_GET = JUHE_API_URL + "?postcode=" + postcode + "&key=" + JUHE_APPKEY;


    //volleyStringRequestDemo_GET()方法有详细的注释，其他方法对照此方法

    /**
     * Volley的StringRequest使用示例
     * HTTP method : GET
     */
    public void volleyStringRequestDemo_GET() {

        //Volley request，参数：请求方式，请求的URL，请求成功的回调，请求失败的回调
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_GET, new Response.Listener<String>() {
            /**
             * 请求成功的回调
             * @param response 请求返回的数据
             */
            @Override
            public void onResponse(String response) {
                // TODO: 处理返回结果
                Log.i("### onResponse", "GET_StringRequest:" + response);
            }
        }, new Response.ErrorListener() {
            /**
             * 请求失败的回调
             * @param error VolleyError
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
                Log.e("### onErrorResponse", "GET_StringRequest:" + error.toString());
            }
        });

        //为request设置tag，通过该tag在全局队列中访问request
        stringRequest.setTag(VOLLEY_TAG);//StringRequestTest_GET

        //将request加入全局队列
        MyApplication.getRequestQueue().add(stringRequest);
    }

    /**
     * Volley的JsonObjectRequest使用示例
     * HTTP method : GET
     * 内部注释和方法volleyStringRequestDemo_GET()相同
     */
    public void volleyJsonObjectRequestDemo_GET() {

        /*
        * 第三个参数：request的参数(POST)，传入null表示没有需要传递的参数
        * 此处为GET方式传输，参数已经包含在URL中
        * */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_GET, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO: 处理返回结果
                Log.i("### onResponse", "GET_JsonObjectRequest:" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
                Log.e("### onErrorResponse", "GET_JsonObjectRequest:" + error.toString());
            }
        });

        jsonObjectRequest.setTag(VOLLEY_TAG);//JsonObjectRequestTest_GET

        MyApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * Volley的JsonArrayRequest使用示例
     * HTTP method : GET
     * 内部注释和方法volleyStringRequestDemo_GET()相同
     */
    public void volleyJsonArrayRequestDemo_GET() {

        /*
        * 第三个参数：request的参数(POST)，传入null表示没有需要传递的参数
        * 此处为GET方式传输，参数已经包含在URL中
        * */
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_GET, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // TODO: 处理返回结果
                Log.i("### onResponse", "GET_JsonArrayRequest:" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
                Log.e("### onErrorResponse", "GET_JsonArrayRequest:" + error.toString());
            }
        });

        jsonArrayRequest.setTag(VOLLEY_TAG);//JsonArrayRequestTest_GET

        MyApplication.getRequestQueue().add(jsonArrayRequest);
    }

    /**
     * Volley的StringRequest使用示例
     * HTTP method : POST
     * 内部注释和方法volleyStringRequestDemo_GET()相同
     */
    public void volleyStringRequestDome_POST() {

        String url = JUHE_API_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // TODO: 处理返回结果
                Log.i("### onResponse", "POST_StringRequest:" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
                Log.e("### onErrorResponse", "POST_StringRequest:" + error.toString());
            }
        }) {
            /**
             * 返回含有POST或PUT请求的参数的Map
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> paramMap = new HashMap<>();
                // TODO: 处理POST参数
                paramMap.put("postcode", postcode);
                paramMap.put("key", JUHE_APPKEY);

                return paramMap;
            }
        };

        stringRequest.setTag(VOLLEY_TAG);//StringRequest_POST

        MyApplication.getRequestQueue().add(stringRequest);
    }

    /**
     * Volley的JsonObjectRequest使用示例
     * HTTP method : POST
     * 内部注释和方法volleyStringRequestDemo_GET()相同
     */
    public void volleyJsonObjectRequestDome_POST() {

        String url = JUHE_API_URL;
        Map<String, String> paramMap = new HashMap<>();
        // TODO: 处理POST参数
        paramMap.put("postcode", postcode);
        paramMap.put("key", JUHE_APPKEY);
        //产生JsonObject类型的参数
        JSONObject jsonParam = new JSONObject(paramMap);

        // TODO: 错误的请求KEY!
        Log.i("DEBUG ###", jsonParam.toString());
        //DEBUG ###: {"postcode":"210044","key":"effd958a513778eadd21f8d29a675644"}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO: 处理返回结果
                Log.i("### onResponse", "POST_JsonObjectRequest:" + response.toString());
                //POST_JsonObjectRequest:{"error_code":10001,"result":null,"reason":"错误的请求KEY!","resultcode":"101"}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
                Log.e("### onErrorResponse", "POST_JsonObjectRequest:" + error.toString());
            }
        });

        jsonObjectRequest.setTag(VOLLEY_TAG);//JsonObjectRequestTest_POST

        MyApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * Volley的JsonArrayRequest使用示例
     * HTTP method : POST
     * 内部注释和方法volleyStringRequestDemo_GET()相同
     */
    public void volleyJsonArrayRequestDemo_POST() {

        String url = JUHE_API_URL;
        Map<String, String> paramMap = new HashMap<>();
        // TODO: 16-9-28 处理POST参数
        paramMap.put("postcode", postcode);
        paramMap.put("key", JUHE_APPKEY);
        //产生JSON类型的参数
        JSONObject jsonParam = new JSONObject(paramMap);
        JSONArray jsonParamArray = new JSONArray();
        jsonParamArray.put(jsonParam);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonParamArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // TODO: 处理返回结果
                Log.i("### onResponse", "POST_JsonArrayRequest:" + response.toString());
                //POST_JsonObjectRequest:{"error_code":10001,"result":null,"reason":"错误的请求KEY!","resultcode":"101"}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
                Log.e("### onErrorResponse", "POST_JsonArrayRequest:" + error.toString());
            }
        });

        jsonArrayRequest.setTag(VOLLEY_TAG);//JsonArrayRequestTest_POST

        MyApplication.getRequestQueue().add(jsonArrayRequest);
    }

    /**
     * Volley的简单回调封装测试(GET)
     */
    public void myVolleyRequestDemo_GET(Context context) {

        MyVolleyRequest myVolleyRequest = new MyVolleyRequest(context, new MyVolleyRequest.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.i("### onSuccess", "GET_MyVolleyRequest" + response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        myVolleyRequest.requestGet(url_GET, "my_get_" + VOLLEY_TAG);
    }

    /**
     * Volley的简单回调封装测试(POST)
     */
    public void myVolleyRequestDemo_POST(Context context) {
        MyVolleyRequest myVolleyRequest = new MyVolleyRequest(context, new MyVolleyRequest.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.i("### onSuccess", "POST_MyVolleyRequest" + response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        Map<String, String> param = new HashMap<>();
        param.put("postcode", postcode);
        param.put("key", JUHE_APPKEY);

        myVolleyRequest.requestPost(JUHE_API_URL, "my_post_" + VOLLEY_TAG, param);
    }

}

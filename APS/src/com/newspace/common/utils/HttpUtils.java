package com.newspace.common.utils;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpProtocolParams;

import com.newspace.common.coder.Coder;

/**
 * 通过apache httpclient向指定url发送http请求的工具类
 * @author huqili
 * @since jdk1.6 apache httpcore 4.2.2
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class HttpUtils {

    /**
     * 发送post请求
     * @param url 请求URL
     * @param params 请求参数
     * @param charset 响应的字符串编码
     * @return 远程服务器的响应
     * @throws Exception
     */
    public static HttpEntity post(String url, Map<String, String> params) throws Exception {

        HttpPost post = new HttpPost(url);
        if (ArrayUtils.hasObject(params)) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
            post.setEntity(entity);
        }
        return request(post);
    }

    /**
     * 发送json形式的post请求参数
     * @param url 请求URL
     * @param jsonString 请求的json字符串
     * @return 远程服务器的响应
     * @throws Exception
     */
    public static HttpEntity postJson(String url, String jsonString, String charset) throws Exception {

        return post(url, jsonString.getBytes(charset));
    }

    /**
     * 发送post请求
     * @param url 请求URL
     * @param bytes post请求的数据
     * @param params 请求参数
     * @param charset 字符编码
     * @return 远程服务器的响应
     * @throws Exception
     */
    public static HttpEntity post(String url, byte[] bytes) throws Exception {

        HttpPost post = new HttpPost(url);
        ByteArrayEntity entity = new ByteArrayEntity(bytes);
        post.setEntity(entity);
        return request(post);
    }

    /**
     * 发送get请求
     * @param url 请求的URL
     * @return
     * @throws Exception
     */
    public static HttpEntity get(String url) throws Exception {

        HttpGet get = new HttpGet(url);
        return request(get);
    }

    /**
     * 发送请求
     * @param request 请求对象
     * @return 远程服务器的响应
     * @throws Exception
     */
    public static HttpEntity request(HttpUriRequest request) throws Exception {

        HttpClient client = getClient();
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        return entity;
    }

    /**
     * 得到一个HttpClient对象
     * @return HttpClient对象
     * @throws Exception
     */
	private static HttpClient getClient() throws Exception {

        HttpClient client = new DefaultHttpClient();
        String agent = "Mozilla/5.0 (Windows; U; Windows NT 5.1;" + " zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9";
        HttpProtocolParams.setUserAgent(client.getParams(), agent);
        Scheme scheme = getSSLScheme();
        client.getConnectionManager().getSchemeRegistry().register(scheme);
        return client;
    }

    /**
     * 得到一个发送SSL的Scheme
     * @return SSL的Scheme
     * @throws Exception
     */
	private static Scheme getSSLScheme() throws Exception {

        TrustManager trustManager = getTrustManager();
        SSLContext context = SSLContext.getInstance(SSLSocketFactory.TLS);
        context.init(null, new TrustManager[] { trustManager }, null);
        SSLSocketFactory factory = new SSLSocketFactory(context, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", 443, factory);
        return scheme;
    }

    /**
     * 得到TrustManager对象
     * @return TrustManager对象
     */
    private static TrustManager getTrustManager() {

        TrustManager manager = new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {

                return null;
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }
        };
        return manager;
    }

    /**
     * 替换http请求的url
     * @param url 替换后的url
     * @return
     */
    public static String parseUrl(String url) {

        url = url.replaceAll("^(http:(/)*)+", "#{http}");
        url = url.replace('\\', '/');
        url = url.replaceAll("/+", "/");
        url = url.replaceFirst("#\\{http\\}", "http://");
        return url;
    }

    /**
     * 判断一个url是否是http请求的url（是否包含http请求的schema）
     * @param url http请求的url
     * @return
     */
    public static boolean isHttpUrl(String url) {

        url = parseUrl(url);
        return url.matches("^((http)|(https))://.*$");
    }
    
    
    public static void main(String[] args) throws Exception {
    	try{

    		//贵州广电异步通知接口
//			String url = "http://10.1.1.62:8080/APS/guizhouNotify.do";
//			String json = "version=1&clientcode=SZATT001&clientpwd=6cb2d0b736f3841658074f9b854e8c54&citycode=5201&servicecode=&requestid=SZATT00120160504974b9659&requestContent=%7B%22bankaccno%22%3A%22%22%2C%22orderNo%22%3A%22100000271322%22%2C%22paycode%22%3A%22040400%22%2C%22payreqid%22%3A%22201605040001896383%22%2C%22payway%22%3A%2233%22%2C%22status%22%3A%222%22%7D";
    			
    		//QQ物联平台短信远程绑定接口（调用此接口下给绑定的号码下发短信）
    		String url = "https://yun.tim.qq.com/v4/SmartDeviceRemoteBind/test?apn=0";
    		String lisence = "3046022100CCD7A0475E8A5AF93688000EB86FD93F2C3B850E60061131933781F64B078CD2022100C6019C62D5B89D092190BFD87E0D8A7859752A7973EA56882A90E84A01F4DD57";
    		String license = lisence.toUpperCase();
    		String md5 = Coder.getHexStringByEncryptMD5(license);
    		String json = "{\"din\":\"\",\"pid\":\"1700004752\",\"sn\":\"9CC03B94AAF24852\",\"lisence\":\""+license+"\",\"md5sum\":\""+md5+"\",\"dstPhoneNum\":\"18681523796\",\"isVerify\":\"\",\"Sig\":\"\",\"verify\":\"\"}";
    		System.out.println("json:"+json);
    		
    		
            HttpEntity entity = HttpUtils.post(url, json.getBytes("UTF-8"));
            System.out.println(entity.getContentEncoding());
            InputStream is = entity.getContent();
            StringBuilder str = new StringBuilder();
            byte[] bs = new byte[1024];
            while (true) {
                int i = is.read(bs);
                if (i < 0) {
                    break;
                }
                str.append(new String(bs, 0, i, "utf-8"));
            }
            System.out.println(str);
            System.out.println("测试");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0 ;i < 2000; i++){
            Runnable run = new Runnable() {
                
                @Override
                public void run() {
                    payType();
                    
                }
            };
            service.execute(run);
        }
        service.shutdown();
        
    }*/

    //    private static void payType(){
    //        try {
    //    
    //            String url = "http://10.1.1.62:8080/atetpayplatform/paymethod.do";
    //            PayMethodReqVo vo = new PayMethodReqVo();
    //            vo.setDeviceId("20140820151141185131");
    //            
    //            String json = JsonUtils.toJson(vo);
    //            
    //            HttpEntity entity = HttpUtils.post(url, json.getBytes("UTF-8"));
    //            System.out.println(entity.getContentEncoding());
    //            InputStream is = entity.getContent();
    //            StringBuilder str = new StringBuilder();
    //            byte[] bs = new byte[1024];
    //            while (true)
    //            {
    //                int i = is.read(bs);
    //                if (i < 0)
    //                {
    //                    break;
    //                }
    //                str.append(new String(bs, 0, i, "utf-8"));
    //            }
    //            System.out.println(str);
    //            System.out.println("测试");
    //        }
    //        catch (Exception e)
    //        {
    //            e.printStackTrace();
    //        }finally{
    //            
    //        }
    //    }
}

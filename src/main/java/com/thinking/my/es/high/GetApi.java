package com.thinking.my.es.high;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2021/2/20 5:52 下午
 **/
public class GetApi {
    static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9201, "http")
            ));
    public static void getApi(){
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            handlerGetResponse(getResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getAsyncApi(){
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                handlerGetResponse(getResponse);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        };
        Cancellable cancellable = client.getAsync(getRequest, RequestOptions.DEFAULT,listener);
    }

    public static void handlerGetResponse(GetResponse getResponse) {
        String index = getResponse.getIndex();
        System.out.println("index:"+index);
        String id = getResponse.getId();
        System.out.println("id:"+id);
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            System.out.println("version:"+version);
            String sourceAsString = getResponse.getSourceAsString();
            System.out.println("sourceAsString:"+sourceAsString);

            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            System.out.println("sourceAsMap:"+sourceAsMap);
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();
            System.out.println("sourceAsBytes:"+sourceAsBytes);

        } else {
        }
    }

    public static void main(String[] args) {
        try {
            //In case a specific document version has been requested, and the existing document has a different version number, a version conflict is raised:
//            GetRequest request = new GetRequest("posts", "1").version(2);
//            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
//            handlerGetResponse(getResponse);
            getApi();
        } catch (ElasticsearchException exception) {
            exception.printStackTrace();
            if (exception.status() == RestStatus.CONFLICT) {

            }
//        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}

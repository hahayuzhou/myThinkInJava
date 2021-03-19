package com.thinking.my.es.high;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.Map;

/**
 * @Description This API helps to get only the _source field of a document.
 * @Author liyong
 * @Date 2021/2/20 5:54 下午
 **/
public class GetSourceApI {
    static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9201, "http")
            ));
    public static GetSourceRequest getSourceRequest() {
        GetSourceRequest getSourceRequest = new GetSourceRequest(
                "posts",
                "1");
        return getSourceRequest;
    }

    public static void OptionalArguments(GetSourceRequest getSourceRequest){
        String[] includes = Strings.EMPTY_ARRAY;//Arguments of the context excludes and includes are optional (see examples in Get API documentation)
        String[] excludes = new String[]{"postDate"};
        getSourceRequest.fetchSourceContext(
                new FetchSourceContext(true, includes, excludes));//FetchSourceContext 's first argument fetchSource must be true, otherwise ElasticsearchException get thrown
        getSourceRequest.routing("routing");//控制请求的分片路由。 使用此值哈希分片而不是ID。
        getSourceRequest.preference("preference");//设置首选项以执行搜索。 默认值是随机分配所有分片。 可以设置为{@code _local}以使用本地分片或自定义值，以确保在不同请求中使用相同的顺序
        getSourceRequest.realtime(false);//Set realtime flag to false (true by default)
        getSourceRequest.refresh(true);//Perform a refresh before retrieving the document (false by default)

    }

    public static void main(String[] args) {

        try {
            GetSourceRequest getSourceRequest = getSourceRequest();
            OptionalArguments(getSourceRequest);
            GetSourceResponse response = client.getSource(getSourceRequest, RequestOptions.DEFAULT);
            Map<String, Object> source = response.getSource();
            System.out.println(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

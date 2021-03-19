package com.thinking.my.es.high;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2021/2/18 8:40 下午
 **/
public class IndexAPI {
   static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9201, "http")
                   ));

    public static void main(String[] args) {
//        index();
//        IndexRequest request = new IndexRequest("posts")
//                .id("1")
//                .source("field", "value")
//                .setIfSeqNo(10L)
//                .setIfPrimaryTerm(20);

//        IndexRequest request = new IndexRequest("posts")
//                .id("1")
//                .source("field", "value")
//                .opType(DocWriteRequest.OpType.CREATE);
//        try {
//            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
//            System.out.println("success");
//            afterIndex(indexResponse);
//        } catch(ElasticsearchException e) {
//            e.printStackTrace();
//            if (e.status() == RestStatus.CONFLICT) {
//                System.out.println(e.status());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        getApi();
//        getAsyncApi();
        indexAsync();

    }
    public static void index() {
        IndexRequest request = new IndexRequest("index1");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"liyong\"," +
                "\"postDate\":\"2021-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println("success");
            afterIndex(indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void afterIndex(IndexResponse indexResponse){
        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            //Handle (if needed) the case where the document was created for the first time
            System.out.println(DocWriteResponse.Result.CREATED);
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println(DocWriteResponse.Result.UPDATED);

        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("number of successful shards is less than total shards");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }
    }
    public static void indexAsync() {
        IndexRequest request = new IndexRequest("index_async");
        request.id("2");
        String jsonString = "{" +
                "\"user\":\"liyong\"," +
                "\"postDate\":\"2021-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        ActionListener listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("fail");
            }
        };
        client.indexAsync(request, RequestOptions.DEFAULT,listener);

    }

    public static IndexRequest mapRequest(){
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1").source(jsonMap);
        return indexRequest;
    }
    public static IndexRequest xContentBuilderRequest() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1").source(builder);
        return indexRequest;
    }



    public static void createIndexApi() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        CreateIndexRequest request = new CreateIndexRequest("twitter_two");//创建索引
        //创建的每个索引都可以有与之关联的特定设置。
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        //创建索引时创建文档类型映射
        request.mapping("tweet",//类型定义
                "  {\n" +
                        "    \"tweet\": {\n" +
                        "      \"properties\": {\n" +
                        "        \"message\": {\n" +
                        "          \"type\": \"text\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }",//类型映射，需要的是一个JSON字符串
                XContentType.JSON);

        //为索引设置一个别名
        request.alias(
                new Alias("twitter_alias")
        );
        //可选参数
        request.timeout(TimeValue.timeValueMinutes(2));//超时,等待所有节点被确认(使用TimeValue方式)
        //request.timeout("2m");//超时,等待所有节点被确认(使用字符串方式)

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));//连接master节点的超时时间(使用TimeValue方式)
        //request.masterNodeTimeout("1m");//连接master节点的超时时间(使用字符串方式)

        request.waitForActiveShards(2);//在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示。
        //request.waitForActiveShards(ActiveShardCount.DEFAULT);//在创建索引API返回响应之前等待的活动分片副本的数量，以ActiveShardCount形式表示。

        //同步执行
        CreateIndexResponse createIndexResponse = client.indices().create(request,RequestOptions.DEFAULT);
        //异步执行
        //异步执行创建索引请求需要将CreateIndexRequest实例和ActionListener实例传递给异步方法：
        //CreateIndexResponse的典型监听器如下所示：
        //异步方法不会阻塞并立即返回。
        ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
            @Override
            public void onResponse(CreateIndexResponse createIndexResponse) {
                //如果执行成功，则调用onResponse方法;
            }
            @Override
            public void onFailure(Exception e) {
                //如果失败，则调用onFailure方法。
            }
        };
        client.indices().createAsync(request, RequestOptions.DEFAULT,listener);//要执行的CreateIndexRequest和执行完成时要使用的ActionListener

        //返回的CreateIndexResponse允许检索有关执行的操作的信息，如下所示：
        boolean acknowledged = createIndexResponse.isAcknowledged();//指示是否所有节点都已确认请求
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();//指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
    }




}

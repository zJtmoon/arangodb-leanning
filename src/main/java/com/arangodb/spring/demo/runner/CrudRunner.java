package com.arangodb.spring.demo.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoSearch;
import com.arangodb.async.ArangoDBAsync;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.CursorEntity;
import com.arangodb.entity.IndexEntity;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.entity.arangosearch.CollectionLink;
import com.arangodb.entity.arangosearch.FieldLink;
import com.arangodb.model.FulltextIndexOptions;
import com.arangodb.model.arangosearch.ArangoSearchCreateOptions;
import com.arangodb.model.arangosearch.ArangoSearchPropertiesOptions;
import com.arangodb.spring.demo.Constant.Pregel;
import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.repository.CharacterRepository;
import com.arangodb.springframework.core.ArangoOperations;
import com.arangodb.springframework.core.template.ArangoTemplate;
import com.arangodb.velocystream.Request;
import com.arangodb.velocystream.RequestType;
import com.arangodb.velocystream.Response;
import io.lettuce.core.ScriptOutputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.arangodb.spring.demo.Constant.ConditionType.PAGERANK;

@Slf4j
@ComponentScan("com.arangodb.spring.demo")
public class CrudRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations;
    //    @Autowired
//    private ArangoDBAsync arangoDBAsync;
//    @Autowired
    private ArangoDB arangoDB;
    @Autowired
    private CharacterRepository repository;
    @Value("${timeLength}")
    private Integer timeLength;
    @Override
    public void run(final String... args) throws Exception {
//        ArangoCursor<Object> arangoCursor=operations.query(" with demo_contract_node,demo_phoneno_node,demo_certno_node,demo_creditcardno_node,demo_depositacct_node,demo_businno_node,demo_loanacct_node\n" +
//                "for  p\n" +
//                "    IN ANY SHORTEST_PATH 'demo_businno_node/10000019970202001X' TO 'demo_certno_node/10000119970903001x'\n" +
//                "demo_certno_phoneno_relation,demo_certno_creditcardno_relation,demo_contract_certno_relation,demo_depositacct_depositacct_relation,demo_creditcardno_businno_relation,demo_certno_depositacct_relation,demo_contract_loanacct_relation,demo_certno_businno_relation\n" +
//                "    OPTIONS {bfs:true, uniqueVertices:'path'}\n" +
//                "    return p",Object.class);
//        ArangoCursor<Object> arangoCursor=operations.query(" FOR c IN FULLTEXT(characters, 'name', 'Ned')\n" +
//                "    RETURN c",Object.class);
//        CursorEntity.Stats stats=arangoCursor.getStats();
//        Collection<CursorEntity.Warning> warnings=arangoCursor.getWarnings();
//        System.out.println(arangoCursor.asListRemaining());
//        System.out.println(JSONObject.toJSONString(stats));
//        System.out.println(JSONObject.toJSONString(warnings))
        System.out.println("======================================================================================================================================================================");
//        //创建全文索引
//        FulltextIndexOptions fulltextIndexOptions = new FulltextIndexOptions();
//        fulltextIndexOptions.minLength(1);
//        List<String> list = new ArrayList<>();
////        list.add("name");
////        list.add("age");
//        list.add("surname");
//        list.add("xxx");
//        list.add("yyy");
//        list.add("zzzz");
//        list.add("surname");
//        operations.collection("characters").ensureFulltextIndex(list,fulltextIndexOptions);
//        operations.collection("characters").dropIndex("86852883");


//        JSONArray q = JSON.parseArray(JSONObject.toJSONString(operations.collection("characters").getIndexes())).stream().filter(a -> "fulltext".equals(((JSONObject) a).get("type")
//        ) && !list.contains(((JSONObject) a).getJSONArray("fields").get(0))).collect(Collectors.toCollection(JSONArray::new));
//
//        //表的全文索引数组
//        JSONArray q1 = JSON.parseArray(JSONObject.toJSONString(operations.collection("characters").getIndexes())).stream().filter(a -> "fulltext".equals(((JSONObject) a).get("type")
//        )).collect(Collectors.toCollection(JSONArray::new));
//
//        //要删除的索引id，
//        List<String> l = q1.stream().filter(a -> !list.contains(((JSONObject) a).getJSONArray("fields").get(0))).map(a -> ((JSONObject) a).getString("id")).collect(Collectors.toList());
//
//        //全文索引数组的feids
//        List l2=q1.stream().map(a -> ((JSONObject) a).getJSONArray("fields").get(0)).collect(Collectors.toList());
//        //要新增的索引
//        List<String> l3=list.stream().filter(a->!l2.contains(a)).collect(Collectors.toList());
//
//        System.out.println(JSONObject.toJSONString(q1));
//        System.out.println(l.toString());
//
//        System.out.println("要新增的索引   "+l3);
        System.out.println("======================================================================================================================================================================");


        System.out.println("=====================================================================================================================");
//        //        //创建arangosearch
//        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
//
//        ArangoDB arangoDB = new ArangoDB.Builder().loadProperties(is).maxConnections(20)
//                .loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN)
//                .build();
//        Request request = new Request("spring-demo", RequestType.POST, "/_api/control_pregel");
//        Map<String, Object> body = new HashMap<>();
//        body.put("algorithm", "scc");
//        body.put("graphName", "aaa");
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("resultField", "\"bindVars\": {\n" +
//                "\"num\": 1\n" +
//                "}");
//        body.put("params", jsonObject);
//        request.setBody(arangoDB.util().serialize(body));
//        StopWatch watch = new StopWatch("上传案件");
//        watch.start("读取文件");
//        Response repsonse = arangoDB.execute(request);
////        System.out.println("111111111111111111111"+JSONObject.toJSONString(repsonse));
//
//
//        System.out.println(PAGERANK.getType());
//        System.out.println("sacafef".toUpperCase());

//        switch (Pregel.getByType("hits")) {
//            case PAGERANK:
//                jsonObject.put("maxGSS", 20);
//                jsonObject.put("threshold", 0.00000001);
//                break;
//            case CONNECTEDCOMPONENTS:
//                break;
//            case SCC:
//                break;
//            case LINERANK:
//                jsonObject.put("maxGSS", 20);
//                jsonObject.put("threshold", 0.00000001);
//                break;
//            case SLPA:
//                jsonObject.put("maxGSS", 100);
//                jsonObject.put("maxCommunities", 1);
//                break;
//            case LABELPROPAGATION:
//                jsonObject.put("maxGSS", 100);
//                break;
//            case HITS:
//                System.out.println("hits");
//                break;
//            default:
//                break;
//        }
        System.out.println("=====================================================================================================================");

//        System.out.println(twoMonth());
        JSONObject j=new JSONObject();
//        //        //创建arangosearch
//        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
//
//        ArangoDB arangoDB = new ArangoDB.Builder().loadProperties(is).maxConnections(20)
//                .loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN)
//                .build();
//        Request request = new Request("consum_apply_graph", RequestType.GET, "/_api/query/current");
//        Response repsonse = arangoDB.execute(request);
//        JSONArray jsonArray = JSON.parseArray(repsonse.getBody().toString());
//
//        List<String> ids=jsonArray.stream().filter(a->("killed".equals(((JSONObject)a).get("state")))).map(a -> ((JSONObject) a).getString("id")).collect(Collectors.toList());
//
//        System.out.println(ids.toString());
        System.out.println("=====================================================================================================================");


//        watch.stop();
//        Map<String, Object> body2 = new HashMap<>();
//        body2.put("zzzzzz", "sccxsaxa");
//        request.setBody(arangoDB.util().serialize(body2));
//        System.out.println("response    "+request.getBody());


        ////        System.out.println( watch.prettyPrint());
//        log.info(watch.prettyPrint());
////        Response repsonse = arangoDB.execute(request);
//        System.out.println("response------ "+repsonse);
//
//        StopWatch watch2 = new StopWatch("pregel计算开始");
//
//        for (int i = 0; i < 10; i++) {
//            watch2.start(i+"读取文件");
//
//            watch2.stop();
//        }
//        log.info(watch2.prettyPrint());
////        collectionLink.analyzers();
//        FieldLink fieldLink = FieldLink.on("vvvvv").analyzers("text_zh");
//
////        FieldLink fieldLink1 = FieldLink.on("bbbbb").analyzers("text_zh");
//        List<FieldLink> l = new ArrayList();
//
//        l.add(fieldLink);
////        l.add(fieldLink1);
//
////        CollectionLink collectionLink = CollectionLink.on("characters").analyzers().includeAllFields(true).analyzers("identity");
//
//
////        for (FieldLink f : l) {
////            collectionLink.fields(f);
////        }
////        ArangoSearchCreateOptions arangoSearchCreateOptions = new ArangoSearchCreateOptions().link(collectionLink);
//
////        System.out.println("name-------"+collectionLink.getAnalyzers());
////        CollectionLink collectionLink=new CollectionLink("xsax");
//
////        arangoSearchCreateOptions.link();
////        arangoSearchCreateOptions.link();
//        String xxx="sdasa1232423425,12345";
//        System.out.println(xxx.contains("12345"));
//
//
//        List<String> aaa=new ArrayList<>();
//        for (String c:aaa) {
//            System.out.println("zzzzzzzzzzzzzzzzzzzz");
//        }
//        arangoDB.db("spring-demo").createArangoSearch("characters_View",arangoSearchCreateOptions);

//        ArangoSearch arangoSearch=arangoDB.db("spring-demo").arangoSearch("characters3-view");
//        if (arangoSearch.exists()){
//            System.out.println("----------------------");
//        }

//        ArangoSearch arangoSearch2 = arangoDB.db("spring-demo").arangoSearch("ExampleView");
//        if (arangoSearch2.exists()) {
//            System.out.println(JSONObject.toJSONString(arangoSearch2));
//        }
//
//        arangoDB.db("spring-demo").arangoSearch("ExampleView").getProperties().getLinks();
//
//        ArangoSearchPropertiesOptions arangoSearchPropertiesOptions=new ArangoSearchPropertiesOptions();
//        arangoSearchPropertiesOptions.link(collectionLink);

//        arangoDB.db("spring-demo").arangoSearch("characters2-view").updateProperties(arangoSearchPropertiesOptions);

//        arangoDB.db("spring-demo").arangoSearch("characters2-view").replaceProperties(arangoSearchPropertiesOptions);


//        InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties");
//        ArangoDB.Builder arango= new ArangoDB.Builder().loadProperties(in);
//        ArangoDB arango1 = arango.build();
//        Request request = new Request("_system", RequestType.POST, "/_api/control_pregel");
//        Map<String, Object> body = new HashMap<>();
//        body.put("algorithm", "pagerank");
//        body.put("graphName", "test");
//        request.setBody(arango1.util().serialize(body));
//
//        Response repsonse = arango1.execute(request);


        // 首先删除数据库，以便我们可以使用相同的数据集多次运行它
//        operations.dropDatabase();
//
//        System.out.println("# CRUD 操作");
//
//        // 在数据库中保存单个实体
//        // 不需要先创建集合。 这会自动发生
//        final Character nedStark = new Character("Ned", "Stark", true, 41);
//        repository.save(nedStark);
//        // 数据库中生成的id在原始实体中设置
//        System.out.println(String.format("Ned Stark 在数据库中的ID为: '%s'", nedStark.getId()));
//
//        // 让我们来看看我们是否可以在数据库中找到Ned Stark
//        final Character foundNed = repository.findById(nedStark.getId()).get();
//        System.out.println(String.format("找到记录  %s", foundNed));
//
//        // 因为每个人都知道Ned Stark在第一季就死了。
//        // 所以我们必须更新他的“活着”旗帜
//        nedStark.setAlive(false);
//        repository.save(nedStark);
//        final Character deadNed = repository.findById(nedStark.getId()).get();
//        System.out.println(String.format("Ned Stark在'alive'标志更新后 : %s", deadNed));
//
//        // 我们保存一些额外的字符
//        final Collection<Character> createCharacters = createCharacters();
//        System.out.println(String.format("保存 %s 个附加字符", createCharacters.size()));
//        repository.saveAll(createCharacters);
//
//        final Iterable<Character> all = repository.findAll();
//        final long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(all.iterator(), 0), false).count();
//        System.out.println(String.format("数据库中共有 %s 个字符", count));
//
//        System.out.println("## 返回按名称排序的所有字符");
//        final Iterable<Character> allSorted = repository.findAll(new Sort(Sort.Direction.ASC, "name"));
//        allSorted.forEach(System.out::println);
//
//        System.out.println("## 返回按名称排序的前5个字符");
//        final Page<Character> first5Sorted = repository
//                .findAll(PageRequest.of(0, 5, new Sort(Sort.Direction.ASC, "name")));
//        first5Sorted.forEach(System.out::println);
    }


    /**
     * 测试数据集
     */
    public static Collection<Character> createCharacters() {
        return Arrays.asList(new Character("Ned", "Stark", false, 41), new Character("Robert", "Baratheon", false),
                new Character("Jaime", "Lannister", true, 36), new Character("Catelyn", "Stark", false, 40),
                new Character("Cersei", "Lannister", true, 36), new Character("Daenerys", "Targaryen", true, 16),
                new Character("Jorah", "Mormont", false), new Character("Petyr", "Baelish", false),
                new Character("Viserys", "Targaryen", false), new Character("Jon", "Snow", true, 16),
                new Character("Sansa", "Stark", true, 13), new Character("Arya", "Stark", true, 11),
                new Character("Robb", "Stark", false), new Character("Theon", "Greyjoy", true, 16),
                new Character("Bran", "Stark", true, 10), new Character("Joffrey", "Baratheon", false, 19),
                new Character("Sandor", "Clegane", true), new Character("Tyrion", "Lannister", true, 32),
                new Character("Khal", "Drogo", false), new Character("Tywin", "Lannister", false),
                new Character("Davos", "Seaworth", true, 49), new Character("Samwell", "Tarly", true, 17),
                new Character("Stannis", "Baratheon", false), new Character("Melisandre", null, true),
                new Character("Margaery", "Tyrell", false), new Character("Jeor", "Mormont", false),
                new Character("Bronn", null, true), new Character("Varys", null, true), new Character("Shae", null, false),
                new Character("Talisa", "Maegyr", false), new Character("Gendry", null, false),
                new Character("Ygritte", null, false), new Character("Tormund", "Giantsbane", true),
                new Character("Gilly", null, true), new Character("Brienne", "Tarth", true, 32),
                new Character("Ramsay", "Bolton", true), new Character("Ellaria", "Sand", true),
                new Character("Daario", "Naharis", true), new Character("Missandei", null, true),
                new Character("Tommen", "Baratheon", true), new Character("Jaqen", "H'ghar", true),
                new Character("Roose", "Bolton", true), new Character("The High Sparrow", null, true));
    }
    public String twoMonth() {
        Date dNow = new Date();
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dNow);
        calendar.add(Calendar.DAY_OF_MONTH, -timeLength);
        dBefore = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String defaultStartDate = sdf.format(dBefore);
        return defaultStartDate;
    }
    public void lll(){
        System.out.println("csaa");

        List<String> s=null;
        if (s==null){
            System.out.println("zzzzzzzzzz");
            for (String b:s) {
                System.out.println("---------------------------------");
            }
        }

    }
    public void xxx(){
        System.out.println(timeLength);
        System.out.println(twoMonth());
    }
}

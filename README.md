#### 项目中使用

`Api.java`

```java
public class Api extends BaseApiImpl {

    private static Api api = new Api(BASE_URL);

    private Api(String baseUrl) {
        super(baseUrl);
    }

    public static RetrofitService getInstance() {
        return api.getRetrofit().create(RetrofitService.class);
    }
}
```

`RetrofitService`

```java
public interface RetrofitService {
	@POST("xxx/xxxx")
    Observable<Bean> funcation(@Body RequestBody body);
}
```

`Activity`

```java
 gson = new Gson();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("xxxx", xxx);

        String s = gson.toJson(hashMap);
        RequestBody requestBody = 			                      RequestBody.create(MediaType.parse("application/json;charset=utf-8"), s);

        Api.getInstance().getAdvertising(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject jsonObject) throws Exception {
                      
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        
                    }
                });
```


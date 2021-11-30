package org.example.clientecenso.gui;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.example.clientecenso.config.ConfigurationSingletonClient;
import org.example.clientecenso.dao.retrofit.RetrofitCasamientos;
import org.example.clientecenso.dao.retrofit.RetrofitDefunciones;
import org.example.clientecenso.dao.retrofit.RetrofitNacimientos;
import org.example.clientecenso.dao.retrofit.RetrofitPersonas;
import org.example.clientecenso.gui.utils.ConstantesGui;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DI {


    private final ConfigurationSingletonClient configurationSingletonClient;

        @Inject
        public DI(ConfigurationSingletonClient configurationSingletonClient) {
            this.configurationSingletonClient = configurationSingletonClient;
        }

        @Produces
        @Singleton
        public OkHttpClient getOKHttpClient() {
            return new OkHttpClient.Builder()
                    .protocols(List.of(Protocol.HTTP_1_1))
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .build();
        }

        @Produces
        @Singleton
        @Named(ConstantesGui.PATHBASE)
        public String getPathBase(ConfigurationSingletonClient configurationSingletonClient){
            return configurationSingletonClient.getPathbase();
        }

        @Produces
        @Singleton
        public Gson getGson() {
            return new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
                            LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                    .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                            (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())).create();
        }

        @Produces
        @Singleton
        public Retrofit createRetrofit(OkHttpClient client,@Named(ConstantesGui.PATHBASE) String pathBase) {
            return new Retrofit.Builder()
                    .baseUrl(pathBase)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(client)
                    .build();
        }

            @Produces
        public RetrofitPersonas apiPersonas(@NotNull Retrofit retrofit) {

            return retrofit.create(RetrofitPersonas.class);
        }

        @Produces
        public RetrofitNacimientos apiNacimientos(@NotNull Retrofit retrofit) {


            return retrofit.create(RetrofitNacimientos.class);
        }

        @Produces
        public RetrofitDefunciones apiDefunciones(@NotNull Retrofit retrofit) {


            return retrofit.create(RetrofitDefunciones.class);
        }

        @Produces
        public RetrofitCasamientos apiCasamientos(@NotNull Retrofit retrofit) {


            return retrofit.create(RetrofitCasamientos.class);
        }

//    private final ConfigurationSingletonClient configurationSingletonClient;
//
//    @Inject
//    public DI(ConfigurationSingletonClient configurationSingletonClient) {
//        this.configurationSingletonClient = configurationSingletonClient;
//    }
//
//    @Produces
//    @Singleton
//    public OkHttpClient getOKHttpClient() {
//        return new OkHttpClient.Builder()
//                .protocols(List.of(Protocol.HTTP_1_1))
//                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
//                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
//                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
//                .build();
//    }
//
//    @Produces
//    @Singleton
//    public Gson getGson() {
//        return new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
//                        LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
//                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
//                        (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())).create();
//    }
//
//    @Produces
//    @Singleton
//    public Retrofit createRetrofit() {
//        return new Retrofit.Builder()
//                .baseUrl(configurationSingletonClient.getPathbase())
//                .addConverterFactory(GsonConverterFactory.create(getGson()))
//                .client(getOKHttpClient())
//                .build();
//    }
//
//
//    @Produces
//    public RetrofitPersonas apiPersonas(@NotNull Retrofit retrofit) {
//
//        return retrofit.create(RetrofitPersonas.class);
//    }
//
//    @Produces
//    public RetrofitNacimientos apiNacimientos(@NotNull Retrofit retrofit) {
//
//
//        return retrofit.create(RetrofitNacimientos.class);
//    }
//
//    @Produces
//    public RetrofitDefunciones apiDefunciones(@NotNull Retrofit retrofit) {
//
//
//        return retrofit.create(RetrofitDefunciones.class);
//    }
//
//    @Produces
//    public RetrofitCasamientos apiCasamientos(@NotNull Retrofit retrofit) {
//
//
//        return retrofit.create(RetrofitCasamientos.class);
//    }
}

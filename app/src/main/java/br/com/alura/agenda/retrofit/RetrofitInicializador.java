package br.com.alura.agenda.retrofit;

import br.com.alura.agenda.service.AlunoService;
import br.com.alura.agenda.service.DispositivoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.100:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public AlunoService getAlunoService(){
        return retrofit.create(AlunoService.class);
    }


    public DispositivoService getDispositivoService() {
        return retrofit.create(DispositivoService.class);
    }
}

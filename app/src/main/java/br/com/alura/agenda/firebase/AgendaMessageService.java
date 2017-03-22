package br.com.alura.agenda.firebase;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dto.AlunoSync;
import br.com.alura.agenda.events.AtualizaListaAlunoEvent;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AgendaMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mensagem = remoteMessage.getData();
        Log.i("mensagem Firebase", "Mensagem recebida" + mensagem);

        converteParaAluno(mensagem);

    }

    private void converteParaAluno(Map<String, String> mensagem) {
        String key = "alunoSync";
        if(mensagem.containsKey(key)){
            String json = mensagem.get(key);
            ObjectMapper mapper = new ObjectMapper();
            try {
                AlunoSync alunoSync = mapper.readValue(json, AlunoSync.class);
                AlunoDAO dao = new AlunoDAO(this);
                dao.sincroniza(alunoSync.getAlunos());
                dao.close();
                EventBus.getDefault().post(new AtualizaListaAlunoEvent());
            } catch (IOException e) {
                new RuntimeException(e);
            }

        }
    }
}

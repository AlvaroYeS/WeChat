package org.example.wechat;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import javafx.geometry.HPos;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.example.wechat.Conexionurl.connectionString;

public class Conexion {
    public static ArrayList<Usuario> ListaUsuarios = new ArrayList<>();
    public static ArrayList<Mensaje> ListaMensajes = new ArrayList<>();



    public static void connect() {


        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();


        try(MongoClient client = MongoClients.create(settings)) {
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            MongoDatabase database = client.getDatabase("WeChat").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Usuario> collection = database.getCollection("Usuarios", Usuario.class);
            MongoCollection<Mensaje> collection1 = database.getCollection("Mensajes", Mensaje.class);


            MongoCursor<Usuario> cursor = collection.find().iterator();
            ListaUsuarios.clear();
            while (cursor.hasNext()) {
                ListaUsuarios.add(cursor.next());
            }

            for (Usuario u : ListaUsuarios) {
                u.convertDate();
            }


            MongoCursor<Mensaje> cursorMensajes = collection1.find().iterator();
            ListaMensajes.clear();
            while (cursorMensajes.hasNext()) {
                ListaMensajes.add(cursorMensajes.next());
            }

            for (Mensaje m : ListaMensajes) {
                m.initialize();
            }

            System.out.println(ListaUsuarios);

        }
    }

    public static void ordenarMensajes() {
        Collections.sort(ListaMensajes, new Comparator<Mensaje>(){
            public int compare(Mensaje o1, Mensaje o2){
                return o1.getFecha_envioDate().compareTo(o2.getFecha_envioDate());
            }
        });
    }
}

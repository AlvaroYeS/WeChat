package org.example.wechat;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.example.wechat.Conexionurl.connectionString;

public class Conexion {
    public static ArrayList<Usuario> ListaUsuarios = new ArrayList<>();
    public static ArrayList<Mensaje> ListaMensajes = new ArrayList<>();

    private static MongoDatabase database;
    private static MongoCollection<Usuario> collection;
    private static MongoCollection<Mensaje> collection1;
    private static ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    private static MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();
    private static MongoClient client = MongoClients.create(settings);

    public static void connect() {
            getUsuarios();
            getMensajes();
    }

    public static void getUsuarios() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        database = client.getDatabase("WeChat").withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection("Usuarios", Usuario.class);
        MongoCursor<Usuario> cursor = collection.find().iterator();
        ListaUsuarios.clear();
        while (cursor.hasNext()) {
            ListaUsuarios.add(cursor.next());
        }

        for (Usuario u : ListaUsuarios) {
            u.convertDate();
        }
    }

    public static void getMensajes() {
        collection1 = database.getCollection("Mensajes", Mensaje.class);
        MongoCursor<Mensaje> cursorMensajes = collection1.find().iterator();
        ListaMensajes.clear();
        while (cursorMensajes.hasNext()) {
            ListaMensajes.add(cursorMensajes.next());
        }

        for (Mensaje m : ListaMensajes) {
            m.getReceptor();
        }
    }

    public static void ordenarMensajes() {
        Collections.sort(ListaMensajes, new Comparator<Mensaje>(){
            public int compare(Mensaje o1, Mensaje o2){
                return o1.getFecha_envioDate().compareTo(o2.getFecha_envioDate());
            }
        });
    }

    public static void insertMensaje(Mensaje mensaje) {
        collection1.insertOne(mensaje);
    }
}

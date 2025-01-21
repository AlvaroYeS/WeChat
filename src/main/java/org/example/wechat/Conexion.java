package org.example.wechat;

import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Conexion {
    public static ArrayList<Usuario> ListaUsuarios = new ArrayList<>();
    public static ArrayList<Mensaje> ListaMensajes = new ArrayList<>();



    public static void connect() {
        try(MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            MongoDatabase database = client.getDatabase("Whatsapp").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Usuario> collection = database.getCollection("Usuarios", Usuario.class);
            MongoCollection<Mensaje> collection1 = database.getCollection("Mensajes", Mensaje.class);


            MongoCursor<Usuario> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                ListaUsuarios.add(cursor.next());
            }

            for (Usuario u : ListaUsuarios) {
                u.convertDate();
            }

            System.out.println(ListaUsuarios);

            MongoCursor<Mensaje> cursorMensajes = collection1.find().iterator();
            while (cursorMensajes.hasNext()) {
                ListaMensajes.add(cursorMensajes.next());
            }

            for (Mensaje m : ListaMensajes) {
                m.initialize();
            }

            System.out.println(ListaMensajes);

        }
    }
}

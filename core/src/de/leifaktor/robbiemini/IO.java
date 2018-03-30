package de.leifaktor.robbiemini;

import java.util.Iterator;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;


public class IO {
	
	public static void save(Episode epi, String filename) {
		FileHandle fh = Gdx.files.local(filename);
		save(epi, fh);
	}
	
	public static Episode load(String filename) {
		FileHandle file = Gdx.files.local(filename);
        Json json = new Json();
        json.setSerializer(RoomManager.class, new RoomManagerSerializer());
        return json.fromJson(Episode.class, file);
	}
	
    public static void save(Episode e, FileHandle file) {        
        Json json = new Json();
        json.setTypeName("class");
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(OutputType.json);
        json.setSerializer(RoomManager.class, new RoomManagerSerializer());
        file.writeString(json.prettyPrint(e), false);
    }
    
    static class RoomManagerSerializer implements Json.Serializer<RoomManager> {

        @Override
        public void write(Json json, RoomManager rooms, Class knownType) {
            json.writeObjectStart();
            json.writeArrayStart("rooms");
            for (Iterator<Entry<XYZPos, Room>> iterator = rooms.getRoomIterator(); iterator.hasNext();) {
                Entry<XYZPos, Room> entry = iterator.next();                
                json.writeObjectStart();
                json.writeValue("x", entry.getKey().x);
                json.writeValue("y", entry.getKey().y);
                json.writeValue("z", entry.getKey().z);
                json.writeValue("room", entry.getValue());
                json.writeObjectEnd();
            }
            json.writeArrayEnd();
            json.writeObjectEnd();
        }

        @Override
        public RoomManager read(Json json, JsonValue jsonData, Class type) {
            RoomManager rooms = new RoomManager();
            jsonData = jsonData.child();
            if (jsonData.child() != null) { // Falls der Array nicht leer ist
                jsonData = jsonData.child(); // gehe in den Array
                jsonData = jsonData.child();
                int x = jsonData.asInt();
                jsonData = jsonData.next();
                int y = jsonData.asInt();
                jsonData = jsonData.next();
                int z = jsonData.asInt();
                jsonData = jsonData.next();
                Room room = json.readValue(Room.class, jsonData);
                rooms.setRoom(z, x, y, room);
                jsonData = jsonData.parent();
                while (jsonData.next() != null) {
                    jsonData = jsonData.next(); // gehe zum nächsten Eintrag
                    jsonData = jsonData.child();
                    x = jsonData.asInt();
                    jsonData = jsonData.next();
                    y = jsonData.asInt();
                    jsonData = jsonData.next();
                    z = jsonData.asInt();
                    jsonData = jsonData.next();
                    room = json.readValue(Room.class, jsonData);
                    rooms.setRoom(z, x, y, room);
                    jsonData = jsonData.parent();
                }
            }
            return rooms;
        }
         
     }

}

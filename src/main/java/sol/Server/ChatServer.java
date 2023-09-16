package sol.Server;


import com.blogspot.debukkitsblog.net.Server;

public class ChatServer extends Server {
    public ChatServer(int port) {
        super(port);
    }

    @Override
    public void preStart() {
        super.registerMethod("LOGIN", (pack, socket) -> registerClient(pack.getSenderID(), socket));

        registerMethod("CHATMESSAGE", (pack, socket) -> {
            System.out.println("Neue Chatmessage: " + pack.get(2) + " ** von: " + pack.get(1));
            int anzahl = broadcastMessage(pack);
            sendReply(socket, String.valueOf (anzahl));
            System.out.println("Antwort an Client: " + "message wurde an " + anzahl + " clients gesendet");
        });
        registerMethod("MATH", (pack, sock) -> {
            int ergebnis = (Integer)pack.get(1) + (Integer)pack.get(2);
            sendReply(sock, ergebnis);
        });
        registerMethod("STOP", (pack, sock) -> {
            System.out.println("Server stopps!");
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            sendReply(sock, "Server stopped!");
            stop();
        });
    }
    public void stop(){
        super.stop();
    }
}

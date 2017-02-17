
public class TransportLayer
{

    private NetworkLayer networkLayer;
    //server is true if the application is a server (should listen) or false if it is a client (should try and connect)
    public TransportLayer(boolean server)
    {
        networkLayer = new NetworkLayer(server);
    }

    public void send(byte[] payload){
        System.out.println("Transport\t\tSend");
        networkLayer.send( payload );
    }

    public byte[] receive(){
         System.out.println("Transport\t\tReceive");
         byte[] payload = networkLayer.receive();    
         return payload;
   }

}

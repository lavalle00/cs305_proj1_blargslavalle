
public class TransportLayer
{

    private NetworkLayer networkLayer;
    //server is true if the application is a server (should listen) or false if it is a client (should try and connect)
    public TransportLayer(boolean server)
    {
        networkLayer = new NetworkLayer(server);
    }

    public void send(byte[] payload)
    {
        networkLayer.send( payload );
    }

    public byte[] receive()
    {
        byte[] payload = networkLayer.receive();    
        return payload;
    }
    public boolean synAckOut(boolean syn, boolean ack, boolean synack){
        //sending syn
        if(syn){
            
        }
        //sending ack
        else if(ack){
            
        }
        //sending synack
        else if(synack){
            
        }
        
        else{
            
        }
        return true;
    }
}
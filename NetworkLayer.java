
public class NetworkLayer
{

    private LinkLayer linkLayer;

    public NetworkLayer(boolean server)
    {
        linkLayer = new LinkLayer(server);

    }
    public void send(byte[] payload)
    {
        linkLayer.send( payload );
    }

    public byte[] receive()
    {
        byte[] payload = linkLayer.receive();
        return payload;
    }
    private void propDelay(byte[] payload){
        //add consistent delay here...
    }
    private void networkDelay(byte[] payload){
        int sizePayload = (payload.length)-1;
        //add delay based on payload length/size via sizePayload...
        
    }
}

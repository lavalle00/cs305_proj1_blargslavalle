import java.util.concurrent.TimeUnit;
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
    private void propDelay(long delay){
        //add consistent delay here...
        try{
        TimeUnit.MILLISECONDS.sleep(delay);
    }catch(InterruptedException e){}
    }
    private void networkDelay(byte[] payload){
        int sizePayload = (payload.length)-1;
        long delay = 0;
        //add delay based on payload length/size via sizePayload...
        try{
        TimeUnit.MILLISECONDS.sleep(delay);
    }catch(InterruptedException e){}
    }
}

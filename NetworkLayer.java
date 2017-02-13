import java.util.concurrent.TimeUnit;
public class NetworkLayer
{
    long delay;
    private LinkLayer linkLayer;

    public NetworkLayer(boolean server)
    {
        linkLayer = new LinkLayer(server);

    }
    public NetworkLayer(boolean server, long delay)
    {
        linkLayer = new LinkLayer(server);
        this.delay = delay;
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
        }
        catch(InterruptedException e){
            System.out.println(e.stackTrace());
        }
    }
    private void networkDelay(byte[] payload){
        int sizePayload = (payload.length)-1;
        long timeDelay = 1000; //1000ms
        long delayDuration = (sizePayload) / (timeDelay);
        //add delay based on payload length/size via sizePayload...
        try{
            TimeUnit.MILLISECONDS.sleep(timeDelay);
        }
        catch(InterruptedException e){
            System.out.println(e.stackTrace());
        }
    }
}

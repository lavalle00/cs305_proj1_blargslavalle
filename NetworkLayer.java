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
        System.out.println("Network \t\tSend");
        //add prop delay
        this.propDelay();
        //add trans delay
        this.networkDelay(payload);
        linkLayer.send( payload );
    }

    public byte[] receive()
    {
        System.out.println("Network \t\tReceive");
        byte[] payload = linkLayer.receive();
        return payload;
    }
    private void propDelay(){
        try{
            TimeUnit.MILLISECONDS.sleep(delay);
        }
        catch(InterruptedException e){
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}

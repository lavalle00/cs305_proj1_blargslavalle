import java.util.concurrent.TimeUnit;
public class NetworkLayer
{
    int pDelay, tDelay;
    long start, end;
    int rtt;
    boolean server;
    private LinkLayer linkLayer;

    public NetworkLayer(boolean server)
    {
        linkLayer = new LinkLayer(server);
        this.server = server;

    }
    public NetworkLayer(boolean server, int pDelay, int tDelay)
    {
        linkLayer = new LinkLayer(server);
        this.pDelay = pDelay;
        this.tDelay = tDelay;
        this.server = server;
    }
    public void send(byte[] payload)
    {
        //System.out.println("Network \t\tSend");
        start = System.currentTimeMillis();
        //add prop delay
        this.propDelay();
        //add trans delay
        this.networkDelay(payload);
        end = System.currentTimeMillis();
        rtt += (int)(end - start); //changing to += gives total trip time for first connection only
        if(server){
            payload = addRTT(payload, rtt);
        }
        linkLayer.send( payload );
    }

    public byte[] receive()
    {
        //System.out.println("Network \t\tReceive");
        byte[] payload = linkLayer.receive();
        return payload;
    }
    private void propDelay(){
        try{
            TimeUnit.MILLISECONDS.sleep(pDelay);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    private void networkDelay(byte[] payload){
        int sizePayload = (payload.length)-1;
        int delayDuration = (sizePayload) * (tDelay);
        //add delay based on payload length/size via sizePayload...
        try{
            TimeUnit.MILLISECONDS.sleep(delayDuration);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    private byte[] addRTT(byte[] payload, int rtt){
        String payloadStr = new String (payload);
        payloadStr = payloadStr + "\nRound Trip Time: " + rtt + "ms";
        return payloadStr.getBytes();
    }
}

public class LinkLayer
{
    private PhysicalLayer physicalLayer;

    public LinkLayer(boolean server)
    {
        physicalLayer = new PhysicalLayer(server);
    }

    public void send(byte[] payload)
    {
        //System.out.println("Link\t\t\tSend");
        physicalLayer.send( payload );
    }

    public byte[] receive()
    {
        //System.out.println("Link\t\t\tReceive");
        byte[] payload = physicalLayer.receive();
        return payload;
    }
}


public class TransportLayer
{
    String str_syn, str_ack;
    private NetworkLayer networkLayer;
    //server is true if the application is a server (should listen) or false if it is a client (should try and connect)
    public TransportLayer(boolean server){
        networkLayer = new NetworkLayer(server);
        this.str_syn = "syn";
        this.str_ack = "ack";
        byte[] arr_syn = str_syn.getBytes();
        byte[] arr_ack = str_ack.getBytes();
    }

    public void send(byte[] payload){
        if(payload == this.arr_ack){
            
        }
        threeWay(str_syn);
        System.out.println("Transport\t\tSend");
        networkLayer.send( payload );
    }

    public byte[] receive(){
        tempReceive = networkLayer.receive();
        //recieve syn
        if(tempReceive == str_syn){
            //respond w/ ack
            this.send(this.arr_ack);
        }
        
        
        System.out.println("Transport\t\tReceive");
        byte[] payload = networkLayer.receive();    
        return payload;
   }
   
   public boolean threeWay(String msg){
       if(msg == str_syn){
           networkLayer.send(arr_syn);
       }
       if(msg == str_ack){
           
       }
       return true;
    }

}

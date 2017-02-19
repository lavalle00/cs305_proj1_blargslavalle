
public class TransportLayer
{
    //hardcoded byte arrays for syn & ack
    String str_syn, str_ack;
    byte[] arr_syn, arr_ack;
    byte[] masterPayload;
    private NetworkLayer networkLayer;
    
    //server is true if the application is a server (should listen) or false if it is a client (should try and connect)
    public TransportLayer(boolean server){
        networkLayer = new NetworkLayer(server);
        this.str_syn = "syn";
        this.str_ack = "ack";
        this.arr_syn = str_syn.getBytes();
        this.arr_ack = str_ack.getBytes();
    }
    
    public TransportLayer(boolean server, long delay){
        networkLayer = new NetworkLayer(server, delay);
        this.str_syn = "syn";
        this.str_ack = "ack";
        this.arr_syn = str_syn.getBytes();
        this.arr_ack = str_ack.getBytes();
    }

    public void send(byte[] payload){
        //check if 3way data
        if(payload == this.arr_ack || payload == this.arr_syn ){
            
        }
        //else cache the payload
        else{
            System.out.println("Cached Master");
            String str = new String ( payload );
            str = str.substring(10).trim();
            System.out.println(str);
            this.masterPayload = str.getBytes();
        }
        //
        if(payload == this.arr_ack){
            
        }
        else{
            System.out.println("Three Way\t\tBegin");
            threeWay(str_syn);
        }
       
        System.out.println("Transport\t\tSend");
        networkLayer.send( payload );
    }

    public byte[] receive(){
        byte[] tempReceive = networkLayer.receive();
        
        //recieve syn
        if(tempReceive == this.arr_syn){
            //respond w/ ack
            this.send(this.arr_ack);
        }
        if(tempReceive == this.arr_ack){
            //send in ze moos
            System.out.println("Payload\t\tSent");
            this.send(this.masterPayload);
        }
        
        
        System.out.println("Transport\t\tReceive");
        byte[] payload = networkLayer.receive();    
        return payload;
   }
   
   public boolean threeWay(String msg){
       if(msg == str_syn){
           //syn through layers to this.receive()
           networkLayer.send(arr_syn);
           //receive from network
           if(this.receive() == arr_ack){
            //send payload
            
            }
       }
       if(msg == str_ack){
           
       }
       return true;
    }

}

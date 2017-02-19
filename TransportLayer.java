
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
            this.masterPayload = payload;
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
        
        System.out.println("Transport Rec Method");
        byte[] tempReceive = networkLayer.receive();
        String recString = new String(tempReceive);
        String synString = new String(this.arr_syn);
        String ackString = new String(this.arr_ack);
        //recieve syn
        if(recString.equals(synString)){
            //respond w/ ack
            System.out.println("Ack\t\tSent");
            this.send(this.arr_ack);
        }
        if(recString.equals(ackString)){
            //send in ze moos
            System.out.println("Payload\t\tSent");
            this.send(this.masterPayload);
        }
        
        
        System.out.println("Transport\t\tReceive");  
        return tempReceive;
   }
   
   public boolean threeWay(String msg){
       if(msg == str_syn){
           //syn through layers to this.receive()
           networkLayer.send(arr_syn);
           //receive from network
           if(this.receive() == arr_ack){
            //send payload
            System.out.println("Transport\t\tSend");
            networkLayer.send( this.masterPayload );
            }
       }
       if(msg == str_ack){
           
       }
       return true;
    }

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
//This class represents the client application
public class ClientApp
{
    
        
    public static void main(String[] args) throws Exception
    {
        //create a new transport layer for client (hence false) (connect to server), and read in first line from keyboard

        
        TransportLayer transportLayer = new TransportLayer(false);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        int num = 0;
        boolean handshake = false;
        //while line is not empty
        while( line != null && !line.equals("") )
        {
            //convert lines into byte array, send to transoport layer and wait for response
            if(!handshake){
                handshake(transportLayer, handshake);
            }
            byte[] byteArray = line.getBytes();
            
            transportLayer.send( byteArray );
            byteArray = transportLayer.receive();
            printString(byteArray, transportLayer);
            System.out.println("#################");
            //read next line
            line = reader.readLine();
        }
    }
    public static void printString(byte[] byteArray, TransportLayer transportLayer){
            String code, payload;
            String[] strSplit;
            String str = new String ( byteArray );
            int totalRTT = 0;
            CharSequence newConn = "/nc ";
            if(str != null){
                strSplit = str.split("//");
                code = strSplit[0];
                if(strSplit.length == 1){
                    payload = "";
                }
                else{
                    payload = strSplit[1];
                }
                if(payload.contains(newConn)){
                    String[] splitFile = payload.split("/nc");
                    for(int i = 0; i < splitFile.length; i++){
                        if(splitFile[i].contains(".txt")){
                            String tempIP = "IP " + splitFile[i].trim();
                            transportLayer.send( tempIP.getBytes() );
                            byte[] tmpByteArray = transportLayer.receive();
                            printString(tmpByteArray, transportLayer);
                        }else{
                        if(i == 0){
                            switch(code) {
                                case "CODE: 200" :
                                    //OK
                                    System.out.println(code + "\t OK...\n\n" + splitFile[i]);
                                    break;
                                case "CODE: 304" :
                                    //Not Modified
                                    System.out.println(code + "\tNot Modified...\n\n" + splitFile[i]);
                                    break;
                                case "CODE: 404" :
                                    //Page Requested Not Found
                                    System.out.println(code + "\tNot Found...\n\n");
                                    break;
                                default:
                                //When none of the above are applicable, just send string
                                System.out.println(str);
                                break;
                            }
                        }else{
                            switch(code) {
                                case "CODE: 200" :
                                    //OK
                                    System.out.println(splitFile[i]);
                                    break;
                                case "CODE: 304" :
                                    //Not Modified
                                    System.out.println(splitFile[i]);
                                    break;
                                case "CODE: 404" :
                                    //Page Requested Not Found
                                    System.out.println(code + "\tNot Found...\n\n");
                                    break;
                                default:
                                //When none of the above are applicable, just send string
                                System.out.println(str);
                                break;
                            }
                        }
                       }
                    }
                    
                }else{
                    switch(code) {
                        case "CODE: 200" :
                            //OK
                            System.out.println(code + "\t OK...\n\n" + payload);
                            break;
                        case "CODE: 304" :
                            //Not Modified
                            System.out.println(code + "\tNot Modified...\n\n" + payload);
                            break;
                        case "CODE: 404" :
                            //Page Requested Not Found
                            System.out.println(code + "\tNot Found...\n\n");
                            break;
                        default:
                            //When none of the above are applicable, just send string
                            System.out.println(str);
                            break;
                    }
                }
                
            }
    }
    public static void handshake(TransportLayer transportLayer, boolean handshake){
            System.out.println("Handshake Started");
            String syn = "syn";
            transportLayer.send(syn.getBytes());
            byte[] shakeArray = transportLayer.receive();
            String ackShake = new String (shakeArray);
            if(ackShake.equals("ack")){
                handshake = true;
            }
    }
}

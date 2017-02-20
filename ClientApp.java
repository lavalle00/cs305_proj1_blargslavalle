import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
//This class represents the client application
public class ClientApp
{
    
        
    public static void main(String[] args) throws Exception
    {
        String httpType = "1.1";
        //create a new transport layer for client (hence false) (connect to server), and read in first line from keyboard
        if(args.length != 1){
        System.exit(1);
        }
        httpType = args[0];
        System.out.println("HTTP Version:\t" + httpType);
        
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
            printString(byteArray, transportLayer, httpType);
            System.out.println("#################");
            //read next line
            line = reader.readLine();
        }
    }
    //takes a byte array, parses it as a printable string, while checking for codes thrown and if there are embedded files
    public static void printString(byte[] byteArray, TransportLayer transportLayer, String httpType){
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
                //split on new connection delimiter for embedded files
                if(payload.contains(newConn)){
                    String[] splitFile = payload.split("/nc");
                    //check if the splits contain a valid filename
                    for(int i = 0; i < splitFile.length; i++){
                        if(splitFile[i].contains(".txt")){
                            String tempIP = "IP " + splitFile[i].trim();
                           if(httpType.equals("1.0")){
                               handshake(transportLayer, true);
                           }
                            transportLayer.send( tempIP.getBytes() );
                            byte[] tmpByteArray = transportLayer.receive();
                            printString(tmpByteArray, transportLayer, httpType);
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
    //3-way handshake simulator, uses send and recieve to ensure correct round trip times including the sending of syn/ack
    public static void handshake(TransportLayer transportLayer, boolean handshake){
            //System.out.println("Handshake Started");
            String syn = "Syn";
            transportLayer.send(syn.getBytes());
            //System.out.println("Syn sent" + System.currentTimeMillis());
            transportLayer.receive();
            //System.out.println("ack recieved");
            handshake = true;
            //System.out.println("Handshake Finished");
            
    }
}

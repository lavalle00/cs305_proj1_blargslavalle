import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

//This class represents the server application
public class ServerApp
{

    TransportLayer transportLayer;
    BufferedReader buffReader;
    FileReader     fileReader;
    String HTTPtype;
    String IPaddress;
    String Command;
    int Pdelay;
    int Tdelay;
    public static void main(String[] args) throws Exception
    {
        ServerApp s = new ServerApp();
        s.init();
    }
    public void init()  throws Exception
    {
        //create a new transport layer for server (hence true) (wait for client)
        this.transportLayer = new TransportLayer(true);
        while( true )
        {
            //receive message from client, and send the "received" message back.
            byte[] byteArray = transportLayer.receive();
            //System.out.println(byteArray);
            //if client disconnected
            String input = new String (byteArray);
            String inputFirstChar = input.substring(0,1);
            String[] arrStr_Output;
            String strParsedOutput;
            boolean IPcomm = false;
            System.out.println("Into switch");
            System.out.println("Input: " + input);
            System.out.println("First Char: " + inputFirstChar);
            switch(inputFirstChar){
                
                case "C": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Command = strParsedOutput;
                          System.out.println("\tCOMMAND:\t" + strParsedOutput);
                          break;
                case "I": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          IPaddress = strParsedOutput;
                          System.out.println("\tIPADDRESS:\t" + strParsedOutput);
                          IPcomm = true;
                          break;
                case "P": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Pdelay = Integer.parseInt(strParsedOutput);
                          System.out.println("\tPropagationDelay:\t" + strParsedOutput);
                          transportLayer.send(stringEncode("Propagation Delay Updated"));
                          break;
                case "T": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Tdelay = Integer.parseInt(strParsedOutput);
                          transportLayer.send(stringEncode("Transmission Delay Updated"));
                          System.out.println("\tTransmissionDelay:\t" + strParsedOutput);
                          break;
                case "H": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          HTTPtype = strParsedOutput;
                          transportLayer.send(stringEncode("HTTP Version Selected"));
                          System.out.println("\tHTTP:\t\t" + strParsedOutput);
                          break;

                default: System.out.println("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"); 
                         transportLayer.send(stringEncode("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"));
                         break;
            }
            if(byteArray==null){
                System.out.println("Null Input");
                break;
            }
            
            //check if requested item can be found
            if(checkFound(IPaddress)){
            }else{
             codeThrow(404);
            }
            //check if requested item is modified
            if(checkModified(IPaddress)){
                
            }else{
             codeThrow(304);
            }
            //if not either of those, ok
            
            System.out.println("\tSending...");
            if(IPcomm){
                //FIX CODE THROWS
                //codeThrow(200);
                System.out.println("\tReading File");
                addressRead(IPaddress);
                IPcomm = false;
            }

        }
    }
    public void addressRead(String address){
        try {

            fileReader = new FileReader(address);
            buffReader = new BufferedReader(fileReader);

            String sCurrentLine;

            buffReader = new BufferedReader(new FileReader(address));
            while ((sCurrentLine = buffReader.readLine()) != null) {
                System.out.println("\tLine: " + sCurrentLine);
                transportLayer.send(stringEncode(sCurrentLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (buffReader != null){
                    buffReader.close();
                }

                if (fileReader != null){
                    fileReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public String codeThrow(int code){
        switch(code) {
            case 200:
                System.out.println("\n\tCode:\t200\t-Ok");
                return "\n\tCode:\t200\t-Ok" ;
            case 404:
                System.out.println("\n\tCode:\t404\t-Not Found");
                return "\n\tCode:\t404\t-Not Found";
            case 304:
                System.out.println("\n\tCode:\t304\t-Not Modified");
                return "\n\tCode:\t304\t-Not Modified";
            default:
                return "\n\tCode:\t404\t-Not Found";
        }
    }
    public boolean checkFound(String file){
        return true;
    }
    public boolean checkModified(String file){
        return true;
    }
    private byte[] stringEncode(String string){
        return string.getBytes();
    }
}

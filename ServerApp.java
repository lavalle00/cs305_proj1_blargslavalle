import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

//This class represents the server application
public class ServerApp
{

    public static void main(String[] args) throws Exception
    {
        //create a new transport layer for server (hence true) (wait for client)
        TransportLayer transportLayer = new TransportLayer(true);
        while( true )
        {
            //receive message from client, and send the "received" message back.
            byte[] byteArray = transportLayer.receive();
            //if client disconnected
            String input = new String (byteArray);
            String inputFirstChar = input.substring(0,1);
            String[] arrStr_Output;
            String strParsedOutput;
            switch(inputFirstChar){
                case "C": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tCOMMAND:\t" + strParsedOutput);
                          break;
                case "I": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tIP ADDRESS:\t" + strParsedOutput);
                          break;
                case "H": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tHTTP?:\t\t" + strParsedOutput);
                          break;
                case "N": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tHTTP PROTOCOL:\t" + strParsedOutput);
                          break;
                default: System.out.println("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"); 
                         break;
            }
            if(byteArray==null){
                System.out.println("Null Input");
                break;
            }
            String str = new String ( byteArray );
            String line = "received";
            byteArray = line.getBytes();
            transportLayer.send( byteArray );

        }
    }
}

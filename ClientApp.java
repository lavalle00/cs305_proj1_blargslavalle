import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
//This class represents the client application
public class ClientApp
{
    public static void main(String[] args) throws Exception
    {
        //create a new transport layer for client (hence false) (connect to server), and read in first line from keyboard
        //String str_200 = "CODE:\t200\tOK";
        //String str_304 = "CODE:\t304\tNotModified";
        //String str_404 = "CODE:\t404\tNotFound";
        String code, payload;
        String[] strSplit;
        TransportLayer transportLayer = new TransportLayer(false);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        int num = 0;
        //while line is not empty
        while( line != null && !line.equals("") )
        {
            //if(transportLayer.receive() != null){
            //    byte[] byteArray = transportLayer.receive();
            //}
            //convert lines into byte array, send to transoport layer and wait for response
            byte[] byteArray = line.getBytes();
            
            transportLayer.send( byteArray );
            byteArray = transportLayer.receive();
            String str = new String ( byteArray );
            if(str != null){
                strSplit = str.split("//");
                //for(int i = 0; i < strSplit.length-1; i++){
                //    System.out.println(strSplit[i]);
                // }
                code = strSplit[0];
                if(strSplit.length == 1){
                    payload = "";
                }
                else{
                    payload = strSplit[1];
                }
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
            System.out.println("#################");
            //read next line
            line = reader.readLine();
        }
    }
}

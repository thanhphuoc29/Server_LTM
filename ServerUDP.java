/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import static TCP.ServerTCP.getCurrentTimeReceive;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dell
 */
public class ServerUDP {
    public static void send(DatagramSocket socket,byte[] data,InetAddress address,int port) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
        socket.send(sendPacket);
    }
    public static void Logs(String message) {
        String currentTime = getCurrentTimeReceive();
        System.out.println(currentTime + message);
    }
    public static String getCurrentTimeReceive() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Định dạng ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        // Chuyển đổi và in ra theo định dạng
        String formattedDateTime = currentDateTime.format(formatter);
        return "[" + formattedDateTime + "] ";
    }
    
    public static String receive(DatagramSocket socket) throws IOException {
        byte buffer[] = new byte[1024];
        DatagramPacket recievePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(recievePacket);
        return new String(recievePacket.getData(),0,recievePacket.getLength());
    }
    
    public static boolean isPrime(int n) {
        if(n <= 1) return false;
        for(int i = 2; i<= Math.sqrt(n);i++) {
            if(n % i == 0) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        try {
            int port = 2002;
            DatagramSocket server = new DatagramSocket(port);
            System.out.println("Server running on port "+port);
           
            while (true) {
                //recieve
                byte buffer[] = new byte[1024];
                DatagramPacket recievePacket = new DatagramPacket(buffer, buffer.length);
                server.receive(recievePacket);
                
                String rq = new String(recievePacket.getData(),0,recievePacket.getLength());
                System.out.println("Server recieve: "+rq);
                
                String ques = rq.trim().split(",")[1];
                String question = ques.trim();
                if(question.trim().equals("100")) {
                    String qs = "1|2|5|7|9|3|6|8|1|2|5|9";
                    byte bufferSend[] = qs.getBytes();
                    send(server, bufferSend, recievePacket.getAddress(), recievePacket.getPort());
                    String data[] = qs.split("\\|");
                    List<Integer> numbers = new ArrayList<>();
                    for (String num : data) {
                        numbers.add(Integer.parseInt(num));
                    }
                    Collections.sort(numbers);
                    String sort1 = numbers.toString();
                    Collections.sort(numbers, Collections.reverseOrder());
                    String ans = sort1 + ", " + numbers.toString();
                    String clientRp = receive(server);
                    System.out.println("client res: "+clientRp);
                    if (clientRp.equals(ans)) {
                        System.out.println("Status: OK");
                    } else {
                        System.out.println("Status: False");
                    }
                } else if(question.equals("200")) {
                    //send
                    String qs = "1|2|2|3|3|9|4|4|5|6|1|2|7|8|9|14|15|20|7|21|29|31|9|9";
                    send(server, qs.getBytes(), recievePacket.getAddress(), recievePacket.getPort());
                    String data[] = qs.split("\\|");
                    Map<Integer, Integer> primes = new LinkedHashMap<>();
                    for (String element : data) {
                        int num = Integer.parseInt(element);
                        if (isPrime(num)) {
                            if (!primes.containsKey(num)) {
                                primes.put(num, 1);
                            } else {
                                primes.put(num, primes.get(num) + 1);
                            }
                        }
                    }
                    String ans = "";
                    for (Map.Entry<Integer, Integer> entry : primes.entrySet()) {
                        ans+=entry.getKey()+": "+entry.getValue()+", ";
                    }
                    ans = ans.substring(0,ans.lastIndexOf(","));
                    String clientRp = receive(server);
                    System.out.println("client send: "+clientRp);
                    System.out.println("-------------------------");
                    System.out.println("corect ans: "+ans);
                    if (clientRp.equals(ans)) {
                        System.out.println("Status: OK");
                    } else {
                        System.out.println("Status: False");
                    }
                } else if(question.equals("300")) {
                   String qs = "100|200|1|2|8|9|299|100|123|234|100";
                    send(server, qs.getBytes(), recievePacket.getAddress(), recievePacket.getPort());
                    String data[] = qs.split("\\|");
                    int s = 0;
                    for(String num : data) {
                        s += Integer.parseInt(num);
                    }
                    String ans = receive(server);
                    System.out.println("client send: "+ans);
                    System.out.println("-------------------------");
                    System.out.println("corect ans: "+ans);
                    if(s == Integer.parseInt(ans)) {
                        System.out.println("Status: OK");
                    } else {
                        System.out.println("Status: False");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Dell
 */
public class ServerTCP {

    public static String getCurrentTimeReceive() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Định dạng ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        // Chuyển đổi và in ra theo định dạng
        String formattedDateTime = currentDateTime.format(formatter);
        return "[" + formattedDateTime + "] ";
    }

    public static void Logs(String message) {
        String currentTime = getCurrentTimeReceive();
        System.out.println(currentTime + message);
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static int lcd(int a, int b) {
        return (int) (a * b) / gcd(a, b);
    }

    public static String capitalizeEachWord(String str) {
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String firstChar = word.substring(0, 1).toUpperCase();
                String remainingChars = word.substring(1).toLowerCase();
                result.append(firstChar).append(remainingChars).append(" ");
            }
        }

        return result.toString().trim();
    }

    public static void main(String[] args) {
        try {
            int port = 2002;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Starting Server TCP with port " + port + "...");
            while (true) {
                Socket client = serverSocket.accept();
                Thread dataQuest = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Logs("Acept connect: " + client.getInetAddress());
                            BufferedReader read = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                            String question = read.readLine();
                            Logs("Receive question from " + client.getInetAddress() + ": " + question);

                            //________________________________________Dạng DataInput/OutputStream________________________________________________
                            if (question.contains("800")) {
                                DataInputStream dis = new DataInputStream(client.getInputStream());
                                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                                Random ran = new Random();
                                int a = ran.nextInt(1000) + 10;
                                int b = ran.nextInt(1000) + 10;
                                dos.writeInt(a);
                                dos.writeInt(b);

                                boolean checkResult = dis.readInt() == (a + b) && dis.readInt() == a * b && dis.readInt() == gcd(a, b) && dis.readInt() == lcd(a, b);
                                String status = checkResult ? "OK" : "False";
                                Logs("respone client " + client.getInetAddress() + " status: " + status);
                            } //________________________________________Dạng Input/OutputStream________________________________________________
                            else if (question.contains("700")) {
                                String data = "6164561|618093|121451|8909|877264|67874|8900|9000|121451|8909|877264|67874|8900|9000";
                                String[] numbers = data.split("\\|");
                                int sum = 0;
                                for (String num : numbers) {
                                    sum += Integer.parseInt(num);
                                }
                                InputStream i = client.getInputStream();
                                OutputStream o = client.getOutputStream();
                                o.write(data.getBytes());
                                o.flush();
                                byte[] buffer = new byte[1024];
                                int bytesReader = i.read(buffer);
                                String ans = new String(buffer, 0, bytesReader);
                                String status = sum == Integer.parseInt(ans) ? "OK" : "False";
                                Logs("respone client " + client.getInetAddress() + " status: " + status);
                            } else if (question.contains("701")) {
                                String[] data = {
                                    "4705100|5249894|3926116|4285120|5824979|4554566|4987268|4575377|5356057|5262092|5249894|3926116|4285120|5824979|4554566|4987268|4575377|5356057|5262092",
                                    "3610560|3762391|2407752|1386341|8287425|5740330|4571555|6074650|8013799|4258997|3762391|2407752|1386341|8287425|5740330|4571555|6074650|8013799|4258997",
                                    "6362964|9315357|7999793|3313611|9844264|1608860|3366486|9741699|5099442|7675290|9315357|7999793|3313611|9844264|1608860|3366486|9741699|5099442|7675290",
                                    "1180054|2784272|6097378|5519550|4690928|1759715|8232997|6455740|6714997|8880097|2784272|6097378|5519550|4690928|1759715|8232997|6455740|6714997|8880097",
                                    "1360254|7172120|5751918|9978663|9674572|1226968|9728243|1319833|3023599|3035351|7172120|5751918|9978663|9674572|1226968|9728243|1319833|3023599|3035351",
                                    "2997595|6259924|6229627|8531264|2936880|7287584|5627742|1167699|1423008|8300758|6259924|6229627|8531264|2936880|7287584|5627742|1167699|1423008|8300758",
                                    "2226629|2257192|6600404|2207808|4832446|3327975|6151440|5204417|9229152|6286678|2257192|6600404|2207808|4832446|3327975|6151440|5204417|9229152|6286678",
                                    "4587193|1232197|2495442|1764046|2510986|7667439|1963880|1569599|9095822|9545039|1232197|2495442|1764046|2510986|7667439|1963880|1569599|9095822|9545039",
                                    "5297160|6065444|1610605|4873196|1667407|7597026|1452827|9483983|3887404|8632045|6065444|1610605|4873196|1667407|7597026|1452827|9483983|3887404|8632045",
                                    "9912583|4206277|1723859|7093518|1352676|9075782|6935994|9921013|9289914|3582665|4206277|1723859|7093518|1352676|9075782|6935994|9921013|9289914|3582665"
                                };
                                Random ran = new Random();
                                String ranData = data[ran.nextInt(data.length)];
                                String[] numbers = ranData.split("\\|");
//                    List<Integer> listNumber = new ArrayList<>();
                                Set<Integer> listNumber = new TreeSet<>();
                                int sum = 0;
                                for (String num : numbers) {
                                    int n = Integer.parseInt(num);
                                    sum += n;
                                    listNumber.add(n);
                                }
                                Integer numArrays[] = listNumber.toArray(new Integer[0]);
                                int max = numArrays[numArrays.length - 1],
                                        secondMax = numArrays[numArrays.length - 2],
                                        min = numArrays[0];

//                    int min = Collections.min(listNumber);
                                InputStream i = client.getInputStream();
                                OutputStream o = client.getOutputStream();
                                o.write(ranData.getBytes());
                                o.flush();
                                byte[] buffer = new byte[1024];
                                int bytesReader = i.read(buffer);
                                String ans = new String(buffer, 0, bytesReader);
                                String status;
                                try {
                                    String rev[] = ans.split(",");
                                    status = (min == Integer.parseInt(rev[0]) && secondMax == Integer.parseInt(rev[1]) && max == Integer.parseInt(rev[2])) ? "OK" : "False";
                                } catch (Exception e) {
                                    status = "False";
                                }

                                Logs("respone client " + client.getInetAddress() + " status: " + status);
                            } //________________________________________Dạng BufferedRead/Write________________________________________________
                            else if (question.contains("600")) {
                                String domains = "2eVFUJl4Z5.vn, s0ANOutx.vn, UwfCsjbQhM.com,jagfjha.edu,kjahfjaf.edu, dQlYBFX8.net, qLK8bpOiOKHW.io, B9KJzd.vn, JBaYY.vn, PxLH4tcuWczYfN.edu, y6qMOW4rYSy.vn, M9n1UJ.com, qMP5qqG.ed";
                                String[] listDomain = domains.split(",");
                                List<String> listAns = new ArrayList<>();
                                for (String domain : listDomain) {
                                    if (domain.endsWith(".edu")) {
                                        listAns.add(domain.trim());
                                    }
//                    out.flush();
                                }
                                write.write(domains);
                                write.newLine();
                                write.flush();
//                out.write("2eVFUJl4Z5.vn, s0ANOutx.vn, UwfCsjbQhM.com, dQlYBFX8.net, qLK8bpOiOKHW.io, B9KJzd.vn, JBaYY.vn, PxLH4tcuWczYfN.edu, y6qMOW4rYSy.vn, M9n1UJ.com, qMP5qqG.ed");
//                out.newLine();
//                out.flush();
                                System.out.println("send client done!");
                                String res = read.readLine();
                                String ans = String.join(",", listAns).trim();
                                Logs("ans: " + ans + "\nclient send: " + res);
                                String status = ans.equals(res) ? "OK" : "False";
                                Logs("respone client " + client.getInetAddress() + " status: " + status);
                            } //________________________________________Object_______________________________________
                            else if (question.contains("500")) {
                                Student student = new Student();
                                student.setName("John");
                                // Khởi tạo ObjectOutputStream sau khi đọc chuỗi "601"
                                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                                System.out.println("init done");
                                oos.writeObject(student);
                                oos.flush();
                                System.out.println("send");
                                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                                Student res = (Student) ois.readObject();
                                String status = res.getAge() == 22 ? "OK" : "False";
                                Logs("respone client " + client.getInetAddress() + " status: " + status);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dataQuest.start();
                System.out.println("Current thread: "+Thread.activeCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

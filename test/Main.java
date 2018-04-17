package com.example.demo.test;


import com.example.demo.protocol.Utils.Computer;

public class Main {

    public static void main(String[] args) {
//        AtomicInteger a=new AtomicInteger(1);
//        AtomicInteger b=new AtomicInteger(1);
//        int c=a.addAndGet(5);
//        b.addAndGet(4);
//        System.out.println(a.get());
//        System.out.println(c);
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.appendField("a",new Integer(15));
//        String operators="a$2";
        //int hh=AnalyzeUtils.computrValue(operators,jsonObject);
        //System.out.println(hh);
//        Computer computer=new Computer();
//        System.out.println(computer.compute(operators));

//        byte[] a={1,2,3,4};
//        StringBuilder aa=new StringBuilder();
//        aa.append(a[0]);
//        System.out.println(aa.toString());

//          Byte[]  a={1,2,3,4};
//          JSONObject jsonObject=new JSONObject();
//          jsonObject.appendField("a",a);
//          System.out.println(jsonObject.toString());
//        Yaml yaml=new Yaml();
//        String url="src\\main\\resources\\test.yml";
//        //String u=url.getPath();
//       // File f=new File(url);
//        try {
//            FileInputStream yml=new FileInputStream(new File(url));
//            Frame frame= yaml.loadAs(yml,Frame.class);
//            System.out.println(frame.getCrc_type());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


//        ArrayList<String> protocol_name=YamlLoder.loadResource("src\\\\main\\\\resources\\\\test.yml",ArrayList.class);
//        protocol_name.forEach((x)->{System.out.println(x);});
        Computer computer=new Computer();
        String s="a1";
        System.out.println(computer.isNumber(s));
    }
}
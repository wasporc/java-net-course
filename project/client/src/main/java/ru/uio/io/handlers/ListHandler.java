package ru.uio.io.handlers;

import ru.uio.io.Common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ListHandler extends BaseHandler {
    public ListHandler(DataInputStream in, DataOutputStream out) {
        super(in, out);
    }

    @Override
    public void workWithMessage() {
        try {
            out.write(Common.createDataPacket("121".getBytes("UTF8"),
                    "list".getBytes("UTF8")));
            out.flush();
            while (true) {
                boolean loop_break = false;
                if(in.read() == 2){
                    byte[] cmd_buff = new byte[3];
                    in.read(cmd_buff, 0, cmd_buff.length);
                    byte[] recv_buff = Common.readStream(in);
                    switch (Integer.parseInt(new String(cmd_buff))) {
                        case 121:
                            System.out.println(new String(recv_buff));
                            break;
                        case 120:
                            if("close".equals(new String(recv_buff))){
                                loop_break = true;
                            }
                            break;
                    }
                }
                if (loop_break == true) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

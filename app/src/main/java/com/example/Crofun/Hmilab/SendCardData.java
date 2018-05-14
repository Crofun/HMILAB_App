package com.example.Crofun.Hmilab;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

/**
 * Created by nodgd on 2017/09/16.
 */

class SendCardData extends BaseCardData {

    private static final String TAG = "SendCardData";

    public double[] cc=new double [4];
    public double dac_high;
    public double dac_low;
    private LineChartData mLineChartData;
    public int cha;
    public int crp;
    private int state = 0;
    private int count = 0;
    private int data[] = new int[0];
    private int channel = 0;
    private List<Integer> messageFlow;
    private SendRunner send;
    private Thread sendThread;
    private static int ChartLength = 30;

//233
    public SendCardData() {
        super();
    }

    public SendCardData(SendCardData cardData) {
        super(cardData);
        data = cardData.getData().clone();
    }

    @Override
    public String getTitle() {
        if (title.equals("")) {
            return "No Tag Sender";
        } else {
            return title + " (Sender)";
        }
    }


    public void startSendThread() {
        if (cha==-1)
            (sendThread=new Thread(send=new SendRunner(data, channel, getIdentifier()))).start();
        else
            (sendThread=new Thread(send=new SendRunner(data, channel, getIdentifier(),cc,dac_high,dac_low,crp))).start();
    }


    public void stopSendThread() {
        //TODO 安全的结束
        Intent intent=new Intent(SendRunner.SendRunner_Off);
        intent.putExtra("channel",channel);
        LocalBroadcastManager.getInstance(MyApplication.getContext()).sendBroadcast(intent);
        Log.i(TAG, "stopSendThread(): TODO");
    }

    public void createLineChartData() {
        mLineChartData = CreateCardParameterAdapter.createLineChartData(cc[0], cc[1], cc[2], cc[3], dac_high, dac_low);
    }

    public LineChartData getLineChartData() {
        return mLineChartData;
    }

    //TODO 整理下面这些乱七八糟的函数，没有用的就删了


    public void DataReview() {
        count = count + 1;
        if (count < data.length)
            add(data[count]);
    }

    public void setData(int a[]) {
        data = a.clone();
    }

    public int[] getData() {
        return data;
    }


    public void setChannel(int Channel) {
        channel = Channel;
    }

    public int getChannel() {
        return channel;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInString() {
        return "" + state;
    }

    public void setMessageFlow(int[] msg) {
        messageFlow = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
            messageFlow.add(msg[i]);
        }
    }

    public void setMessageFlow(List<Integer> msg) {
        messageFlow = msg;
    }

    public void add(int msgItem) {
        messageFlow.add(msgItem);
    }

    public void add(int[] msg) {
        for (int msgItem : msg) {
            messageFlow.add(msgItem);
        }
    }

    public void add(List<Integer> msg) {
        for (Integer msgItem : msg) {
            messageFlow.add(msgItem);
        }
    }
}

package com.example.phase2.phase2;

import android.os.Bundle;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConfigGateway {

    private AdminActions adminActions;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;
    private File contextFilesDir;

    public ConfigGateway(File contextFilesDir){
        this.contextFilesDir = contextFilesDir;
    }

    public ConfigGateway(){

    }
    /**
     * Reads objects from the given path
     * @param path the string path of the file
     * @return a Serializable object containing information for the program
     * @throws IOException from the InputStream
     * @throws ClassNotFoundException from ObjectInputStream
     */

    public Serializable readInfo(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            InputStream fileInput = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(fileInput);
            ObjectInput input = new ObjectInputStream(buffer);

            Serializable serializable = (Serializable) input.readObject();
            input.close();
            return serializable;
        } else {
            if (file.createNewFile()) {
                return readInfo(path);
            }
            else{
                return null;
            }
        }
    }

    private void loadClasses(){
        try {
            adminActions = (AdminActions) readInfo(contextFilesDir + "admins.ser");
            meetingManager = (MeetingManager) readInfo(contextFilesDir + "meetings.ser");
            itemManager = (ItemManager) readInfo(contextFilesDir + "items.ser");
            tradeManager = (TradeManager) readInfo(contextFilesDir + "trades.ser");
            traderManager = (TraderManager) readInfo(contextFilesDir + "traders.ser");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            downloadInitial();
        }
    }
    public Bundle getBundle(){
        loadClasses();
        Bundle bundle = new Bundle();
        bundle.putSerializable(traderManager.getIdentifier(), traderManager);
        bundle.putSerializable(itemManager.getIdentifier(), itemManager);
        bundle.putSerializable(tradeManager.getIdentifier(), tradeManager);
        bundle.putSerializable(meetingManager.getIdentifier(), meetingManager);
        bundle.putSerializable(adminActions.getIdentifier(), adminActions);

        return bundle;
    }

    /**
     * Saves the information to the file
     * @param path the string path to the file
     * @param manager the Serializable object containing information about the file
     * @throws IOException from the OutputStream
     */
    public void saveInfo(String path, Serializable manager) throws IOException{
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(manager);
        output.close();
    }

    private void downloadInitial(){
        HashMap<String, Admin> admins = new HashMap<>();
        admins.put("Admin", new Admin("Admin", "Wordpass", "2020-07-27", true));
        adminActions = new AdminActions(admins);
        Item item1 = new Item(1, "Bruh", "Arjun");
        item1.setStatus(ItemStatus.AVAILABLE);
        item1.setCategory("What do you think?");
        item1.setDescription("bruhbruhbruh");
        HashMap<Integer, Item> tempMap = new HashMap<>();
        tempMap.put(1, item1);
        itemManager = new ItemManager(tempMap);
        meetingManager = new MeetingManager(new HashMap<Integer, Meeting>());
        tradeManager = new TradeManager(new HashMap<Integer, Trade>());
        traderManager = new TraderManager(new HashMap<String, Trader>(), 3, 1, 0);
        traderManager.addTrader(new Trader("Trader1", "Password"));
        traderManager.addTrader(new Trader("Trader2", "Password2"));
        Trader traderFlagged = new Trader("Arjun", "Password3");
        traderFlagged.setFlagged(true);
        traderManager.addTrader(traderFlagged);
        Trader traderUnfreeze = new Trader("Jeffrey", "Password4");
        traderUnfreeze.setFrozen(true);
        traderUnfreeze.setRequestToUnfreeze(true);
        traderManager.addTrader(traderUnfreeze);

        //Adds one way permanent trade
        List<Integer> tempTradeItems = new ArrayList<>();
        tempTradeItems.add(itemManager.addItem("Bike", "Arjun"));
        itemManager.addItemDetails(tempTradeItems.get(0), "Transportation", "Its a bike", 10);
        itemManager.changeStatusToUnavailable(tempTradeItems.get(0));
        //CREATES THE TRADE AND ADDS IT TO TRADE INVENTORY
        Integer tradeId = tradeManager.createTrade("Arjun", "Trader2", "ONEWAY", true, tempTradeItems);
        LocalDate tempDate = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId,tempDate);
        traderManager.addNewTrade("Trader2", tradeId, tempDate);
        //CREATES THE MEETING
        meetingManager.createMeeting(tradeId, "Arjun", "Trader2", true);
        meetingManager.setMeetingInfo(tradeId, LocalDate.now(), LocalDate.now(),
                "Toronto", "Toronto");
        adminActions.newAdmin("Admin2", "Wordpass");
        adminActions.newAdmin("Sup", "nothing");

        //Adds permanent two way trade
        List<Integer> tempTradeItems2 = new ArrayList<>();
        Integer temp2 = itemManager.addItem("Jacket", "Trader2");
        Integer temp3 = itemManager.addItem("Watch", "Arjun");
        tempTradeItems2.add(temp2);
        tempTradeItems2.add(temp3);
        itemManager.addItemDetails(temp2, "Clothing", "Its a jacket", 7);
        itemManager.addItemDetails(temp3, "Accessories", "Its a watch", 5);
        itemManager.changeStatusToUnavailable(temp2);
        itemManager.changeStatusToUnavailable(temp3);

        Integer tradeId2 = tradeManager.createTrade("Arjun", "Trader2", "TWOWAY", true, tempTradeItems2);

        LocalDate tempDate2 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId2,tempDate2);
        traderManager.addNewTrade("Trader2", tradeId2, tempDate2);

        meetingManager.createMeeting(tradeId2, "Arjun", "Trader2", true);
        meetingManager.setMeetingInfo(tradeId2, LocalDate.now(), LocalDate.now(),
                "Toronto", "Toronto");

        //Adds temporary one way trade
        List<Integer> tempTradeItems3 = new ArrayList<>();
        tempTradeItems3.add(itemManager.addItem("Light", "Arjun"));
        itemManager.addItemDetails(tempTradeItems3.get(0), "Home", "Its a light", 10);
        itemManager.changeStatusToUnavailable(tempTradeItems3.get(0));

        //CREATES THE TRADE AND ADDS IT TO TRADE INVENTORY
        Integer tradeId3 = tradeManager.createTrade("Arjun", "Trader1", "ONEWAY", false, tempTradeItems3);
        LocalDate tempDate3 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId3,tempDate3);
        traderManager.addNewTrade("Trader1", tradeId3, tempDate3);
        //CREATES THE MEETING
        meetingManager.createMeeting(tradeId3, "Arjun", "Trader1", false);
        meetingManager.setMeetingInfo(tradeId3, LocalDate.now(), LocalDate.now(),
                "Toronto", "N/A");

        //Adds temporary two-way trade
        List<Integer> tempTradeItems4 = new ArrayList<>();
        Integer temp6 = itemManager.addItem("Laptop", "Trader1");
        Integer temp7 = itemManager.addItem("Phone", "Arjun");
        tempTradeItems4.add(temp6);
        tempTradeItems4.add(temp7);
        itemManager.addItemDetails(temp6, "Technology", "Its a laptop", 7);
        itemManager.addItemDetails(temp7, "Technology", "Its a Phone", 5);
        itemManager.changeStatusToUnavailable(temp6);
        itemManager.changeStatusToUnavailable(temp7);

        Integer tradeId4 = tradeManager.createTrade("Arjun", "Trader1", "TWOWAY", false, tempTradeItems4);

        LocalDate tempDate4 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId4,tempDate4);
        traderManager.addNewTrade("Trader1", tradeId4, tempDate4);

        meetingManager.createMeeting(tradeId4, "Arjun", "Trader1", false);
        meetingManager.setMeetingInfo(tradeId4, LocalDate.now(), LocalDate.now(),
                "Toronto", "N/A");


        //Creates an online one-way meeting:
        List<Integer> tempTradeItems5 = new ArrayList<>();
        Integer temp8 = itemManager.addItem("E-Book", "Trader1");
        tempTradeItems5.add(temp8);
        itemManager.changeStatusToUnavailable(temp8);
        itemManager.addItemDetails(temp8, "Online", "Its an ebook", 10);
        Integer tradeId5 = tradeManager.createTrade("Arjun", "Trader1", "ONLINE-ONEWAY", true, tempTradeItems5);
        LocalDate tempDate5 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId5,tempDate5);
        traderManager.addNewTrade("Trader1", tradeId5, tempDate5);
        //CREATES THE MEETING
        meetingManager.createMeeting(tradeId5, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId5, LocalDate.now(), LocalDate.now(),
                "Online", "Online");

        //Creates an online two-way meeting
        List<Integer> tempTradeItems6 = new ArrayList<>();
        Integer temp9 = itemManager.addItem("E-Book-2", "Trader1");
        Integer temp10 = itemManager.addItem("E-Textbook", "Arjun");
        tempTradeItems6.add(temp9);
        tempTradeItems6.add(temp10);
        itemManager.addItemDetails(temp9, "Online", "Its a ebook", 7);
        itemManager.addItemDetails(temp10, "Online", "Its a textbook", 5);
        itemManager.changeStatusToUnavailable(temp9);
        itemManager.changeStatusToUnavailable(temp10);

        Integer tradeId6 = tradeManager.createTrade("Arjun", "Trader1", "ONLINE-TWOWAY", true, tempTradeItems6);

        LocalDate tempDate6 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId6,tempDate6);
        traderManager.addNewTrade("Trader1", tradeId6, tempDate6);

        meetingManager.createMeeting(tradeId6, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId6, LocalDate.now(), LocalDate.now(),
                "ONLINE", "N/A");

        try {
            String ADMINPATH = "admins.ser";
            saveInfo(contextFilesDir + ADMINPATH, adminActions);
            String MEETINGPATH = "meetings.ser";
            saveInfo(contextFilesDir + MEETINGPATH, meetingManager);
            String TRADERPATH = "traders.ser";
            saveInfo(contextFilesDir + TRADERPATH, traderManager);
            String TRADEPATH = "trade.ser";
            saveInfo(contextFilesDir + TRADEPATH, tradeManager);
            String ITEMPATH = "items.ser";
            saveInfo(contextFilesDir + ITEMPATH, itemManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBundle(Bundle bundle){

    }
}
